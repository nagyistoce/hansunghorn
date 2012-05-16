package answer_ask_Service.TV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

import SOD.Common.Packet;
import SOD.Common.Transceiver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;

public class QuestionnairesSettingSecond extends Activity implements DataSturct {

//	private static final int LOCAL_PORT = 30331;
//	private static final String PHONE_IP = "192.168.0.10";
	final static String SEPARATOR = "|";
	private EditText topic, question;
	private Button Fileadd, questionAdd, complete;
	private RadioButton long_answer, short_answer;
	private Packet packet = null;
	ProgressDialog dialog;
	String abc="";
	//final String[] 
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
	 }
	 return "";
	 }
	public void ButtonEvent() {
		questionAdd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				vector.add(SEPARATOR + "Topic" + SEPARATOR
						+ topic.getText() + SEPARATOR + "Answer" + SEPARATOR
						+ question.getText());
				topic.setEnabled(false);
				question.setText("");
				question.setFocusable(true);
			}
		});
		complete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//dialog  = ProgressDialog.show(QuestionnairesSettingSecond.this, "","잠시만 기다려 주세요 ...", true);
				
				vector.add(SEPARATOR + "Topic" + SEPARATOR
						+ topic.getText() + SEPARATOR + "Answer" + SEPARATOR
						+ question.getText());
				vector.add(SEPARATOR + "xxxxx" + SEPARATOR);
				packet = new Packet();

				for (int i = 0; i < vector.size(); i++) {
					packet.push(vector.elementAt(i));

				}
				ConnectionBean.server.send(packet,ConnectionBean.ClientId);
				//try{Thread.sleep(1000);dialog.dismiss();}catch(Exception e){}
				try{
					abc=""+getLocalAddress();
				}catch(Exception e){}
				question.setText(abc+ConnectionBean.ClientId);
				 
				packet = null; // 패킷 소멸
				vector.removeAllElements(); // 벡터 내용 지우기
				
			}

		});

	}

//	public void Soketinitial() throws SocketException { // 소켓 초기화
//		final SocketAddress address = new InetSocketAddress(PHONE_IP,
//				LOCAL_PORT);
//		transceiver = new Transceiver(address, LOCAL_PORT);
//
//	}

	public void InterfaceInital() {
		topic = (EditText) findViewById(R.id.topic);
		question = (EditText) findViewById(R.id.answer);
		Fileadd = (Button) findViewById(R.id.fileadd);
		questionAdd = (Button) findViewById(R.id.addAnswer);
		complete = (Button) findViewById(R.id.complete);
		long_answer = (RadioButton) findViewById(R.id.first);
		short_answer = (RadioButton) findViewById(R.id.second);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.questionnaire2);
//		try {
		
			InterfaceInital();
			ButtonEvent();
//		} catch (SocketException e) {
//			Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
//		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
