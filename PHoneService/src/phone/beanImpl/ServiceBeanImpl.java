package phone.beanImpl;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import sod.common.Packet;

public interface ServiceBeanImpl {
	
	public final static String DOWNLOADHTML="download";		//  Receive Questionnaire message data // ������ ���� ��������
	public static String Service="";

	public void ReceiveData() throws IOException;
	public void SendData(String data) throws IOException;
}
