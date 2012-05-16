package answer_ask_Service.TV;

import java.net.SocketException;

import SOD.Common.Packet;
import SOD.SmartPhone.AccessManager;
import SOD.SmartTV.AccessManagerServer;
import SOD.SmartTV.ConnectHandler;
import SOD.SmartTV.DisconnectHandler;
import SOD.SmartTV.ServerConfig;
import SOD.SmartTV.ServerReceiveHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;
import answer_ask_Service.TV.TVManagerLoGo.splashhandler;

public class AnA_BootMode extends Activity {
	private Button statisticsGraph_btn,QuestionnaireInitial_btn,QuestionnaireImfo_btn;
	void LayoutInitial()
	{
		 statisticsGraph_btn=(Button)findViewById(R.id.statisticsGraph);
	     QuestionnaireInitial_btn=(Button)findViewById(R.id.QuestionnaireInitialSettinig);
	     QuestionnaireImfo_btn=(Button)findViewById(R.id.QuestionnaireSettingImfomation);
	}
	void TVServerInital()
	{
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig= new ServerConfig();
		ConnectionBean.ServerConfig.Timeout=30000;
		ConnectionBean.ServerConfig.Port=ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckPeriod=4000;
		ConnectionBean.ServerConfig.serviceName ="A&A_Service";
		ConnectionBean.server.setConnectHandler(new ConnectHandler() {
			
			public void onConnect(int connid) {
				// TODO Auto-generated method stub
				
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {
			
			public void onDisconnect(int connid) {
				// TODO Auto-generated method stub
				
			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {
			
			public void onReceive(Packet pkt, int connid) {
				// TODO Auto-generated method stub
				ConnectionBean.ClientId=connid;
			}
		});
		ConnectionBean.server.start(ConnectionBean.ServerConfig);
	}
	
	public void ButtonEvent()
	{
		   statisticsGraph_btn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(AnA_BootMode.this,PieChartBuild.class);
			          startActivity(intent);
			        
				}
			});
		       QuestionnaireInitial_btn.setOnClickListener(new OnClickListener() {
		   		
		   		public void onClick(View v) {
		   		  Intent intent = new Intent(AnA_BootMode.this,QuestionnaireSettingFirst.class);
		            startActivity(intent);
		           
		   		}
		   	});
		       QuestionnaireImfo_btn.setOnClickListener(new OnClickListener() {
		   		
		   		public void onClick(View v) {
		   			// TODO Auto-generated method stub
		   			
		   		}
		   	});
	}
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.bootting);
       LayoutInitial();
       TVServerInital();
       ButtonEvent();
    } 


}
