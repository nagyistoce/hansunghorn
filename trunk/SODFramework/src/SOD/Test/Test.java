package SOD.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.Common.Serializer;
import SOD.Common.ThreadEx;
import SOD.Common.Transceiver;
import SOD.SmartPhone.AccessManager;
import SOD.SmartPhone.ServerInfo;
import SOD.SmartTV.AccessManagerServer;
import SOD.SmartTV.ConnectHandler;
import SOD.SmartTV.DisconnectHandler;
import SOD.SmartTV.ServerConfig;
import SOD.SmartTV.ServerReceiveHandler;

public class Test {
	final static String ServerIP = "127.0.0.1";
	final static int ServerPort = 3201; 
	
	static Logable log;
	static AccessManagerServer server; 
	static AccessManager client;
	static{
		log = new ConsoleLog();
	}
	
	public static void testAccessManager(){
		
		//server side
		server = new AccessManagerServer();
		ServerConfig conf = new ServerConfig();
		conf.Timeout = 30000;
		conf.Port = ServerPort;
		conf.CheckPeriod = 4000;
		conf.serviceName = "TestService";
		server.setConnectHandler(new ConnectHandler() {			
			@Override
			public void onConnect(int connid) {
				log.write("(server): new connection is accepted - " + connid + "\n");				
			}
		});
		server.setDisconnectHandler(new DisconnectHandler() {
			
			@Override
			public void onDisconnect(int connid) {
				log.write("(server): client is disconnected - " + connid + "\n");				
			}
		});
		server.setReceiveHandler(new ServerReceiveHandler() {			
			@Override
			public void onReceive(Packet pkt, int connid) {
				log.write("(server): a packet from client - " + connid + "\n");
				server.send(pkt, connid);
			}
		});
		server.start(conf);
		
		//client side
		client = new AccessManager();
		ServerInfo svinfo = new ServerInfo();
		svinfo.EndPoint = new InetSocketAddress(ServerIP, ServerPort);
		client.setReceiveHandler(new ReceiveHandler() {			
			@Override
			public void onReceive(Packet pkt) {
				log.write("(client): a packet from server" + (String)pkt.pop() + "\n");
			}
		});

		client.connect(svinfo);
		
		Packet pkt = new Packet();
		pkt.signiture = 1;
		pkt.push("a string on packet");
		client.send(pkt);
		
		//end of test		
		ThreadEx.sleep(1000);
		log.write("press any key...\n");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		client.dispose();
		server.shutdown();
		
		log.write("finishing test...\n");
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
				
				t.dispose();
			}
		});
		
		ThreadEx.sleep(1000);
		Transceiver t = new Transceiver(new InetSocketAddress(ServerIP, ServerPort));
		Packet p = new Packet();
		p.push("trasceiver test...\n");
		boolean check = t.send(p);
		log.write(Boolean.toString(check)+"\n");
		log.write("sent msg...\n");
		
		t.dispose();
		
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
			//pkt.push(200L);
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
