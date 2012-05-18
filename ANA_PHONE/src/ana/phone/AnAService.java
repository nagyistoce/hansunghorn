package ana.phone;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.phonegap.*;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.SmartPhone.AccessManager;
import SOD.SmartPhone.ServerInfo;
import SOD.SmartTV.AccessManagerServer;
import SOD.SmartTV.ConnectHandler;
import SOD.SmartTV.DisconnectHandler;
import SOD.SmartTV.ServerConfig;
import SOD.SmartTV.ServerReceiveHandler;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;
import ana.phonecontroller.Client_Initalize;

public class AnAService extends DroidGap {
	Button TvSearch, ServiceLocation, InstallService;
	
	AnAServiceNet net;
	public void Initialize() {
		super.loadUrl("file:///android_asset/www/AnA.html");
	}
	public void Client_Initalize() {
		
		ConnectionBean.client = new AccessManager();
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		StrictMode.enableDefaults();
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);
	}
	public void Client_Data()
	{
		
	
	}
	public void Layout_Initalize() {
		TvSearch = (Button) findViewById(R.id.TvSearch);
		ServiceLocation = (Button) findViewById(R.id.ServiceLocation);
		InstallService = (Button) findViewById(R.id.InstallService);
		TvSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// new AccessManager().searchServer(); // TV 서버 찾기
			}
		});
		ServiceLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});
		InstallService.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 다운로드 부분생략
				// TV 접속UI 생략
			
				Client_Initalize();
				Initialize();
			}
		});
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Layout_Initalize();

	}
}
