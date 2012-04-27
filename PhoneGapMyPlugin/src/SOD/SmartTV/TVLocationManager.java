package SOD.SmartTV;


/**
 * 
 * @author MB
 *
 */
abstract public class TVLocationManager {

	
	/**
	 * ���� ������ ��ġ�� �����ϰ� ��ġ���� ������ ������ �����Ѵ�.
	 * GPS�� ��� �������� ���� ��� handler�� �̿��� ���� ǥ���ϰ� �������� �����ϵ��� �Ѵ�.
	 * ��ġ�� �����Ǹ� beginReply�� ȣ���Ѵ�.
	 * @param info
	 * ������ ����
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� �Ѱ� �̻� null�� ��� �߻�
	 * @throws IllegalStateException
	 * �� �� �̻� �޼��尡 ȣ��ɶ� �߻�
	 */
	abstract public void setTVLocation(AdminInformation info, MapCallBackHandler handler) throws IllegalArgumentException, IllegalStateException;
	
	/**
	 * ���� �ð����� ���� ������ ��������� �˷��ִ� ��Ŷ�� �����ϴ� �۾� �����带 �����Ѵ�.
	 */
	abstract protected void beginReply();
	
	/**
	 * make notice to server which this tv is alive.
	 */
	abstract protected void replyToServer();

	/**
	 * send current tv location to central-server(which controls service points).
	 */
	abstract protected void sendTVLocationToServer();
}
