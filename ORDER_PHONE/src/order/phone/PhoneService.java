package order.phone;

import sod.smarttv.ServerConfig;

import com.phonegap.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PhoneService extends DroidGap {
	Button TvSearch, ServiceLocation, InstallService;

	PhoneServiceNet net;
	private ProgressDialog dialog;

	public void Layout_Initalize() {
		TvSearch = (Button) findViewById(R.id.TvSearch);
		ServiceLocation = (Button) findViewById(R.id.ServiceLocation);
		InstallService = (Button) findViewById(R.id.InstallService);
		TvSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// new AccessManager().searchServer(); // TV 서버 찾기
				Intent intent = new Intent(PhoneService.this, SeverCon.class);
				startActivity(intent);

				}
			}
		);
		ServiceLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(PhoneService.this, TVLocationViewerActivity.class);
				startActivity(intent);
			}
		});
		InstallService.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 다운로드 부분생략
				// TV 접속UI 생략
			//	Client_Initalize();
			//	InitHTML();
			}
		}); 
	}	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Layout_Initalize();

	}
}
