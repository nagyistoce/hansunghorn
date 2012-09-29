package sod.smartphone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream.GetField;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import sod.common.ActionEx;
import sod.common.Constants;
import sod.common.Disposable;
import sod.common.NetworkUtils;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.Serializer;
import sod.common.ThreadEx;
import sod.common.Transceiver;

/**
 * 
 * @author MB
 *
 */
public class AccessManager implements Disposable {
	
	ServerInfo svinfo;
	Transceiver conn;
	ReceiveHandler cb;
	
	ServiceManager serviceManager;
	
	ActionEx startServiceDelegate;
	
	//debug
	int downloadCnt = 0;
	
	//listening loop �����
	boolean isRunning = false;
	
	//�����κ��� ������ �޾Ҵ��� ����
	boolean isConnected = false;
	
	public AccessManager(){
		serviceManager = new ServiceManager();
	}
	/**
	 * ������ ������ �õ��Ѵ�.
	 * @param info
	 * ������ ������ ����
	 */
	public void connect(ServerInfo info){
		this.svinfo = info;
		conn = new Transceiver(info.EndPoint);
		
		Packet p = new Packet();
		p.signiture = Packet.REQUEST_ACCEPT;
 		conn.send(p);
		isRunning = true;
		beginListening();
		
	}
	
	
	/**
	 * ���� �������̿� �ִ� ������ ã�� ����� ��ȯ
	 * �ݹ����� �Ѱ��ִ� ���ڰ� null�̸� Ž���� �������� �������� �ǹ���.
	 * @param baseIP ip����
	 * @param cb  �˻� �ݹ� �Լ�
	 */
	public static void searchServer(String baseIP, SearchCallBack cb){		
		final int TurnAroundWaitTime = 4000;
		
		MulticastSocket s = NetworkUtils.createMutlicastSocket(Constants.Multicast_IP, Constants.Multicast_Port_Send);
		Transceiver t = null;
		try {
			Serializer se = new Serializer();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			Packet p = new Packet();
			p.signiture = Packet.REQUEST_PING;
			p.push(NetworkUtils.getLocalIP());
			p.push(Constants.Multicast_Port_Response);

			se.serialize(output, p);
			byte[] buf = output.toByteArray();
			DatagramPacket rawp = new DatagramPacket(buf, buf.length,
					NetworkUtils.getMulticastAddr(),
					Constants.Multicast_Port_Send);
			s.send(rawp);
			s.close();
			t = new Transceiver(null, Constants.Multicast_Port_Response);
			
			ThreadEx.invoke(new Object[]{cb, t}, new ActionEx() {				
				@Override
				public void work(Object arg) {
					Object[] args = (Object[])arg;
					SearchCallBack _cb = (SearchCallBack)args[0];
					Transceiver _t = (Transceiver)args[1];
					
					while(true){
						try {				
							Packet p = new Packet();
							Object rv = _t.receive(p);
							if(rv == null) return;							
							
							ServerInfo info = new ServerInfo();
							String _ip = (String)p.pop();
							int _port = (Integer)p.pop();
							String _service = (String)p.pop();
							info.EndPoint = new InetSocketAddress(_ip, _port);
							info.ServiceName = _service;
							_cb.onSearch(info);
						} catch (Exception ex) {
							ex.printStackTrace();
							return;
						}
					}
				}
			});
			ThreadEx.sleep(TurnAroundWaitTime);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(t != null)
				t.dispose();
			cb.onSearch(null);
		}
	}

	/**
	 * �����ϴ� �۾� �����带 �����ϰ� �Ҵ�� ��� ���ҽ��� ��ȯ�Ѵ�. (������ �ڵ����� �����)
	 * terminate listening thread and release resources.
	 */
	public void dispose(){
		isRunning = false;
		conn.dispose();
	}

	/**
	 * Packet�� ���ŵ����� ȣ��� �ݹ��Լ��� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 */
	public void setReceiveHandler(ReceiveHandler handler){
		cb = handler;
	}
	
	/**
	 * ���񽺸� ������ �� ������ �Լ��� ����Ѵ�.
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 */
	public void setStartServiceDelegate(ActionEx startAction){
		this.startServiceDelegate = startAction;
	}

	/**
	 * ��Ŷ�� ������ ������.
	 * @param pkt
	 * ���� �����͸� ���� ��Ŷ
	 */
	public boolean send(Packet pkt){
		if(pkt == null) return false;
		return conn.send(pkt);
	}
	
	/**
	 * Ŭ���̾�Ʈ�� ���� ������ ���ӵ��ִ� ���θ� �˷��ִ� �޼ҵ�
	 * @return ���ӵ� ���¸� true, �ƴϸ� false
	 */
	public boolean isConnected(){
		return isConnected;
	}

	/**
	 * ������ ���� ���ŵ����͸� Ȯ���ϰ� ������ Ȯ�ο� ������ �۾� �����带 �����Ѵ�.
	 */
	protected void beginListening(){
		ThreadEx.invoke(null, new ActionEx() {		
			
			@Override
			public void work(Object arg) {
				InetSocketAddress sender = null;
				Packet p = new Packet();
				Packet p_check = new Packet();
				p_check.signiture = Packet.RESPONSE_CLIENT_ALIVE;

				while(isRunning){
					p.clear();
					sender = conn.receive(p);
					if(sender == null) continue;
					
					switch(p.signiture){
					case Packet.RESPONSE_ACCEPT:
						isConnected = true;
						
						Constants.logger.log("(debug:client) RESPONSE_ACCEPT.\n");
						//1. ���񽺰� �ִ��� ������ Ȯ���Ѵ�.
						if(isExistService()){
							Constants.logger.log("(debug:client) START SERVICE.\n");
							startService();   //���߿� �ּ� ���־���
						}
						else{
							Constants.logger.log("(debug:client) REQUEST_SERVICE_DATA.\n");
							p.signiture= Packet.REQUEST_SERVICE_DATA;
							conn.send(p);
						}
						break;
					case Packet.REQUEST_CLIENT_ALIVE:
						conn.send(p_check);
						break;
					case Packet.RESPONSE_SERVICE_DATA:
						Constants.logger.log("(debug:client) installService");
						
						serviceManager.installService(p);

						break;

					case Packet.RESPONSE_SERVICE_DATA_END:
						Constants.logger.log("(debug:client) Service Install complete");
						startService();    //���߿� �ּ� ���־���
						break;
					default:
						cb.onReceive(p);
						break;
					}
				}
			}
			
		});		
	}
	
	/**
	 * �ٿ�ε� ���� ���񽺰� �ִ��� Ȯ���ϴ� �޼ҵ�
	 * @return �ٿ�ε� ���� ���񽺰� ������ true, �ƴϸ� false
	 */
	protected boolean isExistService(){
		return serviceManager.checkServiceInstalled(svinfo.ServiceName);
		
	}
	
	/**
	 * startServiceDelegate�� ����� �ݹ��Լ��� �����Ѵ�.
	 */
	protected void startService(){
		startServiceDelegate.work(svinfo.ServiceName);
	}

}

