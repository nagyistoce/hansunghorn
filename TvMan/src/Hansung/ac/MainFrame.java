package Hansung.ac;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import SOD.Common.Packet;
import SOD.Common.Transceiver;
import SOD.Test.ThreadEx;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainFrame extends Activity {
	EditText topic,answer;
	Button Fileadd,answerAdd,complete;
	RadioButton first,second;
	static final int LocalPort = 30331;
	static final String HostIP = "192.168.0.17";
	ByteArrayInputStream input;
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	private Packet pac=null;
	Transceiver t;
	
	public void Soketinitial() throws SocketException { // 소켓 초기화
	final SocketAddress address = new InetSocketAddress(HostIP, LocalPort);
	t= new Transceiver(address,LocalPort);
	
	}
	
	public void Interfaceinital() 
	{
		   topic=(EditText)findViewById(R.id.topic);
		   answer=(EditText)findViewById(R.id.answer);
		   Fileadd=(Button)findViewById(R.id.fileadd);
		   answerAdd=(Button)findViewById(R.id.addAnswer);
		   complete=(Button)findViewById(R.id.complete);
		   first=(RadioButton)findViewById(R.id.first);
		   second=(RadioButton)findViewById(R.id.second);
	}
	
	public void ActionPlease()
	{
		answerAdd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				DataStructure.vec.add("\nTopic\n"+topic.getText()+"\nAnswer\n"+answer.getText());
				topic.setEnabled(false);
			}
		});
		complete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DataStructure.vec.add("\nTopic\n"+topic.getText()+"\nAnswer\n"+answer.getText());
				DataStructure.vec.add("\nxxxxx\n");
				pac=new Packet();
				
				for(int i=0;i<DataStructure.vec.size();i++)
				{
					pac.push(DataStructure.vec.elementAt(i));
					
				}
				t.send(pac);
				pac=null;
				DataStructure.vec.removeAllElements();
				
			}
			
		});
		
		
	}
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	       setContentView(R.layout.tv_manager_service);
	        try{
	       Soketinitial();
	       Interfaceinital();
	       ActionPlease();
	        }catch(Exception e){Toast.makeText(this,""+e, Toast.LENGTH_LONG).show();}
	  }
private static String getLocalAddress() throws IOException { // 내 디바이스 IP
			// 받아오기
try {
for (Enumeration<NetworkInterface> en = NetworkInterface
.getNetworkInterfaces(); en.hasMoreElements();) {
NetworkInterface intf = en.nextElement();
for (Enumeration<InetAddress> enumIpAddr = intf
.getInetAddresses(); enumIpAddr.hasMoreElements();) {
InetAddress inetAddress = enumIpAddr.nextElement();
if (!inetAddress.isLoopbackAddress()) {
return inetAddress.getHostAddress().toString();
}
}
}
} catch (SocketException ex) {
	return ""+ex;
}
return "";
}


	    protected void onDestroy(){
	    	super.onDestroy();
	    }

}
