package ana.phone;

import java.net.InetSocketAddress;

import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import com.phonegap.DroidGap;

import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;
import android.os.Bundle;
import android.os.StrictMode;

public class SeverCon extends DroidGap{
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {

			@Override
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
	public void InitHTML()
	{
		super.loadUrl("file:///android_asset/www/AnA.html");
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Client_Initalize();
		InitHTML();
	}
}
