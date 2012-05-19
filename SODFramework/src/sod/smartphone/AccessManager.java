package sod.smartphone;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import sod.test.ActionEx;

import sod.common.Constants;
import sod.common.Disposable;
import sod.common.NetworkUtils;
import sod.common.Packet;
import sod.common.ReceiveHandler;
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
	
	//listening loop �����
	boolean isRunning = false;
	
	//�����κ��� ������ �޾Ҵ��� ����
	boolean isConnected = false;
	
	//���� Ž������ ǥ���ϴ� �÷���
	static boolean isSearching = false;
	
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
	 * ���� Ž���� �����ϰ� ����� ����(���� Ž�������̸� true)
	 * @return
	 */
	public static boolean stopSearch(){
		boolean rv = isSearching;
		isSearching = false;
		return rv;
	}
	
	static void trySearch(Packet pkt, String ip, int port, SearchCallBack cb){
		
		final int TurnAroundLimitTime = 1000;
		
		Transceiver t = new Transceiver(new InetSocketAddress(ip, port));
		t.send(pkt);
		
		if(Constants.isDebug)
			Constants.logger.log("(debug:client) pinged to " + ip + "\n");
			
		ThreadEx.invoke(new Object[]{t, cb}, new ActionEx() {
			@Override
			public void work(Object arg) {
				Object[] args = (Object[])arg;
				Transceiver _t = (Transceiver)args[0];
				SearchCallBack _cb = (SearchCallBack)args[1];
				
				Packet _p = new Packet();
				InetSocketAddress result = _t.receive(_p);

				if(result != null)
				{
					ServerInfo rv = new ServerInfo();
					rv.EndPoint = result;
					rv.ServiceName = (String) _p.pop();
					_cb.onSearch(rv);
				}
			}
		});
		
		ThreadEx.sleep(TurnAroundLimitTime);
		t.dispose();
	}

	/**
	 * ���� �������̿� �ִ� ������ ã�� ����� ��ȯ
	 * �ݹ����� �Ѱ��ִ� ���ڰ� null�̸� Ž���� �������� �������� �ǹ���.
	 */		
	public static void searchServer(String baseIP, int port, SearchCallBack cb){
		
		final int DevideCount = 4;
		
		isSearching = true;		
		ThreadEx.invoke(new Object[] {baseIP, port, cb}, new ActionEx() {			
			@Override
			public void work(Object arg) {
				Object[] args = (Object[])arg;
				String base = (String) args[0];
				Integer port = (Integer) args[1];
				SearchCallBack _cb = (SearchCallBack) args[2];
				Iterator<String> ipset = NetworkUtils.getIPSet(base);
				
				Packet p = new Packet();
				p.signiture = Packet.REQUEST_PING;
				
				while(ipset.hasNext() && isSearching){
					String ip = ipset.next();
					trySearch(p, ip, port, _cb);
				}
				
				//end of search
				if(Constants.isDebug)
					Constants.logger.log("(debug:client) search is finished\n");
				
				_cb.onSearch(null);
			}
		});
		
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
	 * ��Ŷ�� ������ ������.
	 * @param pkt
	 * ���� �����͸� ���� ��Ŷ
	 */
	public boolean send(Packet pkt){
		if(pkt == null) return false;
		return conn.send(pkt);
	}
	
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
						break;
					case Packet.REQUEST_CLIENT_ALIVE:
						conn.send(p_check);
						break;
					case Packet.RESPONSE_SERVICE_DATA:
					case Packet.RESPONSE_SERVICE_NAME:
						//need to implement
						break;
					default:
						cb.onReceive(p);
						break;
					}
				}
			}
			
		});		
	}

}

