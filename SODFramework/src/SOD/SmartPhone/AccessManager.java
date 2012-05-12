package SOD.SmartPhone;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import SOD.Common.Disposable;
import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.Common.ThreadEx;
import SOD.Common.Transceiver;
import SOD.Test.ActionEx;

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
	 * (���� ���� �ϼ��� �ƴ�. �ݹ� raise�κа� ������ ���� ó�� �̿ϼ�)
	 */	
	
	public static void searchServer(SearchCallBack cb){
		
		ThreadEx.invoke(cb, new ActionEx() {			
			@Override
			public void work(Object arg) {
				SearchCallBack _cb = (SearchCallBack)arg;
				Queue<Transceiver> list = new LinkedList<Transceiver>();
				
				Packet p = new Packet();
				p.signiture = Packet.REQUEST_PING;
				for(int u = 0; u < 0xFF; ++u){
					Transceiver t = new Transceiver(null);
					t.send(p);
					list.offer(t);
					
					ThreadEx.invoke(new Object[]{t, _cb}, new ActionEx() {
						@Override
						public void work(Object arg) {
							Object[] args = (Object[])arg;
							Transceiver _t = (Transceiver)args[0];
							SearchCallBack __cb = (SearchCallBack)args[1];
							
							Packet _p = new Packet();
							Object result = _t.receive(_p);

							if(result != null)
							{
								//not implemented yet
								//need to raise cb;
							}
						}
					});
					
					ThreadEx.sleep(100);
				}
				
				//turn-around limit time
				ThreadEx.sleep(4000);
				
				while(list.size() > 0){
					Transceiver t = list.poll();
					t.dispose();
					ThreadEx.sleep(100);
				}				
				
				//end of search
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

