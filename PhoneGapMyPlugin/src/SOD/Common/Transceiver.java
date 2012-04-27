package SOD.Common;

import java.net.SocketException;

/**
 * 
 * @author MB
 * Make a connection between remote point
 */
abstract public class Transceiver {
	
	/**
	 * 넘겨준 패킷 객체를 상대(스마트TV, 스마트폰)에게 보낸다.
	 * @param pkt
	 * @throws IllegalArgumentException
	 * 넘어온 패킷 객체가 NULL일 때, IllegalArgumentException을 던진다.
	 * @throws SocketException
	 * 전송하는 과정이 실패하면 SocketException을 던진다.
	 */
	abstract public void send(Packet pkt) throws IllegalArgumentException, SocketException;
	
	/**
	 * 수신을 받을때 사용할 핸들러를 등록한다.
	 * @param handler
	 * 패킷을 수신했을 때, 프레임워크를 이용하는 소프트웨어에게 패킷을 넘겨주는 핸들러
	 * @throws IllegalArgumentException
	 * NULL을 넘겨주면  IllegalArgumentException을 던진다.
	 */
	abstract public void setReceiveHandler(ReceiveHandler handler) throws IllegalArgumentException;
	
	/**
	 * 상대방(스마트TV, 스마트폰)으로부터 패킷을 수신받을 때, 호출되는 메소드이다.
	 * @param pkt
	 * 상대방(스마트TV, 스마트폰)으로부터 받은 패킷
	 */
	protected abstract void onReceive(Packet pkt);  //protect
}
