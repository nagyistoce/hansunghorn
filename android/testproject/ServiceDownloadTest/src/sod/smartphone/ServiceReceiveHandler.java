package sod.smartphone;

public interface ServiceReceiveHandler {
	/**
	 * ���� �����͸� ���������� ȣ��Ǵ� �ݹ��Լ�.
	 * @param serviceName
	 * ������ �̸�
	 */
	abstract void onReceive(String serviceName);
	
}
