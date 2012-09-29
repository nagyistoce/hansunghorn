package sod.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;

import sod.bean.AnA_Bean;
import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.Storage;
import sod.common.StorageFile;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import com.phonegap.*;

import sod.service.R;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

public class GCC_PHONEActivity extends DroidGap {
	/** Called when the activity is first created. */
	Handler startHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicedownload);
		startHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				InitHTML();
			}
		};
		Client_Initalize();
	}

	public void InitHTML() {
		// super.loadUrl("file:///android_asset/www/index.html");

		String serviceName = ConnectionBean.ServerInfomation.ServiceName;
		String servicePath = null;
		String indexHtmlPath = null;
		try {
			Storage downloadedService = Storage.getStorage(serviceName
					+ "/service");// "A&A_Service/service"
			servicePath = downloadedService.getSODrootPath();
			StorageFile indexHtmlPathStorageFile = downloadedService.openFile(
					"indexHtmlPath.ini", StorageFile.READ);

			byte[] buf = new byte[indexHtmlPathStorageFile.getLength()];
			indexHtmlPathStorageFile.read(buf);
			indexHtmlPath = new String(buf);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String loadUrlPath ="file:///"+ servicePath + "/AnA.html";
		String loadUrlPath = "file:///" + servicePath + indexHtmlPath;
		super.loadUrl(loadUrlPath);

	}

	public void Client_Initalize() { // 클라이언트 연결 및 초기화 그리고 네트워크 각 핸들러
		ConnectionBean.client = new AccessManager();
		// 이 부분은 TVServerLisrtActivity 에서 서버검색 후 하기 때문에
		// 주석처리
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() { // client
																		// ReceiveHandler//
																		// 클라이언트
																		// 리시브
																		// 핸들러

					public void onReceive(Packet pkt) {
						if (ConnectionBean.ServerInfomation.ServiceName
								.equals("gcc")) {
							String temp = pkt.pop().toString();
							if (pkt != null && temp.equals("down")) {
								Packet pt = new Packet();
								pt.push("getCard");
								ConnectionBean.client.send(pt);
								StrictMode.enableDefaults();
							} else if (pkt != null && temp.equals("sendCard")) {
								for (int i = 0; i < 10; i++) {
									myPlugin.mMessage += pkt.pop().toString()
											+ ",";
								}
								StrictMode.enableDefaults();
								InitHTML();
							} else {
								while (pkt.getElementCount() > 0) // packet
																	// element
																	// count //
																	// 패킷 엘리먼트갯수
								{
									myPlugin.DataSign += pkt.pop().toString();
								}
								StrictMode.enableDefaults();
								myPlugin.waithandle.release();
							}
						} else {
							while (pkt.getElementCount() > 0) {
								Object item = pkt.pop();
								AnA_Bean.Message += item.toString();
							}
							AnA_Bean.waithandle.release();
						}
					}
				});
		StrictMode.enableDefaults(); // stack default // 스택영역 디폴트 초기화
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() { // 서비스
																		// 시작핸들러
					public void work(Object arg) {
						// TODO Auto-generated method stub
						// 핸들러로 보내기
						startHandler.sendMessage(Message.obtain());
					}
				});
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation); // 접속할
																		// 서버에
																		// 연결//
																		// connection
																		// Server

	}
}
