package sod.activity;

import java.util.ArrayList;

import sod.common.NetworkUtils;
import sod.smartphone.AccessManager;
import sod.smartphone.SearchCallBack;
import sod.smartphone.ServerInfo;
import sod.test.demo.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TVServerListActivity extends ListActivity {

	final static int port = 30331;
	
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
		
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				ServerInfo info = (ServerInfo)msg.obj;
				list.add(info.EndPoint.getAddress().getHostAddress() +","+ info.ServiceName);
				adapter.notifyDataSetChanged();
			}
			
		};
		
		
		String localip = NetworkUtils.getLocalIP();
		AccessManager.searchServer(localip, port, new SearchCallBack() {
			
			@Override
			public void onSearch(ServerInfo info) {
				// TODO Auto-generated method stub
				if(info == null){
					
				}else{
					//핸들러 이용해서 보내야함
					Message msg = Message.obtain();
					msg.obj = (Object)info;
					handler.sendMessage(msg);
				}
			}
		});
		/*
		list.add("dasfdsf");
		adapter.notifyDataSetChanged();
		*/
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String []strs = adapter.getItem(position).split(",");
		String ip = strs[0];
		
		Toast.makeText(this, ip, Toast.LENGTH_SHORT).show();
	}

}
