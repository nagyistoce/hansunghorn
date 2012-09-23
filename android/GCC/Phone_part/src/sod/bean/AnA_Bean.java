package sod.bean;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;

import sod.beanImpl.ServiceBeanImpl;
import sod.common.Constants;
import sod.common.Packet;
import sod.service.ConnectionBean;

public class AnA_Bean implements ServiceBeanImpl {

	public final static String DOWNLOADHTML = "download"; // 다운로드를 위한 확인 패킷이 될
	public final static String QUSTION = "Questionnaire"; // 질문 패킷

	public static String Message = ""; // 임시 데이터 저장 메세지
	public static int ClientId; // 클라이언트 아이디
	public static String TempMessage = ""; // 임시 데이터 저장변수
	public static Semaphore waithandle; // 네트워크 타이밍을 맞추기 위한 세마포어

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
	public void ReceiveData() throws IOException { // 리시브 Logic
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
