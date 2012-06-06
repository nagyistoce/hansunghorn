package answer_ask_Service.TV;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

import sod.common.Packet;
import sod.common.Storage;
import sod.common.StorageFile;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;
import answer_ask_BeanTV.DataBean;
import answer_ask_BeanTV.DataSturct;
import answer_ask_BeanTV.FileBean;
import answer_ask_BeanTV.LayoutComponentBean;

import answer_ask_Service.TV.R;

public class QuestionnairesSettingSecond extends Activity implements DataSturct {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private String[] items = { "Yes or No", "Good Nomal bad",
			"VeryGood Good Bad VeryBad", "VeryGood Good Nomal Bad VeryBad" };
	private int a = 0;
	private Storage storage;
	private StorageFile storageFile;
	int count = 0;

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
		ConnectionBean.ServerConfig.CheckingPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "A&A_Service";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			@Override
			public void onConnect(int connid) {

				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "접속했음",
				// Toast.LENGTH_LONG).show();
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			@Override
			public void onDisconnect(int connid) {
				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "접속 끈김",
				// Toast.LENGTH_LONG).show();

			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {

			@Override
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
						// else if(ConnectionBean.Message.equals("answerData"))
						// {
						else if (ConnectionBean.Message.equals("Questionnaire")) {
							ConnectionBean.Message = pkt.pop().toString();
							DataBean.Message += ConnectionBean.Message;
							try {
								DataBean.Message=DataBean.Message.substring(2);
								ReArrange();

							} catch (Exception e) {
							}

						}
						// }

					}
				}

			}
		});

	}

	public void Store() throws Exception {
		if (Storage.checkIsStorageExists("ana")) {
			storage = Storage.getStorage("ana");
		} else
			storage = Storage.createStorage("ana");

	}

	public void ReArrange() throws Exception {
		String key="",value="";
		Store();
		if (!storage.checkIsFileExists("data.txt")) {
			storageFile = storage.createFile("data.txt");
		} else {
			storageFile = storage.openFile("data.txt", Storage.WRITE);
		}
		String str = "";
		for (int i = 0; i < DataSturct.vector.size(); i++) {
			str += DataSturct.vector.get(i);
		}
		StringTokenizer tokenizer1 = new StringTokenizer(str,
				LayoutComponentBean.SEPARATOR);
		StringTokenizer tokenizer2 = new StringTokenizer(DataBean.Message,
				LayoutComponentBean.SEPARATOR);
		
		
		storageFile.write("<Topic>".getBytes());
		storageFile.write(FileBean.Topic.getBytes());
		storageFile.write("</Topic>".getBytes());
		storageFile.write("\n".getBytes());
		while (tokenizer1.hasMoreElements()) {
			if(tokenizer1.nextToken().equals("Answer"))
			{
				DataBean.AnswerCount++;
				storageFile.write("  ".getBytes());	
				storageFile.write("<Answer>".getBytes());
				key=tokenizer1.nextToken();
				storageFile.write(key.getBytes());
				storageFile.write("</Answer>".getBytes());
				storageFile.write("\n".getBytes());
				storageFile.write("    ".getBytes());			
				storageFile.write("<".getBytes());
				value=tokenizer2.nextToken();
				String count=(ValueCount(key,value));
				storageFile.write(value.getBytes());
				storageFile.write(">".getBytes());
				storageFile.write(count.getBytes());
				storageFile.write("</".getBytes());
				storageFile.write(value.getBytes());
				storageFile.write(">".getBytes());
				storageFile.write("\n".getBytes());
			}
		}
		DataBean.Message="";
		storageFile.close();
	}
	
	public String ValueCount(String key,String value) throws Exception
	{
		int  In_value=0;
		HashMap<String, Integer> hash=new HashMap<String, Integer>();
		DataBean.data_index=0;
		if(FileBean.hashmap.isEmpty() || !((FileBean.hashmap.containsKey(key)&&(FileBean.hashmap.get(key).containsKey(value)))))
		{
			In_value=1;
		    hash.put(value,(Integer)In_value); 
			FileBean.hashmap.put(key,hash);
		}
		else 
		{
			In_value=FileBean.hashmap.get(key).get(value);
			In_value++;
		    hash.put(value,In_value); 
			FileBean.hashmap.put(key,hash);
			
		}
		
		return ""+In_value;
	}
	public void ButtonEvent() {
		LayoutComponentBean.radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
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

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														LayoutComponentBean.choice = which;

													}
												})
										.setNegativeButton("확인", null).show();
							} else {
								LayoutComponentBean.choice = -1;
							}
						}
					}
				});
		LayoutComponentBean.questionAdd
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					///////////////////////////// 데모용 //////////////////////////
						switch (count) {
						case 0:
							LayoutComponentBean.question
									.setText("밥은 제때 먹구 합니까?");
							break;
						case 1:
							LayoutComponentBean.question
									.setText("교수님께서 잘 지도 해주셨습니까?");
							break;
						case 2:
							LayoutComponentBean.question
									.setText("생각 했던 결과와 어느 정도 일치 합니까?");
							break;
						case 3:
							LayoutComponentBean.question
									.setText("설계 하는 동안 여자친구를 만들지는 않았습니까?");
							break;
						default:
							try{
							LayoutComponentBean.question
									.setText(""+getLocalAddress());
							
							}catch(Exception e){}
							break;
						}
						///////////////////////////// //////////////////////////
						vector.add(LayoutComponentBean.SEPARATOR + "Topic"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.topic.getText()
								+ LayoutComponentBean.SEPARATOR + "Answer"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.question.getText()
								+ LayoutComponentBean.SEPARATOR + "Choice"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.choice);

						count++;
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

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String str = "문항  = " + list.get(position);
						Toast.makeText(QuestionnairesSettingSecond.this, str,
								Toast.LENGTH_LONG).show();
					}

				});

		LayoutComponentBean.complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FileBean.Topic=""+LayoutComponentBean.topic.getText();
				if(!LayoutComponentBean.question.getText().equals("")){
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
				}
				if (LayoutComponentBean.ScreenCount == 0) {
					TVServerIni();
					ConnectionBean.server.start(ConnectionBean.ServerConfig);
					LayoutComponentBean.ScreenCount++;
				}

				LayoutComponentBean.statisticsGraph_btn.setEnabled(true);
				LayoutComponentBean.QuestionnaireImfo_btn.setEnabled(true);
//				LayoutComponentBean.QuestionnaireInitial_btn
//						.setText("설문지 재 설정");
				LayoutComponentBean.resetFlag=true;
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
		LayoutComponentBean.Fileadd = (ImageButton) findViewById(R.id.fileadd);
		LayoutComponentBean.questionAdd = (ImageButton) findViewById(R.id.addAnswer);
		LayoutComponentBean.complete = (ImageButton) findViewById(R.id.complete);
		LayoutComponentBean.long_answer = (RadioButton) findViewById(R.id.first);
		LayoutComponentBean.short_answer = (RadioButton) findViewById(R.id.second);
		LayoutComponentBean.listview = (ListView) findViewById(R.id.listview);

		// ///////////데모 ////////////////////////
		LayoutComponentBean.topic.setText("설계");
		// ///////////////////////////////////////
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
