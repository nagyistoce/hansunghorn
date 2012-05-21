package ana.phone;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.Transceiver;


import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;

import com.phonegap.api.Plugin;

public abstract class AnAServiceNet extends Plugin {
	Packet packet;
	private final String ANSWERDATA="answerData";
	public AnAServiceNet() {
		super();
	}
	public void ReceiveData() throws IOException {
		packet = new Packet();
		packet.push(DownLoad.DOWNLOADHTML);
		ConnectionBean.client.send(packet);
	}
	public void SendData(String data) throws IOException {
		packet = new Packet();
	//	packet.push(answerData);
		packet.push(data);		
		ConnectionBean.client.send(packet);
	}

}