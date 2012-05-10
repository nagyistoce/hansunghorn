package answer_ask_Service.TV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import SOD.Common.Packet;
import SOD.Common.Transceiver;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class QuestionnairesMain extends Activity implements DataSturct {

	private static final int LOCAL_PORT = 30331;
	private static final String PHONE_IP = "192.168.0.10";
	final static String SEPARATOR = "|";
	private EditText topic, question;
	private Button Fileadd, questionAdd, complete;
	private RadioButton long_answer, short_answer;

	private Packet packet = null;
	private Transceiver transceiver;

	public void ButtonEvent() {
		questionAdd.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				vector.add(SEPARATOR + "Topic" + SEPARATOR
						+ topic.getText() + SEPARATOR + "Answer" + SEPARATOR
						+ question.getText());
				topic.setEnabled(false);
			}
		});
		complete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vector.add(SEPARATOR + "Topic" + SEPARATOR
						+ topic.getText() + SEPARATOR + "Answer" + SEPARATOR
						+ question.getText());
				vector.add(SEPARATOR + "xxxxx" + SEPARATOR);
				packet = new Packet();

				for (int i = 0; i < vector.size(); i++) {
					packet.push(vector.elementAt(i));

				}
				transceiver.send(packet);

				packet = null; // 패킷 소멸
				vector.removeAllElements(); // 벡터 내용 지우기

			}

		});

	}

	public void Soketinitial() throws SocketException { // 소켓 초기화
		final SocketAddress address = new InetSocketAddress(PHONE_IP,
				LOCAL_PORT);
		transceiver = new Transceiver(address, LOCAL_PORT);

	}

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

		setContentView(R.layout.tv_manager_service);
		try {
			Soketinitial();
			InterfaceInital();
			ButtonEvent();
		} catch (SocketException e) {
			Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
