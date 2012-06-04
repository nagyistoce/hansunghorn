package sod.test.service;

import java.net.InetSocketAddress;

import sod.common.ActionEx;
import sod.common.ConsoleLogger;
import sod.common.Logable;
import sod.common.NetworkUtils;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.ThreadEx;
import sod.smartphone.AccessManager;
import sod.smartphone.SearchCallBack;
import sod.smartphone.ServerInfo;
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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ServiceDownloadTestActivity extends Activity {
	/** Called when the activity is first created. */

	final static String ServerIP = "127.0.0.1";
	final static int ServerPort = 6789;

	static Logable logger;
	static AccessManagerServer server;
	static AccessManager client;
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
		
		context = this;

		NetworkUtils.setLocalIp(getLocalIpAddress());
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
				Toast.makeText(context, "서비스 시작..", Toast.LENGTH_SHORT);
			}

		};
		
		// /////////////// client side
		
		
		ThreadEx.invoke(null, new ActionEx(){

			@Override
			public void work(Object arg) {
				// TODO Auto-generated method stub
				// now we are looking for server.
				String localip = NetworkUtils.getLocalIP();
				logger.log("localip = " + localip + "\n");

				
				AccessManager.searchServer(localip, new SearchCallBack() {
					@Override
					public void onSearch(ServerInfo info) {
						if (info == null) {
							// end of search
							logger.log("end of search.\n");
							return;
						} else {
							logger.log("found server: "
									+ info.EndPoint.getAddress().getHostAddress()
									+ ", " + info.ServiceName + "\n");
						}
						
					}// end onSearch.....
				}); // end searchServer......
				
				
				client = new AccessManager();
				ServerInfo svinfo = new ServerInfo();
				svinfo.EndPoint = new InetSocketAddress(localip, ServerPort);
				//svinfo.EndPoint = new InetSocketAddress(ServerIP, ServerPort);
				client.setReceiveHandler(new ReceiveHandler() {
					@Override
					public void onReceive(Packet pkt) {
						logger.log("(client): a packet from server - "
								+ (String) pkt.pop() + "\n");
					}
				});
				

				client.setStartServiceDelegate(new ActionEx() {

					@Override
					public void work(Object arg) {
						// TODO Auto-generated method stub
						// startServiceHandler
						startServiceHandler.sendMessage(Message.obtain());

					}

				});

				client.connect(svinfo);

			}
			
		});

		
	}

	// //////////////////////////////////////////////////////////////////////////////////////
	public String getLocalIpAddress() {
		// need to
		// <uses-permission
		// android:name="android.permission.ACCESS_WIFI_STATE"/>
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
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