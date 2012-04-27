package SOD.SmartTV;

import SOD.Common.Packet;


/**
 * 
 * @author MB
 *
 */
abstract public class ServiceProvider {
	
	/**
	 * set files path where content files are stored.
	 * @param newpath
	 * ������ ���丮 ���
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	abstract void setPath(String newpath) throws IllegalArgumentException;
	
	/**
	 * ���� �����͸� ����ִ� ��Ŷ�� ��ȯ
	 * ���� ȣ��� createServicePacket()�� ���������� ȣ��ȴ�.
	 * @return
	 * ���� �����͸� ���� ��Ŷ
	 */
    public abstract Packet getServicePacket();
	
	/**
	 * create packet which contains every content files need to service.
	 * @return
	 * ���� �����͸� ���� ��Ŷ
	 * @throws IllegalStateException
	 * ���ο� ������ ��ΰ� ���ų� �߸��Ǿ��� ��� �߻�
	 */
	protected abstract Packet createServicePacket() throws IllegalStateException;
}
