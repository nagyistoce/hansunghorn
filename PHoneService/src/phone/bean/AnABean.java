package phone.bean;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import phone.beanImpl.ServiceBeanImpl;
import sod.common.Packet;

public class AnABean implements ServiceBeanImpl {
	public Packet packet;
	public final static String QUSTION="Questionnaire";
	public static Semaphore waithandle;		// Semaphore // ��������
	public static String Message="";
	public static String TempMessage="";
	
	static{
		waithandle = new Semaphore(1);		// �������� //Semaphore
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void ReceiveData() throws IOException {
		// TODO Auto-generated method stub
		packet = new Packet();
		packet.push(DOWNLOADHTML);
		ConnectionBean.client.send(packet);
	}
	
	public void SendData(String data) throws IOException {
		packet = new Packet();
		packet.push(QUSTION);
		packet.push(data);		
		ConnectionBean.client.send(packet);
		
	}		//  Send questionnaire answer message  // �亯 ���� ������ 
	
}
