package phone.beanImpl;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import sod.common.Packet;

public interface ServiceBeanImpl {
	
	public final static String DOWNLOADHTML="download";		//  Receive Questionnaire message data // 설문지 정보 가져오기
	public static String Service="";

	public void ReceiveData() throws IOException;
	public void SendData(String data) throws IOException;
}
