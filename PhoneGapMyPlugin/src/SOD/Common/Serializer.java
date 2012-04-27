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
	 * ��Ŷ ��ü�� ���޹޾� ����ȭ�� �Ŀ� OutputStream�� �̿��Ͽ� OutputStream�� ����.
	 * ����ȭ�� Json�� �̿��Ͽ� �̷������.
	 * �۽��ϴ� ������ ����ҿ� �������� ȣ��Ǵ� �޼ҵ��̴�.
	 * @param dest 
	 * ��ü�� ����ȭ�� �Ŀ� ������ ��Ʈ��
	 * @param pkt 
	 * ����ȭ�� �ϱ����� ��ü
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�̰ų� ��Ʈ���� NULL�̸� IllegalArgumentException�� ������.
	 */
	abstract void serialize(OutputStream dest, Packet pkt) throws IllegalArgumentException;
	
	/**
	 * ������ȭ�� �ϱ����� InputStream�� �Ѱ��ָ� ���� �Ѱ��� ��Ŷ ��ü�� �̿��Ͽ� ��Ŷ ��ü�� ������ȭ�� �Ѵ�.
	 * @param src
	 * ������ȭ�� �Ϸ��� �ϴ� ��Ʈ��
	 * @param pkt
	 * ������ȭ�� ��, ��ü�� ������� ��Ŷ ��ü
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�̰ų� ��Ʈ���� NULL�̸� IllegalArgumentException ������.
	 */
	abstract void deSerialize(InputStream src, Packet pkt) throws IllegalArgumentException;
}
