package sod.beanImpl;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;

import sod.common.Packet;

public interface ServiceBeanImpl {
	
	public final static String DOWNLOADHTML="download";		//  Receive Questionnaire message data // ������ ���� ��������
	public void ReceiveData() throws IOException;
	public void SendData(String data) throws IOException;
	public void SendData(Packet pkt,JSONArray data) throws Exception;
}
