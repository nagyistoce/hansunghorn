package sod.common;


import java.util.LinkedList;
import java.util.Queue;


/**
 * 
 * @author MB a unit of transferring
 */

public class Packet implements Cloneable {
	
	public static final String DataType_Int = "int";
//	public static final String DataType_Long = "long";
	public static final String DataType_Float = "float";
	public static final String DataType_Double = "double";
	public static final String DataType_String = "string";
	public static final String DataType_ByteArray = "bytearray";
	
	/*
	 * Ŭ���̾�Ʈ���� ������ ��û�ϴ� ��Ŷ �ñ״���
	*/
	public static final int REQUEST_ACCEPT = 0xFF000010;
	
	/*
	 * �������� ������ ���������� �˷��ִ� ��Ŷ �ñ״���
	*/
	public static final int RESPONSE_ACCEPT = 0xFF000011;
	
	/*
	 * ������ �����ϴ��� �˱� ���� Ŭ���̾�Ʈ���� ������ ��Ŷ �ñ״���
	*/
	public static final int REQUEST_PING = 0xFF000020;
	
	/*
	 * Ŭ���̾�Ʈ���� ��û�� �ο� �����ϴ� ��Ŷ �ñ״���
	*/
	public static final int RESPONSE_PING = 0xFF000021;
	

	/**
	 * ����ƮTV���� ����Ʈ���� ���ӻ��°� ��ȿ������ üũ�ϱ����� ������ ��Ŷ�� �ñ״���
	 */
	public static final int REQUEST_CLIENT_ALIVE = 0xFF000001;

	/**
	 * ����ƮTV�κ��� ���ӻ��� Ȯ�� ��Ŷ�� ���� ��, ���ӻ��°� ��ȣ�ϴٴ� ���� ��Ŷ�� �ñ״���
	 */
	public static final int RESPONSE_CLIENT_ALIVE = 0xFF000002;

	/**
	 * ����ƮTV�κ��� ���񽺸�Ī�� ���� ��, ���񽺸� �����Ű������ ������ �ִ����� Ȯ���ϰ� ������ ������ ������ ��Ŷ�� �ñ״���
	 */
	public static final int REQUEST_SERVICE_DATA = 0xFF000003;

	/**
	 * ����Ʈ�����κ��� ���񽺰� ���ٴ� ��Ŷ�� ���� ��, ���� ����� ���õ� ������ �����ִ� ��Ŷ�� �ñ״���
	 */
	public static final int RESPONSE_SERVICE_DATA = 0xFF000004;
	
	/**
	 * ���� ������ ��� �Ϸ�ƴٴ� ��Ŷ�� �ñ״���
	 */
	public static final int RESPONSE_SERVICE_DATA_END = 0xFF000005;
	
	/**
	 * ����Ʈ���� ����ƮTV�� ���� ��Ŷ�� �� �޾Ҵٴ� ��Ŷ�� �ñ״���
	 */
	public static final int RESPONSE_SERVICE_DATA_ARK = 0xFF000006;

	
	protected LinkedList<String> typeset;
	protected LinkedList<Object> dataset;
	
	
	/**
	 * �����ڰ� ������ �ñ״��ķ� �����Ͽ� ��Ŷ�� �뵵�� �����ȴ�. 0xff000000~0xff000004�� �ý��ۿ��� �Ҵ���
	 * �ñ״����̹Ƿ� �����ڴ� ����ϸ� �ȵȴ�.
	 */
	public int signiture = 0;
	
	public Packet(){
		typeset = new LinkedList<String>();
		dataset = new LinkedList<Object>();
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() throws CloneNotSupportedException{
		Packet returnPkt = (Packet)super.clone();
		returnPkt.typeset = (LinkedList<String>)typeset.clone();
		returnPkt.dataset = (LinkedList<Object>)dataset.clone();
		return returnPkt;
	}

	/**
	 * ��Ŷ�� ����ִ� ��ü���� ������ ��ȯ�Ѵ�. ��Ŷ�� ���� �� ó���ϴ� �������� ����ϸ� �ȴ�.
	 * 
	 * @return ��Ŷ�� ����ִ� ��ü�� ����
	 */
	public int getElementCount(){
		return dataset.size();
	}
	
	public String getTopElementType() {
		if (dataset.size() == 0)
			return null;
		Object o = dataset.peek();

		typeset.peek();

		if (o instanceof Integer)
			return DataType_Int;
		// if(o instanceof Long)
		// return DataType_Long;
		if (o instanceof Float)
			return DataType_Float;
		if (o instanceof Double)
			return DataType_Double;
		if (o instanceof String)
			return DataType_String;
		if (o instanceof byte[])
			return DataType_ByteArray;
		else
			return null;

	}

	/**
	 * ��Ŷ�� ���鶧, ��Ŷ�� ��ü�� ������� �� ����ϴ� �޼ҵ��̴�. ��ü�� ���Ѵ�� ����.
	 * 
	 * @param item
	 *            ����ڰ� ��Ŷ�� ����ְ��� �ϴ� ��ü
	 * @throws UnsupportedDataTypeException
	 *             ��Ŷ���� push�� ��, ������ �����ʴ� ��ü�� ���� UnsupportedDataTypeException��
	 *             ������. �����Ǵ� ��ü�� Ÿ���� Int, Double, String, ByteArray ���̴�.
	 */
	public boolean push(Object item) {
		if(item == null) return false;
		if(item instanceof Integer ||
//		   obj instanceof Long ||
		   item instanceof Float ||
		   item instanceof Double ||
		   item instanceof String ||
		   item instanceof byte[])
			dataset.offer(item);
		else
			return false;
		return true;
	}
	
	/**
	 * ��Ŷ�� ���鶧, ��Ŷ�� ��ü�� ������� �� ����ϴ� �޼ҵ��̴�. ��ü�� ���Ѵ�� ����
	 * @param type ��ü�� Ÿ��
	 * @param item ����ڰ� ��Ŷ�� ����ְ��� �ϴ� ��ü
	 * @return
	 */
	public boolean push(Object item, String type){
		
		typeset.offer(type);
		dataset.offer(item);
		
		return true;
	}

	/**
	 * ��Ŷ���� ��ü�� ������ ����ϴ� �޼ҵ��̴�. getElementCount()�� �̿��Ͽ� ������ �ľ��ϰ� pop()�� �̿��ϸ� ����
	 * ���ϰ� �̿� �����ϴ�.
	 * 
	 * @return ��Ŷ�� ����ִ� ��ü �߿� ���� ���� ������� ��ü
	 * @throws NullPointerException
	 *             ������ ��ü�� ������ '0'�� �Ǹ� IllegalStateException�� ������.
	 */
	public Object pop() {
		if(dataset.size() == 0)
			return null;
		
		typeset.poll();
		return dataset.poll();
	}
	
	/**
	 * ��Ŷ���� ��ü�� ������ ����ϴ� �޼ҵ��̴�.
	 * type�� �����Ǵ� Object�� ��ȯ�Ѵ�.
	 * @param type
	 * @return type�� �����Ǵ� Object, ������ -1 ��ȯ
	 */
	public Object pick(String type){
		//�ٽ� �����ؾ���
		int index = typeset.indexOf(type);
		if( index == -1)
			return null;
		else{
			typeset.remove(index);
			return dataset.remove(index);
		}
		
	}

	/**
	 * ��Ŷ ��ü�� �����ϱ� ���� ��� ������ ����
	 */
	public void clear(){
		signiture = 0;
		
		while(typeset.size() > 0)
			typeset.poll();
		
		while(dataset.size() > 0)
			dataset.poll();
	
	}
}


