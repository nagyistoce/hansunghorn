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
	
	//listening loop 제어용
	boolean isRunning = false;
	
	//서버로부터 인증을 받았는지 여부
	boolean isConnected = false;
	
	//서버 탐색중을 표시하는 플래그
	static boolean isSearching = false;
	
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
	 * 서버 탐색을 중지하고 결과를 리턴(서버 탐색도중이면 true)
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
	 * 같은 와이파이에 있는 서버를 찾아 목록을 반환
	 * 콜백으로 넘겨주는 인자가 null이면 탐색의 마지막에 도달함을 의미함.
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

