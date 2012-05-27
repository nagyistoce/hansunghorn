package sod.activity;

import java.util.ArrayList;

import sod.common.ActionEx;
import sod.common.NetworkUtils;
import sod.common.ThreadEx;
import sod.smartphone.AccessManager;
import sod.smartphone.SearchCallBack;
import sod.smartphone.ServerInfo;
import sod.test.demo.R;

import android.app.ListActivity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TVServerListActivity extends ListActivity {

	final static int port = 3221;
	
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	
	Handler handler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tvserverlist);
		
		list = new ArrayList<String>();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		
		Log.i("jaeyeong","jaeyeong start");
	
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				ServerInfo info = (ServerInfo) msg.obj;
				list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
				adapter.notifyDataSetChanged();
				
			}
			
		};
		
		
		ThreadEx.invoke(null, new ActionEx() {
			
			@Override
			public void work(Object arg) {
				// TODO Auto-generated method stub
//				String localip = NetworkUtils.getLocalIP();
				String localip = getLocalIpAddress();
				Log.i("jaeyeong", localip);
				AccessManager.searchServer(localip, new SearchCallBack() {
						
					@Override
					public void onSearch(ServerInfo info) {
						// TODO Auto-generated method stub
						if(info == null){
							
						}else{
							//핸들러 이용해서 보내야하나??
							Log.i("jaeyeong", "jaeyeong"+"OnSearch");
							Log.i("jaeyeong",info.EndPoint.getAddress().getHostAddress()) ;
							
							Message msg = handler.obtainMessage();
							msg.obj = info;
							handler.sendMessage(msg);
							
	/*						//핸들러 이용해서 보내자
							list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
							adapter.notifyDataSetChanged();
	*/
							
						}
					}
				});
			}
		});
		

		//stub code..../////////////////////////////////
//		list.add("192.168.0.5,A&A");
		adapter.notifyDataSetChanged();
		///////////////////////////////////////////////////////////
		
		/*
		Button searchButton = (Button)findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String localip = NetworkUtils.getLocalIP();
				Toast.makeText(v.getContext(), localip, Toast.LENGTH_LONG);
				AccessManager.searchServer(localip, new SearchCallBack() {
					
					@Override
					public void onSearch(ServerInfo info) {
						// TODO Auto-generated method stub
						if(info == null){
							
						}else{
							//핸들러 이용해서 보내야함
							Log.i("jaeyeong", "jaeyeong"+"OnSearch");
							
							list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
							adapter.notifyDataSetChanged();
							
						}
					}
				});
				
			}
		});
		*/
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	public String getLocalIpAddress() {
    	// need to
    	//<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    	WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	int ipAddress = wifiInfo.getIpAddress();
    	
    	byte [] bytes = int2byte(ipAddress);
    	int [] values = new int[4];
    	
    	for(int i = 0 ; i<4 ; i++)
    		values[i] = bytes[i] & 0xFF;

    	String ipStr = String.format("%d.%d.%d.%d", 
    			values[3], values[2], values[1], values[0] );

		return ipStr;
	 
	}
    
    final byte [] int2byte(int i){
    	byte[] dest = new byte[4];
    	dest[3] = (byte)(i & 0xff);
    	dest[2] = (byte)(i >> 8 & 0xff);
    	dest[1] = (byte)(i >> 16& 0xff);
    	dest[0] = (byte)(i >> 24& 0xff);
    	
    	return dest;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String []strs = adapter.getItem(position).split(",");
		String ip = strs[0];
		
		Toast.makeText(this, ip, Toast.LENGTH_SHORT).show();
		
		//여기서 인텐트를 이용해서 서비스를 실행시키면 됩니다.
	}
	
	

}