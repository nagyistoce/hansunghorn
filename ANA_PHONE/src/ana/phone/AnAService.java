package ana.phone;

import java.net.InetSocketAddress;

import sod.common.Packet;
import sod.common.ReceiveHandler;

import com.phonegap.*;

import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ana.phoneBean.ConnectionBean;
import ana.phoneBean.DownLoad;

public class AnAService  extends DroidGap {
	Button TvSearch, ServiceLocation, InstallService;

	AnAServiceNet net;
	

	public void Layout_Initalize() {
		TvSearch = (Button) findViewById(R.id.TvSearch);
		ServiceLocation = (Button) findViewById(R.id.ServiceLocation);
		InstallService = (Button) findViewById(R.id.InstallService);
		TvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// new AccessManager().searchServer(); // TV ���� ã��
				Intent intent = new Intent(AnAService.this, TVServerListActivity.class);
				startActivity(intent);
			}
		});
		ServiceLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AnAService.this, TVLocationViewerActivity.class);
				startActivity(intent);
			}
		});
		InstallService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �ٿ�ε� �κл���
				// TV ����UI ����
			//	Client_Initalize();
			//	InitHTML();
			}
		}); 
	} 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Layout_Initalize();

	}
}









