package ana.phone;

import java.io.IOException;
import java.net.InetSocketAddress;

import sod.common.Packet;
import sod.common.ReceiveHandler;

import com.phonegap.*;

import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;

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
		
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {

			public void onReceive(Packet pkt) {
				if (pkt != null) {
					while(pkt.getElementCount() > 0)
					{
						Object item = pkt.pop();
						DownLoad.Message += item.toString();
					}
					DownLoad.waithandle.release();
				}
			}
		});
		StrictMode.enableDefaults();
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);
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
