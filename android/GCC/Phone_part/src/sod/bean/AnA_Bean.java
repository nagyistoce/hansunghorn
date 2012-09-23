package sod.bean;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;

import sod.beanImpl.ServiceBeanImpl;
import sod.common.Constants;
import sod.common.Packet;
import sod.service.ConnectionBean;

public class AnA_Bean implements ServiceBeanImpl {

	public final static String DOWNLOADHTML = "download"; // �ٿ�ε带 ���� Ȯ�� ��Ŷ�� ��
	public final static String QUSTION = "Questionnaire"; // ���� ��Ŷ

	public static String Message = ""; // �ӽ� ������ ���� �޼���
	public static int ClientId; // Ŭ���̾�Ʈ ���̵�
	public static String TempMessage = ""; // �ӽ� ������ ���庯��
	public static Semaphore waithandle; // ��Ʈ��ũ Ÿ�̹��� ���߱� ���� ��������

	public Packet packet;
	static {
		waithandle = new Semaphore(1);
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ReceiveData() throws IOException { // ���ú� Logic
		packet = new Packet();
		packet.push(DOWNLOADHTML);
		ConnectionBean.client.send(packet);

	}
	public void SendData(String data) throws IOException { // Send Logic
		// TODO Auto-generated method stub
		packet = new Packet();
		packet.push(QUSTION);
		packet.push(data);
		ConnectionBean.client.send(packet);

		Constants.logger.log("(debug:client) Succeed and disconnet");
		ConnectionBean.client.dispose();
	}
	public void SendData(Packet pkt, JSONArray data) throws Exception {
		// TODO Auto-generated method stub

	}

}
