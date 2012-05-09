package ana.phone;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.Transceiver;

import com.phonegap.api.Plugin;

public abstract class AnAServiceNet extends Plugin {
	static String Message;
	static final int LOCALPORT = 30331;
	static final String HOSTIP = "192.168.0.17";

	protected int flag = 0;
	private Packet packet;
	private Transceiver transceiver;

	public AnAServiceNet() {
		super();
	}

	public void Soketinitial() throws SocketException { // 소켓 초기화
		final SocketAddress address = new InetSocketAddress(HOSTIP, LOCALPORT);
		transceiver = new Transceiver(address, LOCALPORT);

	}

	public void getData() throws IOException {
		packet = new Packet();
		boolean check = transceiver.receive(packet);
		if (check) {
			for (int i = 0; i < packet.getElementCount() + 1; i++) {
				Message += packet.pop().toString();
			}
		}
		packet = null;
		return;
	}

}