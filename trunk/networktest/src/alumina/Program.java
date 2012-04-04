package alumina;

import java.io.*;
import java.net.*;

public class Program {
	static final String LocalIP = "113.198.80.209";
	static final int LocalPort = 30331;
	
	public static void main(String[] args){
		
		runServer();
		System.out.println("server is running.");
		//runClient();
		
		try{
			System.in.read();
		}
		catch(Exception ex){
			
		}
	}
	
	static void runServer(){
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					DatagramSocket conn = new DatagramSocket(new InetSocketAddress(LocalIP, LocalPort));
					DatagramPacket pkt = new DatagramPacket(new byte[0xFF], 0xFF);
					int u;
					while(true){
						byte[] buf = pkt.getData();
						for(u = 0; u < 0xFF; ++u)
							buf[u] = 0;
						conn.receive(pkt);
						byte[] data = pkt.getData();
						String val = new String(data, "UTF-8");
						System.out.println("(server) received: " + val);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		th.start();
	}
	
	static void runClient(){
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					SocketAddress addr = new InetSocketAddress(LocalIP, LocalPort);
					DatagramSocket conn = new DatagramSocket();
					
					DatagramPacket pkt = new DatagramPacket(new byte[0xFF], 0xFF);
					pkt.setSocketAddress(addr);
					
					while(true){
						conn.send(pkt);
						System.out.println("(client) sent bytes: " + pkt.getLength());
						Thread.sleep(1000);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		th.start();
	}
	
}
