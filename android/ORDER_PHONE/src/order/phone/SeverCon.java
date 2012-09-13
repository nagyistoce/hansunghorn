package order.phone;

import java.io.IOException;
import java.net.InetSocketAddress;

import order.bean.ConnectionBean;
import order.bean.DownLoad;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.Storage;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import com.phonegap.DroidGap;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

public class SeverCon extends DroidGap{
	
	Handler startHandler;;
	
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		/*//이 부분은 TVServerLisrtActivity 에서 서버검색 후 하기 때문에
		 	//주석처리
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		*/
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {

			public void onReceive(Packet pkt) {
				if (pkt != null) {
					while(pkt.getElementCount() > 0)
					{
						Object item = pkt.pop();
						ConnectionBean.Message += item.toString();
					}
					DownLoad.waithandle.release();
				}
			}
		});
		StrictMode.enableDefaults();
		
		
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {
			
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
		//super.loadUrl("file:///android_asset/www/order.html");
		
		String servicePath = null;
		try {                                              //이것도 상수화 시켜야한다.  ini 방식을 이용
			Storage downloadedService = Storage.getStorage("order/service");// "A&A_Service/service"
			servicePath = downloadedService.getSODStoragePath();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                             //이거 상수화 시켜버려 ㅇㅇ
		String loadUrlPath ="file:///"+ servicePath + "/order.html";
		super.loadUrl(loadUrlPath);
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
