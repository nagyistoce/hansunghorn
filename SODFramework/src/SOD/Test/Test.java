package SOD.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import SOD.Common.Packet;
import SOD.Common.Serializer;
import SOD.Common.ThreadEx;
import SOD.Common.Transceiver;

public class Test {
	final static String ServerIP = "113.198.80.209";
	final static int ServerPort = 4207; 
	
	static Logable log;
	static{
		log = new ConsoleLog();
	}
	
	public static void testTransceiver(){		
		ThreadEx.invoke(null, new ActionEx() {			
			@Override
			public void work(Object arg) {
				log.write("thread invoked\n");
				Transceiver t = new Transceiver(new InetSocketAddress(ServerIP, 0), ServerPort);
				Packet p = new Packet();
				InetSocketAddress sender = t.receive(p);
				log.write(sender.getAddress().toString() + ":" + Integer.toString(sender.getPort()) + "\n");
				log.write(p.pop());
			}
		});
		
		ThreadEx.sleep(1000);
		Transceiver t = new Transceiver(new InetSocketAddress(ServerIP, ServerPort));
		Packet p = new Packet();
		p.push("trasceiver test...\n");
		boolean check = t.send(p);
		log.write(Boolean.toString(check)+"\n");
		log.write("sent msg...\n");
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testSerializer() {
		// TODO Auto-generated method stub
		Packet pkt = new Packet();
		Packet pkt2 = new Packet();
		ByteArrayInputStream input;
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			int u;

			pkt.signiture = -1;
			pkt.push(100);
			pkt.push(200L);
			pkt.push(1.0f);
			pkt.push(2.0);
			pkt.push("hello");

			byte[] bytes = new byte[0x10];
			for (u = 0; u < bytes.length; ++u)
				bytes[u] = (byte) u;
			pkt.push(bytes);

			for (u = 0; u < bytes.length; ++u)
				log.write(bytes[u] + " ");
			log.write("\n");

			Serializer se = new Serializer();
			se.serialize(output, pkt);
			log.write("\n");

			input = new ByteArrayInputStream(output.toByteArray());
			se.deserialize(input, pkt2);

			log.write(pkt.signiture);
			log.write("\n");
			
			while (pkt2.getElementCount() > 0) {
				if (!pkt2.getTopElementType()
						.equals(Packet.DataType_ByteArray))
					log.write(pkt2.pop() + "\n");
				else {
					byte[] result = (byte[]) pkt2.pop();
					for (u = 0; u < result.length; ++u)
						log.write(result[u] + " ");
					log.write("\n");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
