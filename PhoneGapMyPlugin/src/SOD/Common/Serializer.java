package SOD.Common;


import java.io.InputStream;
import java.io.OutputStream;


/**
 * 
 * @author MB
 *
 * Write & read object via stream
 */
abstract public class Serializer {
	
	/**
	 * 패킷 객체를 전달받아 직렬화한 후에 OutputStream을 이용하여 OutputStream에 쓴다.
	 * 직렬화는 Json을 이용하여 이루어진다.
	 * 송신하는 과정과 저장소에 쓰기전에 호출되는 메소드이다.
	 * @param dest 
	 * 객체를 직렬화한 후에 쓰여질 스트림
	 * @param pkt 
	 * 직렬화를 하기위한 객체
	 * @throws IllegalArgumentException
	 * 넘어온 패킷 객체가 NULL이거나 스트림이 NULL이면 IllegalArgumentException을 던진다.
	 */
	abstract void serialize(OutputStream dest, Packet pkt) throws IllegalArgumentException;
	
	/**
	 * 역직렬화를 하기위해 InputStream을 넘겨주면 같이 넘겨준 패킷 객체를 이용하여 패킷 객체로 역직렬화를 한다.
	 * @param src
	 * 역직렬화를 하려고 하는 스트림
	 * @param pkt
	 * 역직렬화한 후, 객체를 담기위해 패킷 객체
	 * @throws IllegalArgumentException
	 * 넘어온 패킷 객체가 NULL이거나 스트림이 NULL이면 IllegalArgumentException 던진다.
	 */
	abstract void deSerialize(InputStream src, Packet pkt) throws IllegalArgumentException;
}
