package sod.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class Test {
	
	final static Test Instance = new Test();
	final static ILog log = new ConsoleLog();  
	
	static SimpleChat create(){
		return Instance.new SimpleChat();
	}
	
	class SimpleReceiver extends ReceiverAdapter {

		public void viewAccepted(View v) {
		    log.print("** view: " + v);
		}

		public void receive(Message msg) {
			log.print(msg.getSrc() + ": " + msg.getObject());
		}
	}
	
	class SimpleChat {

	    JChannel channel;
	    String user_name = System.getProperty("user.name", "n/a");

	    public void start() throws Exception {
	        channel = new JChannel(); // use the default config, udp.xml
	        channel.setReceiver(new SimpleReceiver());
	        channel.connect("ChatCluster");	
	        loop();
	        channel.close();
	    }
	    
	    public void loop() {

			BufferedReader in = new BufferedReader(
					new InputStreamReader(System.in));

			while (true) {
				try {
					log.print("> ");
					log.flush();

					String line = in.readLine().toLowerCase();
					if (line.startsWith("quit") || line.startsWith("exit"))
						break;

					line = "[" + user_name + "] " + line;
					Message msg = new Message(null, null, line);
					channel.send(msg);
				} catch (Exception e) { }
			}
		}

	}
	
	public static void test() throws Exception{		
		SimpleChat chat = create();
		chat.start();
	}
}
