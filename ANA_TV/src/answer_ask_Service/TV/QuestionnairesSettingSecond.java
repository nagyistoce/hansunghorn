package answer_ask_Service.TV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import SOD.Common.Packet;
import SOD.Common.Transceiver;
import SOD.SmartTV.AccessManagerServer;
import SOD.SmartTV.ConnectHandler;
import SOD.SmartTV.DisconnectHandler;
import SOD.SmartTV.ServerConfig;
import SOD.SmartTV.ServerReceiveHandler;
import SOD.Test.ConsoleLog;
import SOD.Test.Logable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.*;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup.*;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;
import answer_ask_BeanTV.LayoutComponentBean;

public class QuestionnairesSettingSecond extends Activity implements DataSturct {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private String[] items = { "양자택일", "삼자택일", "사자택일", "오자택일" };
	private int a = 0;

	// final String[]
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

	public void TVServerIni() {
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig = new ServerConfig();
		ConnectionBean.ServerConfig.Timeout = 30000;
		ConnectionBean.ServerConfig.Port = ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "A&A_Service";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			public void onConnect(int connid) {
				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "접속했음",
				// Toast.LENGTH_LONG).show();
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			public void onDisconnect(int connid) {
				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "접속 끈김",
				// Toast.LENGTH_LONG).show();

			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {

			public void onReceive(Packet pkt, int connid) {
				ConnectionBean.ClientId = connid;
				Packet packet;
				if (pkt != null) {
					while (pkt.getElementCount() > 0) {
						ConnectionBean.Message = pkt.pop().toString();
						if (ConnectionBean.Message.equals("download")) {
							packet = new Packet();
							for (int i = 0; i < DataSturct.vector.size(); i++) {
								packet.push(DataSturct.vector.elementAt(i));

							}
							ConnectionBean.server.send(packet,
									ConnectionBean.ClientId);
						}
					}
					// packet=null;
				}
			}
		});

	}

	public void ButtonEvent() {
		LayoutComponentBean.radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId != -1) {

							RadioButton rb = (RadioButton) findViewById(checkedId);

							if (rb.getText().equals("객관식")) {
								Toast.makeText(
										QuestionnairesSettingSecond.this,
										rb.getText(), Toast.LENGTH_SHORT)
										.show();
								new AlertDialog.Builder(
										QuestionnairesSettingSecond.this)
										.setTitle("Select Question count")
										.setSingleChoiceItems(
												items,
												-1,
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														LayoutComponentBean.choice = which;

													}
												})
										.setNegativeButton("확인", null).show();
							}
							else
							{
								LayoutComponentBean.choice=-1;
							}
						}
					}
				});
		LayoutComponentBean.questionAdd
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						vector.add(LayoutComponentBean.SEPARATOR + "Topic"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.topic.getText()
								+ LayoutComponentBean.SEPARATOR + "Answer"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.question.getText()
								+ LayoutComponentBean.SEPARATOR + "Choice"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.choice);

						list.add(""
								+ ++a
								+ ". "
								+ LayoutComponentBean.question.getText()
										.toString());
						LayoutComponentBean.topic.setEnabled(false);
						LayoutComponentBean.question.setText("");
						LayoutComponentBean.question.setFocusable(true);
						adapter.notifyDataSetChanged();
					}

				});
		LayoutComponentBean.listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String str = "문항  = " + list.get(position);
						Toast.makeText(QuestionnairesSettingSecond.this, str,
								Toast.LENGTH_LONG).show();
					}

				});

		LayoutComponentBean.complete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vector.add(LayoutComponentBean.SEPARATOR + "Topic"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.topic.getText()
						+ LayoutComponentBean.SEPARATOR + "Answer"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.question.getText()
						+ LayoutComponentBean.SEPARATOR + "Choice"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.choice);
				vector.add(LayoutComponentBean.SEPARATOR + "xxxxx"
						+ LayoutComponentBean.SEPARATOR);

				if (LayoutComponentBean.ScreenCount == 0) {
					TVServerIni();
					ConnectionBean.server.start(ConnectionBean.ServerConfig);
					LayoutComponentBean.ScreenCount++;
				}

				LayoutComponentBean.statisticsGraph_btn.setEnabled(true);
				LayoutComponentBean.QuestionnaireImfo_btn.setEnabled(true);
				LayoutComponentBean.QuestionnaireInitial_btn
						.setText("설문지 재 설정");
				Intent intent = new Intent(QuestionnairesSettingSecond.this,
						AnA_BootMode.class);
				startActivity(intent);
			}

		});

	}

	public void InterfaceInital() {
		LayoutComponentBean.radiogroup = (RadioGroup) findViewById(R.id.radioGroup1);
		LayoutComponentBean.topic = (EditText) findViewById(R.id.topic);
		LayoutComponentBean.question = (EditText) findViewById(R.id.answer);
		LayoutComponentBean.Fileadd = (Button) findViewById(R.id.fileadd);
		LayoutComponentBean.questionAdd = (Button) findViewById(R.id.addAnswer);
		LayoutComponentBean.complete = (Button) findViewById(R.id.complete);
		LayoutComponentBean.long_answer = (RadioButton) findViewById(R.id.first);
		LayoutComponentBean.short_answer = (RadioButton) findViewById(R.id.second);
		LayoutComponentBean.listview = (ListView) findViewById(R.id.listview);
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(QuestionnairesSettingSecond.this,
				R.layout.textstyle, list);
		LayoutComponentBean.listview.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionnaire2);
		InterfaceInital();
		ButtonEvent();

	}


}
