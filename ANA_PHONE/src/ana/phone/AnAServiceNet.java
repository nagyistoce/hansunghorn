package ana.phone;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.Common.Transceiver;

import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;

import com.phonegap.api.Plugin;

public abstract class AnAServiceNet extends Plugin {
	Packet packet;
	public AnAServiceNet() {
		super();
	}
	public void getHTMLData() throws IOException {
		packet = new Packet();
		packet.push(DownLoad.DOWNLOADHTML);
		ConnectionBean.client.send(packet);
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {

			public void onReceive(Packet pkt) {
				if (pkt != null) {
					for (int i = 0; i < pkt.getElementCount(); i++) {
						DownLoad.Message += pkt.pop().toString();
					}
				}
			}
		});
		return;
	}

}