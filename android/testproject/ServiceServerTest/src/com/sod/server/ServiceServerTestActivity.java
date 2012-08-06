package com.sod.server;

import sod.common.ActionEx;
import sod.common.Logable;
import sod.common.NetworkUtils;
import sod.common.Packet;
import sod.common.ThreadEx;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ServiceServerTestActivity extends Activity {
	/** Called when the activity is first created. */
	final static String ServerIP = "127.0.0.1";
	final static int ServerPort = 6789;

	static Logable logger;
	static AccessManagerServer server;
	
	WifiManager.MulticastLock mlock;

	static {
		logger = new Logable() {			
			@Override
			public void log(Object arg) {
				Log.d("logger", arg.toString());
				
			}
		};
	}
	
	static Context context;

	Handler startServiceHandler;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mlock = getWifiManager().createMulticastLock("test_mlock");
		mlock.setReferenceCounted(true);
		mlock.acquire();

		NetworkUtils.setLocalIp(getLocalIpAddress());
		context = this;
		
		// server side
		
		server = new AccessManagerServer();
		ServerConfig conf = new ServerConfig();
		conf.Timeout = 10000;
		conf.Port = ServerPort;
		conf.CheckingPeriod = 2000;
		conf.serviceName = "TestService";
		server.setConnectHandler(new ConnectHandler() {
			@Override
			public void onConnect(int connid) {
				logger.log("(server): new connection is accepted - " + connid
						+ "\n");
			
			}
		});
		server.setDisconnectHandler(new DisconnectHandler() {

			@Override
			public void onDisconnect(int connid) {
				logger.log("(server): client is disconnected - " + connid
						+ "\n");
			}
		});
		server.setReceiveHandler(new ServerReceiveHandler() {
			@Override
			public void onReceive(Packet pkt, int connid) {
				logger.log("(server): a packet from client - " + connid + "\n");
				server.send(pkt, connid);
			}
		});
		server.start(conf);
		
//////////////////////////////////
		
		startServiceHandler = new Handler() {

			public void handleMessage(Message msg) {
				Toast.makeText(context, "���� ����..", Toast.LENGTH_SHORT);
			}

		};
    }
	
	@Override
	protected void onDestroy() {
		mlock.release();
	};
	
	WifiManager getWifiManager(){
		return (WifiManager) getSystemService(Context.WIFI_SERVICE);
	}
	
	
	
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

}