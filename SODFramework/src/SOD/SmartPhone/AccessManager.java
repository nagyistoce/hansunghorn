package SOD.SmartPhone;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;

/**
 * 
 * @author MB
 *
 */
public abstract class AccessManager {
	
	ServerInfo svinfo;
	
	/**
	 * 서버와 연결을 시도한다.
	 * @param info
	 * 접속할 서버의 정보
	 */
	public void connect(ServerInfo info){
		this.svinfo = info;
		
	}

	/**
	 * 같은 와이파이에 있는 서버를 찾아 목록을 반환
	 * @return
	 * 발견된 서버 목록
	 */
	public abstract ServerInfo[] searchServer();

	/**
	 * 서버와 연결을 끊고 dispose()를 호출한다.
	 */
	public abstract void disconnect();

	/**
	 * Packet이 수신됐을때 호출될 콜백함수를 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 */
	public abstract void setReceiveHandler(ReceiveHandler handler);

	/**
	 * 패킷을 서버로 보낸다.
	 * @param pkt
	 * 보낼 데이터를 담은 패킷
	 */
	public abstract void send(Packet pkt);

	/**
	 * 서버로 부터 수신데이터를 확인하고 서버의 확인에 응답할 작업 스레드를 생성한다.
	 */
	protected abstract void beginListening();

	/**
	 * 수신하는 작업 스레드를 종료하고 할당된 모든 리소스를 반환한다.
	 * terminate listening thread and release resources.
	 */
	protected abstract void dispose();
}
