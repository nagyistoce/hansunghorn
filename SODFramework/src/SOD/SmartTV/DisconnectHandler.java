package SOD.SmartTV;

public interface DisconnectHandler {
	/**
	 * ������ �������� ȣ��Ǵ� �ݹ��Լ�.
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 */
	abstract void onDisconnect(int connid);
}
