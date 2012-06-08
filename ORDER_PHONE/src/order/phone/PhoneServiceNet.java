package order.phone;

import java.io.IOException;

import order.bean.ConnectionBean;
import order.bean.DownLoad;
import sod.common.Packet;

import com.phonegap.api.Plugin;

public abstract class PhoneServiceNet extends Plugin {
	Packet packet;
	private final String ANSWERDATA="answerData";
	public PhoneServiceNet() {
		super();
	}
	public void ReceiveData() throws IOException {
		packet = new Packet();
		packet.push(DownLoad.DOWNLOADHTML);
		ConnectionBean.client.send(packet);
	}
	public void SendData(String data) throws IOException {
		packet = new Packet();
		packet.push(DownLoad.QUSTION);
		packet.push(data);		
		ConnectionBean.client.send(packet);
	}

}