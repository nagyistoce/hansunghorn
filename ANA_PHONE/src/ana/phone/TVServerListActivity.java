package ana.phone;

import java.util.ArrayList;

import sod.common.NetworkUtils;
import sod.smartphone.AccessManager;
import sod.smartphone.SearchCallBack;
import sod.smartphone.ServerInfo;


import ana.phoneBean.ConnectionBean;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TVServerListActivity extends ListActivity
{

	final static int port = 30331;
	private AnAService ana;
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
		/*
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				ServerInfo info = (ServerInfo)msg.obj;
				Log.i("jaeyeong", "jaeyeong"+info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
				list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
				adapter.notifyDataSetChanged();
			}
			
		};
		*/
		
		
		String localip = NetworkUtils.getLocalIP();
		AccessManager.searchServer(localip, port, new SearchCallBack() {
			
			@Override
			public void onSearch(ServerInfo info) {
				// TODO Auto-generated method stub
				if(info == null){
					
				}else{
					//핸들러 이용해서 보내야하나??
					Log.i("jaeyeong", "jaeyeong"+"OnSearch");
					
					list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
					adapter.notifyDataSetChanged();
					/*
					Message msg = Message.obtain();
					msg.obj = (Object)info;
					handler.sendMessage(msg);
					*/
				}
			}
		});
		//stub code..../////////////////////////////////
		list.add("192.168.0.10,A&A");
		list.add("192.168.0.5,A&A");
		
		adapter.notifyDataSetChanged();
		///////////////////////////////////////////////////////////
		
		/*
		Button searchButton = (Button)findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String localip = NetworkUtils.getLocalIP();
				AccessManager.searchServer(localip, port, new SearchCallBack() {
					
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
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String []strs = adapter.getItem(position).split(",");
		String ip = strs[0];
		ConnectionBean.SERVERIP=ip;
		Intent intent=new Intent(TVServerListActivity.this,SeverCon.class);
		startActivity(intent);
		
//		AnAService service=new AnAService();
//		service.Client_Initalize();
//		service.loadUrl("file:///android_asset/www/AnA.html");
//		
//		ClientConTrol conTrol=new ClientConTrol();
//		conTrol.Client_Initalize();
//		conTrol.InitHTML();
	}

}
