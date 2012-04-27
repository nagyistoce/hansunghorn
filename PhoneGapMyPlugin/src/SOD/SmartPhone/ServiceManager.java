package SOD.SmartPhone;

import java.net.SocketException;


/**
 * 
 * @author MB
 *
 */
abstract public class ServiceManager {

	/**
	 * ���񽺰� ��ġ�Ǿ� �ִ��� Ȯ��
	 * @param serviceName
	 * ���� �̸�
	 * @return
	 * ���񽺰� ��ġ�Ǿ� ������ true �ƴϸ� false
	 * @throws IllegalArgumentException
	 * �ŰԺ����� null�� �� IllegalArgumentException�� �߻��Ѵ�.
	 */
	abstract public boolean findServiceByName(String serviceName) throws IllegalArgumentException;

	/**
	 * �ش� �̸��� ���� ����
	 * @param serviceName
	 * ���� �̸�
	 * @throws IllegalArgumentException
	 * �Ű������� null�̰ų� �ش� �̸��� ���񽺰� ��ġ���� ���� ��� �߻�
	 * @throws IllegalStateException
	 * �ι� �̻� ȣ��� �� �߻�.
	 */
	abstract public void startService(String serviceName) throws IllegalArgumentException, IllegalStateException;

	/**
	 * ��ġ�Ǿ� �ִ� ���� ��� ��ȯ
	 * @return
	 * ���� ���
	 */
	abstract public String[] getServiceList();

	/**
	 * Ư�� ���񽺸� ����
	 * @param serviceName
	 * ���� �̸�
	 * @throws IllegalArgumentException
	 * �Ű������� null�̰ų� �ش� serviceName�� ��ġ�ϴ� ���� �����Ͱ� ���� ��� �߻�.
	 */
	abstract public void removeService(String serviceName) throws IllegalArgumentException;

	/**
	 * ���� �ٿ�ε�� ȣ���� �ݹ� �Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�� ��� �߻�.
	 */
	abstract public void setSeriveReceiveHandler(ServiceReceiveHandler handler) throws IllegalArgumentException;

	/**
	 * ���񽺰� ������ �ٿ�ε� ��û
	 * ���� �����͸� ��û�ϴ� ��Ŷ ���� ����
	 * @param acc
	 * �����͸� �ٿ�ε��ϱ� ���� ���Ǵ� ������ ����� AccessManager ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�̰ų� send�� ȣ�� �� �� ���� ������ ��� �߻�.
	 * @throws SocketException
	 * �Ѱ��� AccessManager ��ü ���� ���ܰ� �߻��� ����
	 */
	abstract public void requestService(AccessManager acc) throws IllegalArgumentException, SocketException;
	
}
