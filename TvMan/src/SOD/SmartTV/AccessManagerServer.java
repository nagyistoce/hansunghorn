package SOD.SmartTV;

import java.net.SocketException;

import SOD.Common.Packet;

/**
 * 
 * @author MB
 *
 */
abstract public class AccessManagerServer {

	/**
	 * 서버 초기화 및 beginListening, beginCheckingConnection 호출
	 * @param conf
	 * 서버의 정보
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 * @throws IllegalStateException
	 * setReceiveHandler()가 호출되지 않았거나 이미 이전에 start()가 호출되었다면 발생
	 */
	public abstract void start(ServerConfig conf) throws IllegalArgumentException, IllegalStateException;

	/**
	 * beginListening, beginCheckingConnection에서 생성한 스레드를 종료하고,
	 * 콜백 함수를 전부 제거하고 할당된 모든 리소스 해제.
	 * @throws IllegalStateException
	 * start()가 호출되기 전에 호출되거나 두번 이상 호출될 시 발생
	 */
	public abstract void shutdown() throws IllegalStateException;

	/**
	 * 새 연결이 들어왔을떄 호출되는 콜백함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public abstract void setConnectHandler(ConnectHandler handler) throws IllegalArgumentException;

	/**
	 * 연결이 끊겼을떄 호출되는 콜백함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public abstract void setDisconnectHandler(DisconnectHandler handler) throws IllegalArgumentException;

	/**
	 * 현재 서버에 접속이 성립되어 생성된 Transceiver중 어떤 것이든 onReceive 콜백이 호출될때
	 * 전달된 Packet및 해당 Transceiver의 id를 넘겨주는 콜백 함수를 등록한다.
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	public abstract void setReceiveHandler(ServerReceiveHandler handler) throws IllegalArgumentException;
	
	/**
	 * 컬렉션에 등록된 연결 갯수를 가져온다.
	 * @return
	 * 현재 붙어있는 연결 갯수
	 */
	public abstract int getConnectionCount();

	/**
	 * Send a Packet via Transceiver which matching connid.
	 * @param pkt
	 * 데이터를 담은 패킷
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * pkt이 null이거나 connid가 유효하지 않은 경우 발생
	 * @throws IllegalStateException
	 * start()를 호출하기 전에 호출되었을 경우 발생
	 * @throws SocketException
	 * 내부 Transceiver 객체 에서 예외가 발생시 전달
	 */
	public abstract void send(Packet pkt, int connid) throws IllegalArgumentException, IllegalStateException, SocketException;

	/**
	 * 해당 id에 일치하는 Transceiver의 연결을 끊고 컬렉션에서 제거한다.
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * connid가 유효하지 않은 경우 발생
	 */
	public abstract void dropConnection(int connid) throws IllegalArgumentException;

	/**
	 * 새로운 연결을 수신(Accept)하기 위한 작업 스레드를 생성한다.
	 * 연결이 될때마다 내부의 컬렉션에 연결이 추가된다.
	 */
	protected abstract void beginListening();

	/**
	 * 현재 컬렉션에 있는 각 Transceiver들을 열거하며 연결 점검용 패킷을 보내기 위한 작업 스레드를 생성한다.
	 * 열거도중 패킷이 마지막으로 응답한 시간과 현재 시간의 차이가 Timeout을 초과하면 dropConnection 호출.
	 */
	protected abstract void beginCheckingConnection();
	
	/**
	 * 특정 Transceiver에게 이 서비스의 이름을 전송한다.
	 * @param name
	 * 서비스의 이름
	 * @param connid
	 * Transceiver 객체를 가르키는 id
	 * @throws IllegalArgumentException
	 * String NULL이거나 ,connid가 유효하지 않은 경우 발생
	 */
	protected abstract void sendServiceName(String name, int connid) throws IllegalArgumentException;

}
