package SOD.SmartPhone;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;

/**
 * 
 * @author MB
 *
 */
public abstract class AccessManager {
	
	ServerInfo svinfo;
	
	/**
	 * ������ ������ �õ��Ѵ�.
	 * @param info
	 * ������ ������ ����
	 */
	public void connect(ServerInfo info){
		this.svinfo = info;
		
	}

	/**
	 * ���� �������̿� �ִ� ������ ã�� ����� ��ȯ
	 * @return
	 * �߰ߵ� ���� ���
	 */
	public abstract ServerInfo[] searchServer();

	/**
	 * ������ ������ ���� dispose()�� ȣ���Ѵ�.
	 */
	public abstract void disconnect();

	/**
	 * Packet�� ���ŵ����� ȣ��� �ݹ��Լ��� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 */
	public abstract void setReceiveHandler(ReceiveHandler handler);

	/**
	 * ��Ŷ�� ������ ������.
	 * @param pkt
	 * ���� �����͸� ���� ��Ŷ
	 */
	public abstract void send(Packet pkt);

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
