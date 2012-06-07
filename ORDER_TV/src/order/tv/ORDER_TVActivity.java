package order.tv;

import order.bean.ConnectionBean;
import order.bean.LayoutComponentBean;
import order.control.Networking;
import sod.common.NetworkUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ana.tv.R;
import android.view.View.OnClickListener;

public class ORDER_TVActivity extends Activity {
	/** Called when the activity is first created. */
	////엄씨가 넣은것///////////
	WifiManager.MulticastLock mlock;
	//////////엄씨가 넣은것

	public void LayoutComponent() {
		LayoutComponentBean.Total_Menu = (Button) findViewById(R.id.Total_Menu);
		LayoutComponentBean.AddMenuItem = (Button) findViewById(R.id.AddMenuItem);
		LayoutComponentBean.updateMenuItem = (Button) findViewById(R.id.updateMenuItem);
		LayoutComponentBean.Total_Menu
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								ToTalMenuOrder.class);
						startActivity(intent);
	
					}
				});
		LayoutComponentBean.AddMenuItem
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								AddMenuOrder.class);
						startActivity(intent);
						//finish();
					}
				});
		LayoutComponentBean.updateMenuItem
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								ModiFyMenuOrder.class);
						startActivity(intent);
	
					}
				});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_order);
		// ///////////엄씨가 넣은것///////////////////////////////////////////
		NetworkUtils.setLocalIp(getLocalIpAddress());
		
		//서버탐색이 응답하기 위해 필요하다
		mlock = getWifiManager().createMulticastLock("test_mlock");
		mlock.setReferenceCounted(true);
		mlock.acquire();
		// ////////////엄씨가 넣은것////////////////////////////////
		
		LayoutComponent();
		if(ConnectionBean.pagecount==0)
		{
		new Networking().TVServerIni();
		ConnectionBean.server.start(ConnectionBean.ServerConfig);
		ConnectionBean.pagecount++;
		}
	}

	// ////////////////////////////엄씨가 추가/////////
	WifiManager getWifiManager() {
		return (WifiManager) getSystemService(Context.WIFI_SERVICE);
	}

	// /////////////////////////////////////////////////////
	//////////////////////////엄씨가 추가/////////////////////////////////////////
	public String getLocalIpAddress() {
		// need to
		// <uses-permission
		// android:name="android.permission.ACCESS_WIFI_STATE"/>
		WifiManager wifiManager = getWifiManager();
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		byte[] bytes = int2byte(ipAddress);
		int[] values = new int[4];

		for (int i = 0; i < 4; i++)
			values[i] = bytes[i] & 0xFF;

		String ipStr = String.format("%d.%d.%d.%d", values[3], values[2],
				values[1], values[0]);

		return ipStr;

	}

	final byte[] int2byte(int i) {
		byte[] dest = new byte[4];
		dest[3] = (byte) (i & 0xff);
		dest[2] = (byte) (i >> 8 & 0xff);
		dest[1] = (byte) (i >> 16 & 0xff);
		dest[0] = (byte) (i >> 24 & 0xff);

		return dest;
	}
	/////////////////엄씨가 추가///////////////////////////////////////////////////
}