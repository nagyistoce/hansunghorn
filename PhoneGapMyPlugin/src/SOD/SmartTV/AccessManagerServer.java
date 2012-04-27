package SOD.SmartTV;

import java.net.SocketException;

import SOD.Common.Packet;

/**
 * 
 * @author MB
 *
 */
abstract public class AccessManagerServer {

	/**
	 * ���� �ʱ�ȭ �� beginListening, beginCheckingConnection ȣ��
	 * @param conf
	 * ������ ����
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 * @throws IllegalStateException
	 * setReceiveHandler()�� ȣ����� �ʾҰų� �̹� ������ start()�� ȣ��Ǿ��ٸ� �߻�
	 */
	public abstract void start(ServerConfig conf) throws IllegalArgumentException, IllegalStateException;

	/**
	 * beginListening, beginCheckingConnection���� ������ �����带 �����ϰ�,
	 * �ݹ� �Լ��� ���� �����ϰ� �Ҵ�� ��� ���ҽ� ����.
	 * @throws IllegalStateException
	 * start()�� ȣ��Ǳ� ���� ȣ��ǰų� �ι� �̻� ȣ��� �� �߻�
	 */
	public abstract void shutdown() throws IllegalStateException;

	/**
	 * �� ������ �������� ȣ��Ǵ� �ݹ��Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public abstract void setConnectHandler(ConnectHandler handler) throws IllegalArgumentException;

	/**
	 * ������ �������� ȣ��Ǵ� �ݹ��Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public abstract void setDisconnectHandler(DisconnectHandler handler) throws IllegalArgumentException;

	/**
	 * ���� ������ ������ �����Ǿ� ������ Transceiver�� � ���̵� onReceive �ݹ��� ȣ��ɶ�
	 * ���޵� Packet�� �ش� Transceiver�� id�� �Ѱ��ִ� �ݹ� �Լ��� ����Ѵ�.
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public abstract void setReceiveHandler(ServerReceiveHandler handler) throws IllegalArgumentException;
	
	/**
	 * �÷��ǿ� ��ϵ� ���� ������ �����´�.
	 * @return
	 * ���� �پ��ִ� ���� ����
	 */
	public abstract int getConnectionCount();

	/**
	 * Send a Packet via Transceiver which matching connid.
	 * @param pkt
	 * �����͸� ���� ��Ŷ
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 * @throws IllegalArgumentException
	 * pkt�� null�̰ų� connid�� ��ȿ���� ���� ��� �߻�
	 * @throws IllegalStateException
	 * start()�� ȣ���ϱ� ���� ȣ��Ǿ��� ��� �߻�
	 * @throws SocketException
	 * ���� Transceiver ��ü ���� ���ܰ� �߻��� ����
	 */
	public abstract void send(Packet pkt, int connid) throws IllegalArgumentException, IllegalStateException, SocketException;

	/**
	 * �ش� id�� ��ġ�ϴ� Transceiver�� ������ ���� �÷��ǿ��� �����Ѵ�.
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 * @throws IllegalArgumentException
	 * connid�� ��ȿ���� ���� ��� �߻�
	 */
	public abstract void dropConnection(int connid) throws IllegalArgumentException;

	/**
	 * ���ο� ������ ����(Accept)�ϱ� ���� �۾� �����带 �����Ѵ�.
	 * ������ �ɶ����� ������ �÷��ǿ� ������ �߰��ȴ�.
	 */
	protected abstract void beginListening();

	/**
	 * ���� �÷��ǿ� �ִ� �� Transceiver���� �����ϸ� ���� ���˿� ��Ŷ�� ������ ���� �۾� �����带 �����Ѵ�.
	 * ���ŵ��� ��Ŷ�� ���������� ������ �ð��� ���� �ð��� ���̰� Timeout�� �ʰ��ϸ� dropConnection ȣ��.
	 */
	protected abstract void beginCheckingConnection();
	
	/**
	 * Ư�� Transceiver���� �� ������ �̸��� �����Ѵ�.
	 * @param name
	 * ������ �̸�
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 * @throws IllegalArgumentException
	 * String NULL�̰ų� ,connid�� ��ȿ���� ���� ��� �߻�
	 */
	protected abstract void sendServiceName(String name, int connid) throws IllegalArgumentException;

}
