package SOD.Common;


enum SerializableObjectType
{
    Int,
    Double,
    String,
    ByteArray,
}

/**
 * 
 * @author MB
 * a unit of transferring
 */
 abstract public class Packet {
	 
	 /**
	  * 스마트TV에서 스마트폰과 접속이 완료되고난 뒤에 스마트폰에 서비스를 실행하기위한 파일이 있는지를 확인한다.
	  *  이 때, 파일이 있는지를 확인하기 위해 서비스의 명칭을 보내기 위한 패킷의 시그니쳐 
	  */
	 public final int RESPONSE_SERVICE_NAME = 0xFF000000;
	 
	 /**
	  * 스마트TV에서 스마트폰의 접속상태가 유효한지를 체크하기위해 보내는 패킷의 시그니쳐
	  */
	 public final int REQUEST_CLIENT_ALIVE = 0xFF000001;
	 
	 /**
	  * 스마트TV로부터 접속상태 확인 패킷을 받은 후, 접속상태가 양호하다는 응답 패킷의 시그니쳐
	  */
	 public final int RESPONSE_CLIENT_ALIVE = 0xFF000002;
	 
	 /**
	  * 스마트TV로부터 서비스명칭을 받은 후, 서비스를 실행시키기위한 파일이 있는지를 확인하고 파일이 없을때 보내는 패킷의 시그니쳐
	  */
	 public final int REQUEST_SERVICE_DATA = 0xFF000003;

	 /**
	  * 스마트폰으로부터 서비스가 없다는 패킷을 받은 후, 서비스 실행과 관련된 파일을 보내주는 패킷의 시그니쳐
	  */
	 public final int RESPONSE_SERVICE_DATA = 0xFF000004;
		 
	
	 /**
	  * 개발자가 지정한 시그니쳐로 구분하여 패킷의 용도가 결정된다.
	  * 0xff000000~0xff000004는 시스템에서 할당한 시그니쳐이므로 개발자는 사용하면 안된다.
	  */
	public int signiture = 0;
	
	 /**
	  * 패킷에 들어있는 객체들의 개수를 반환한다. 패킷을 받은 후 처리하는 과정에서 사용하면 된다. 
	  * @return
	  * 패킷에 들어있는 객체의 개수
	  */
	abstract public int getElementCount();
	
	/**
	 * 패킷을 만들때, 패킷에 객체를 집어넣을 때 사용하는 메소드이다. 객체는 무한대로 들어간다.
	 * @param obj
	 * 사용자가 패킷에 집어넣고자 하는 객체
	 * @throws UnsupportedDataTypeException
	 * 패킷에서 push할 때, 지원이 되지않는 객체일 때는 UnsupportedDataTypeException을 던진다.
	 * 지원되는 객체의 타입은 Int, Double, String, ByteArray 등이다.
	 */
	abstract public void push(Object obj);
	
	/**
	 * 패킷에서 객체를 꺼낼때 사용하는 메소드이다. getElementCount()를 이용하여 개수를 파악하고 pop()을 이용하면 더욱 편리하게 이용 가능하다.
	 * @return
	 * 패킷에 담겨있던 객체 중에 제일 먼저 집어넣은 객체
	 * @throws NullPointerException
	 * 내부의 객체의 개수가 '0'이 되면 NullPointerException을 던진다.
	 */
	abstract public Object pop() throws NullPointerException;
	
}
