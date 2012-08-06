package sod.common;

import java.util.LinkedList;
import java.util.Queue;

import javax.activation.UnsupportedDataTypeException;

/**
 * 
 * @author MB a unit of transferring
 */

public class Packet {
	
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
	 * ����ƮTV���� ����Ʈ���� ������ �Ϸ�ǰ� �ڿ� ����Ʈ���� ���񽺸� �����ϱ����� ������ �ִ����� Ȯ���Ѵ�. �� ��, ������ �ִ�����
	 * Ȯ���ϱ� ���� ������ ��Ī�� ������ ���� ��Ŷ�� �ñ״���
	 */
	public static final int RESPONSE_SERVICE_NAME = 0xFF000000;

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
	


	protected Queue<Object> dataset;	
	
	/**
	 * �����ڰ� ������ �ñ״��ķ� �����Ͽ� ��Ŷ�� �뵵�� �����ȴ�. 0xff000000~0xff000004�� �ý��ۿ��� �Ҵ���
	 * �ñ״����̹Ƿ� �����ڴ� ����ϸ� �ȵȴ�.
	 */
	public int signiture = 0;
	
	public Packet(){
		dataset = new LinkedList<Object>();
	}

	/**
	 * ��Ŷ�� ����ִ� ��ü���� ������ ��ȯ�Ѵ�. ��Ŷ�� ���� �� ó���ϴ� �������� ����ϸ� �ȴ�.
	 * 
	 * @return ��Ŷ�� ����ִ� ��ü�� ����
	 */
	public int getElementCount(){
		return dataset.size();
	}
	
	public String getTopElementType(){
		if(dataset.size() == 0)
			return null;
		Object o = dataset.peek();
		
		if(o instanceof Integer)
			return DataType_Int;
//		if(o instanceof Long)
//			return DataType_Long;
		if(o instanceof Float)
			return DataType_Float;
		if(o instanceof Double)
			return DataType_Double;
		if(o instanceof String)
			return DataType_String;
		if(o instanceof byte[])
			return DataType_ByteArray;
		else
			return null;
	}

	/**
	 * ��Ŷ�� ���鶧, ��Ŷ�� ��ü�� ������� �� ����ϴ� �޼ҵ��̴�. ��ü�� ���Ѵ�� ����.
	 * 
	 * @param obj
	 *            ����ڰ� ��Ŷ�� ����ְ��� �ϴ� ��ü
	 * @throws UnsupportedDataTypeException
	 *             ��Ŷ���� push�� ��, ������ �����ʴ� ��ü�� ���� UnsupportedDataTypeException��
	 *             ������. �����Ǵ� ��ü�� Ÿ���� Int, Double, String, ByteArray ���̴�.
	 */
	public boolean push(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Integer ||
//		   obj instanceof Long ||
		   obj instanceof Float ||
		   obj instanceof Double ||
		   obj instanceof String ||
		   obj instanceof byte[])
			dataset.offer(obj);
		else
			return false;
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
		return dataset.poll();
	}

	/**
	 * ��Ŷ ��ü�� �����ϱ� ���� ��� ������ ����
	 */
	public void clear(){
		signiture = 0;
		while(dataset.size() > 0)
			dataset.poll();
	}
}


