package sod.service;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONException;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebSettings.PluginState;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class myPlugin extends Plugin{
	Handler startHandler;;
	public static String mMessage=""; // return value at plugin /// �÷������� ���� Message�� ����
	public static Semaphore waithandle;		// Semaphore // ��������
	public String[] ag=new String[3];
	public Integer num=1;
	Packet pk;
	static{
		waithandle = new Semaphore(1);		// �������� //Semaphore
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public PluginResult execute(String arg0, JSONArray arg1, String arg2) {
		// TODO Auto-generated method stub
			Client_Initalize();
			pk=new Packet();
			if(arg0.equals("sendData")){
				pk.push(num);
				ConnectionBean.client.send(pk);
				
			return new PluginResult(PluginResult.Status.OK, "success");
			}
			else
				return new PluginResult(PluginResult.Status.ERROR, "Fail");
		
		
		
	//	return null;
	}
    public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		//�� �κ��� TVServerLisrtActivity ���� �����˻� �� �ϱ� ������
		 	//�ּ�ó��
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {		// client ReceiveHandler// Ŭ���̾�Ʈ ���ú� �ڵ鷯

			public void onReceive(Packet pkt) {
//				if (pkt != null) {
//					while(pkt.getElementCount() > 0)			//packet element count // ��Ŷ ������Ʈ����
//					{
//						Object item = pkt.pop();				
//						mMessage += item.toString();
//					}
//					waithandle.release();
//				}
			}
		});
		StrictMode.enableDefaults();			// stack default // ���ÿ��� ����Ʈ �ʱ�ȭ
		
		
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {	// ���� �����ڵ鷯
			
			public void work(Object arg) {
//				// TODO Auto-generated method stub
//				//�ڵ鷯�� ������
//				startHandler.sendMessage(Message.obtain());
			}
		});
		
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);	// ������ ������ ����// connection Server
		
		
	}
  
}
