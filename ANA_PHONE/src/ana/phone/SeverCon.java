package ana.phone;

import java.net.InetSocketAddress;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import com.phonegap.DroidGap;

import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

public class SeverCon extends DroidGap{
	
	Handler startHandler;
	
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		/*//이 부분은 TVServerLisrtActivity 에서 서버검색 후 하기 때문에
		 	//주석처리
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		*/
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
		
		
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {
			
			@Override
			public void work(Object arg) {
				// TODO Auto-generated method stub
				//핸들러로 보내기
				startHandler.sendMessage(Message.obtain());
			}
		});
		
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);
		
		
	}
	public void InitHTML()
	{
		super.loadUrl("file:///android_asset/www/AnA.html");
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicedownload);
	
		startHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				InitHTML();
			}
			
		};
		
		Client_Initalize();
		
		//InitHTML(); //나중에 주석처리 해야함
	}
}
