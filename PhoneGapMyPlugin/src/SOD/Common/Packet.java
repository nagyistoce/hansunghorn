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
	  * ����ƮTV���� ����Ʈ���� ������ �Ϸ�ǰ� �ڿ� ����Ʈ���� ���񽺸� �����ϱ����� ������ �ִ����� Ȯ���Ѵ�.
	  *  �� ��, ������ �ִ����� Ȯ���ϱ� ���� ������ ��Ī�� ������ ���� ��Ŷ�� �ñ״��� 
	  */
	 public final int RESPONSE_SERVICE_NAME = 0xFF000000;
	 
	 /**
	  * ����ƮTV���� ����Ʈ���� ���ӻ��°� ��ȿ������ üũ�ϱ����� ������ ��Ŷ�� �ñ״���
	  */
	 public final int REQUEST_CLIENT_ALIVE = 0xFF000001;
	 
	 /**
	  * ����ƮTV�κ��� ���ӻ��� Ȯ�� ��Ŷ�� ���� ��, ���ӻ��°� ��ȣ�ϴٴ� ���� ��Ŷ�� �ñ״���
	  */
	 public final int RESPONSE_CLIENT_ALIVE = 0xFF000002;
	 
	 /**
	  * ����ƮTV�κ��� ���񽺸�Ī�� ���� ��, ���񽺸� �����Ű������ ������ �ִ����� Ȯ���ϰ� ������ ������ ������ ��Ŷ�� �ñ״���
	  */
	 public final int REQUEST_SERVICE_DATA = 0xFF000003;

	 /**
	  * ����Ʈ�����κ��� ���񽺰� ���ٴ� ��Ŷ�� ���� ��, ���� ����� ���õ� ������ �����ִ� ��Ŷ�� �ñ״���
	  */
	 public final int RESPONSE_SERVICE_DATA = 0xFF000004;
		 
	
	 /**
	  * �����ڰ� ������ �ñ״��ķ� �����Ͽ� ��Ŷ�� �뵵�� �����ȴ�.
	  * 0xff000000~0xff000004�� �ý��ۿ��� �Ҵ��� �ñ״����̹Ƿ� �����ڴ� ����ϸ� �ȵȴ�.
	  */
	public int signiture = 0;
	
	 /**
	  * ��Ŷ�� ����ִ� ��ü���� ������ ��ȯ�Ѵ�. ��Ŷ�� ���� �� ó���ϴ� �������� ����ϸ� �ȴ�. 
	  * @return
	  * ��Ŷ�� ����ִ� ��ü�� ����
	  */
	abstract public int getElementCount();
	
	/**
	 * ��Ŷ�� ���鶧, ��Ŷ�� ��ü�� ������� �� ����ϴ� �޼ҵ��̴�. ��ü�� ���Ѵ�� ����.
	 * @param obj
	 * ����ڰ� ��Ŷ�� ����ְ��� �ϴ� ��ü
	 * @throws UnsupportedDataTypeException
	 * ��Ŷ���� push�� ��, ������ �����ʴ� ��ü�� ���� UnsupportedDataTypeException�� ������.
	 * �����Ǵ� ��ü�� Ÿ���� Int, Double, String, ByteArray ���̴�.
	 */
	abstract public void push(Object obj);
	
	/**
	 * ��Ŷ���� ��ü�� ������ ����ϴ� �޼ҵ��̴�. getElementCount()�� �̿��Ͽ� ������ �ľ��ϰ� pop()�� �̿��ϸ� ���� ���ϰ� �̿� �����ϴ�.
	 * @return
	 * ��Ŷ�� ����ִ� ��ü �߿� ���� ���� ������� ��ü
	 * @throws NullPointerException
	 * ������ ��ü�� ������ '0'�� �Ǹ� NullPointerException�� ������.
	 */
	abstract public Object pop() throws NullPointerException;
	
}
