package sod.common;

/**
 * 
 * @author MB
 *
 */
public interface ReceiveHandler {
	
	/**
	 * AccessManagerServer/AccessManager�� beginListenning�� ���� ����� �����忡 ���� ȣ��Ǵ� �޼ҵ�� �����ӿ�ũ�� �̿��ϴ� ����Ʈ����� ���ŵ� ��Ŷ�� �����Ѵ�.
	 * �������̽��̹Ƿ� ����ڰ� ����ϱ����ؼ��� �� �����Ǿ���Ѵ�.
	 * @param pkt
	 * �����ӿ�ũ�� �̿��ϴ� ����Ʈ����� �Ѱ��� ������ ��Ŷ
	 */
	void onReceive(Packet pkt);
}
