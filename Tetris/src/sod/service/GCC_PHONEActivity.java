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
		
		//Client_Initalize(); // ���߿� �ּ� ó�� ���� �ؾ���
		InitHTML();
	}
	public void InitHTML() {
		super.loadUrl("file:///android_asset/www/Contorller.html");
		//���߿� �ٿ�ε� �ǰ� �ؾ���
		/* 
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
		*/
		
	}
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		// �� �κ��� TVServerLisrtActivity ���� �����˻� �� �ϱ� ������
		// �ּ�ó��
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() { // client
																		// ReceiveHandler//
																		// Ŭ���̾�Ʈ
																		// ���ú�
																		// �ڵ鷯

					public void onReceive(Packet pkt) {
						
					}
				});
		
		
		StrictMode.enableDefaults(); // stack default // ���ÿ��� ����Ʈ �ʱ�ȭ
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() { // ����
																		// �����ڵ鷯
					public void work(Object arg) {
						// TODO Auto-generated method stub
						// �ڵ鷯�� ������
						startHandler.sendMessage(Message.obtain());
					}
				});
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation); // ������
																		// ������
																		// ����//
																		// connection
																		// Server

	}
}
