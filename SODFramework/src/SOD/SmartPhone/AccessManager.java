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
	
	//listening loop 제어용
	boolean isRunning = false;
	
	//서버로부터 인증을 받았는지 여부
	boolean isConnected = false;
	
	/**
	 * 서버와 연결을 시도한다.
	 * @param info
	 * 접속할 서버의 정보
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
	 * 같은 와이파이에 있는 서버를 찾아 목록을 반환
	 * 콜백으로 넘겨주는 인자가 null이면 탐색의 마지막에 도달함을 의미함.
	 * (아직 구현 완성이 아님. 콜백 raise부분과 서버측 응답 처리 미완성)
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
	 * 수신하는 작업 스레드를 종료하고 할당된 모든 리소스를 반환한다. (접속은 자동으로 종료됨)
	 * terminate listening thread and release resources.
	 */
	public void dispose(){
		isRunning = false;
		conn.dispose();
	}

	/**
	 * Packet이 수신됐을때 호출될 콜백함수를 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 */
	public void setReceiveHandler(ReceiveHandler handler){
		cb = handler;
	}

	/**
	 * 패킷을 서버로 보낸다.
	 * @param pkt
	 * 보낼 데이터를 담은 패킷
	 */
	public boolean send(Packet pkt){
		if(pkt == null) return false;
		return conn.send(pkt);
	}
	
	public boolean isConnected(){
		return isConnected;
	}

	/**
	 * 서버로 부터 수신데이터를 확인하고 서버의 확인에 응답할 작업 스레드를 생성한다.
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

