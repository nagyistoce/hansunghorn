package SOD.Common;

import java.net.SocketException;

/**
 * 
 * @author MB
 * Make a connection between remote point
 */
abstract public class Transceiver {
	
	/**
	 * �Ѱ��� ��Ŷ ��ü�� ���(����ƮTV, ����Ʈ��)���� ������.
	 * @param pkt
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�� ��, IllegalArgumentException�� ������.
	 * @throws SocketException
	 * �����ϴ� ������ �����ϸ� SocketException�� ������.
	 */
	abstract public void send(Packet pkt) throws IllegalArgumentException, SocketException;
	
	/**
	 * ������ ������ ����� �ڵ鷯�� ����Ѵ�.
	 * @param handler
	 * ��Ŷ�� �������� ��, �����ӿ�ũ�� �̿��ϴ� ����Ʈ����� ��Ŷ�� �Ѱ��ִ� �ڵ鷯
	 * @throws IllegalArgumentException
	 * NULL�� �Ѱ��ָ�  IllegalArgumentException�� ������.
	 */
	abstract public void setReceiveHandler(ReceiveHandler handler) throws IllegalArgumentException;
	
	/**
	 * ����(����ƮTV, ����Ʈ��)���κ��� ��Ŷ�� ���Ź��� ��, ȣ��Ǵ� �޼ҵ��̴�.
	 * @param pkt
	 * ����(����ƮTV, ����Ʈ��)���κ��� ���� ��Ŷ
	 */
	protected abstract void onReceive(Packet pkt);  //protect
}
