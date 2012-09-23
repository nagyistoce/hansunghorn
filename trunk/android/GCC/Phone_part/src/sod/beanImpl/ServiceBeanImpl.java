package sod.beanImpl;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;

import sod.common.Packet;

public interface ServiceBeanImpl {			// 서비스별 제공을 위한 인터페이스
	
	public final static String DOWNLOADHTML="download";		//  Receive  message data // 
	public void ReceiveData() throws IOException;
	public void SendData(String data) throws IOException;
	public void SendData(Packet pkt,JSONArray data) throws Exception;
}
