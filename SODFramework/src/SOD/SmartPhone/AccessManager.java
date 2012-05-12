package SOD.SmartPhone;

import java.net.InetSocketAddress;

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
	 * @return
	 * 발견된 서버 목록
	 */
	public ServerInfo[] searchServer(){
		//아직 미구현
		return null;
	}

	/**
	 * 수신하는 작업 스레드를 종료하고 할당된 모든 리소스를 반환한다. (접속은 자동으로 종료됨)
	 * terminate listening thread and release resources.
	 */
	public void dispose(){
		isRunning = false;
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
				Packet p = new Packet();
				Packet p_check = new Packet();
				p_check.signiture = Packet.RESPONSE_CLIENT_ALIVE;

				while(isRunning){
					p.clear();
					conn.receive(p);
					
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
