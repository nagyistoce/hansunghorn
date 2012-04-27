package SOD.SmartPhone;

import java.net.ConnectException;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;

/**
 * 
 * @author MB
 *
 */
public abstract class AccessManager {
	
	/**
	 * 서버와 연결을 시도한다.
	 * @param info
	 * 접속할 서버의 정보
	 * @throws  ConnectException
	 * 접속하는 과정이 실패 했을 경우, ConnectException을 던진다.
	 * @throws IllegalStateException
	 * 이미 연결되어 있는 상태에서 dispose()되기 전에 connect()를 다시 호출하면 IllegalStateException을 던진다.
	 * @throws IllegalArgumentException
	 * ServerInfo 객체가 NULL이면 IllegalArgumentException을 던진다.
	 */
	public abstract void connect(ServerInfo info) throws ConnectException, IllegalStateException, IllegalArgumentException;

	/**
	 * 같은 와이파이에 있는 서버를 찾아 목록을 반환
	 * @return
	 * 발견된 서버 목록
	 */
	public abstract ServerInfo[] searchServer();

	/**
	 * 서버와 연결을 끊고 dispose()를 호출한다.
	 * @throws IllegalStateException
	 * connect()가 되기 전에 호출되면 IllegalStateException을 던진다.
	 */
	public abstract void disconnect() throws IllegalStateException;

	/**
	 * Packet이 수신됐을때 호출될 콜백함수를 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 핸들러가 아닌 것을 넘겨주면 IllegalArgumentException을 던진다.
	 */
	public abstract void setReceiveHandler(ReceiveHandler handler) throws IllegalArgumentException;

	/**
	 * 패킷을 서버로 보낸다.
	 * @param pkt
	 * 보낼 데이터를 담은 패킷
	 * @throws IllegalArgumentException
	 * 넘어온 패킷 객체가 NULL이면, IllegalArgumentException을 던진다.
	 * @throws IllegalStateException
	 * connect()가 호출되기 전에 send를 호출하면 IllegalStateException을 던진다.
	 * @throws SocketException
	 * 전송하는 과정이 실패하면 SocketException을 던진다.
	 */
	public abstract void send(Packet pkt) throws IllegalArgumentException, IllegalStateException, SocketException;

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
