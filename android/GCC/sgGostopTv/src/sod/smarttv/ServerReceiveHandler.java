package sod.smarttv;

import sod.common.Packet;

public interface ServerReceiveHandler {
	/**
	 * ��Ŷ�� �����ҋ� ȣ��Ǵ� �ݹ��Լ�.
	 * @param pkt
	 * ���ŵ� �����͸� ���� ��Ŷ
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 */
	void onReceive(Packet pkt, int connid);
}
