package SOD.SmartPhone;

import java.net.ConnectException;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;

/**
 * 
 * @author MB
 *
 */
public abstract class AccessManager {
	
	/**
	 * ������ ������ �õ��Ѵ�.
	 * @param info
	 * ������ ������ ����
	 * @throws  ConnectException
	 * �����ϴ� ������ ���� ���� ���, ConnectException�� ������.
	 * @throws IllegalStateException
	 * �̹� ����Ǿ� �ִ� ���¿��� dispose()�Ǳ� ���� connect()�� �ٽ� ȣ���ϸ� IllegalStateException�� ������.
	 * @throws IllegalArgumentException
	 * ServerInfo ��ü�� NULL�̸� IllegalArgumentException�� ������.
	 */
	public abstract void connect(ServerInfo info) throws ConnectException, IllegalStateException, IllegalArgumentException;

	/**
	 * ���� �������̿� �ִ� ������ ã�� ����� ��ȯ
	 * @return
	 * �߰ߵ� ���� ���
	 */
	public abstract ServerInfo[] searchServer();

	/**
	 * ������ ������ ���� dispose()�� ȣ���Ѵ�.
	 * @throws IllegalStateException
	 * connect()�� �Ǳ� ���� ȣ��Ǹ� IllegalStateException�� ������.
	 */
	public abstract void disconnect() throws IllegalStateException;

	/**
	 * Packet�� ���ŵ����� ȣ��� �ݹ��Լ��� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �ڵ鷯�� �ƴ� ���� �Ѱ��ָ� IllegalArgumentException�� ������.
	 */
	public abstract void setReceiveHandler(ReceiveHandler handler) throws IllegalArgumentException;

	/**
	 * ��Ŷ�� ������ ������.
	 * @param pkt
	 * ���� �����͸� ���� ��Ŷ
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�̸�, IllegalArgumentException�� ������.
	 * @throws IllegalStateException
	 * connect()�� ȣ��Ǳ� ���� send�� ȣ���ϸ� IllegalStateException�� ������.
	 * @throws SocketException
	 * �����ϴ� ������ �����ϸ� SocketException�� ������.
	 */
	public abstract void send(Packet pkt) throws IllegalArgumentException, IllegalStateException, SocketException;

	/**
	 * ������ ���� ���ŵ����͸� Ȯ���ϰ� ������ Ȯ�ο� ������ �۾� �����带 �����Ѵ�.
	 */
	protected abstract void beginListening();

	/**
	 * �����ϴ� �۾� �����带 �����ϰ� �Ҵ�� ��� ���ҽ��� ��ȯ�Ѵ�.
	 * terminate listening thread and release resources.
	 */
	protected abstract void dispose();
}
