package sod.activity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import sod.common.ActionEx;
import sod.common.NetworkUtils;
import sod.common.ThreadEx;
import sod.smartphone.AccessManager;
import sod.smartphone.SearchCallBack;
import sod.smartphone.ServerInfo;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ServerConfig;
import sod.test.demo.R;

import android.app.ListActivity;
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
	
		////////////
		/*
		AccessManagerServer server = new AccessManagerServer();
		final int ServerPort = 3221; 
		ServerConfig conf = new ServerConfig();
		conf.Port = ServerPort;
		conf.serviceName = "TestService";
		server.start(conf);		
	
		*/
		////////////

//		String localip = "192.168.0.24";
		
		ThreadEx.invoke(null, new ActionEx() {
			
			@Override
			public void work(Object arg) {
				// TODO Auto-generated method stub
				String localip = getLocalIpAddress();
				AccessManager.searchServer(localip, new SearchCallBack() {
					
					@Override
					public void onSearch(ServerInfo info) {
						// TODO Auto-generated method stub
						if(info == null){
							
						}else{
							//핸들러 이용해서 보내야하나??
							Log.i("jaeyeong", "jaeyeong"+"OnSearch");
							Log.i("jaeyeong",info.EndPoint.getAddress().getHostAddress());
							
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
	public String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf =(NetworkInterface)en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	            
	                InetAddress inetAddress =(InetAddress) enumIpAddr.nextElement();

	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("jaeyeong", ex.toString());
	    }
	    return null;
	}
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
