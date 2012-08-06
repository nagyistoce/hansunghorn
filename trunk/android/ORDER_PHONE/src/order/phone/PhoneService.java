package order.phone;

import com.phonegap.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class PhoneService extends DroidGap {
	ImageButton TvSearch, ServiceLocation, ServiceList;

	PhoneServiceNet net;
	private ProgressDialog dialog;

	public void Layout_Initalize() {
		TvSearch = (ImageButton) findViewById(R.id.TvSearch);
		ServiceLocation = (ImageButton) findViewById(R.id.ServiceLocation);
		ServiceList = (ImageButton) findViewById(R.id.InstallService);
		TvSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// new AccessManager().searchServer(); // TV 서버 찾기
				Intent intent = new Intent(PhoneService.this, TVServerListActivity.class);
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
		ServiceList.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 다운로드 부분생략
				// TV 접속UI 생략
			//	Client_Initalize();
			//	InitHTML();
				Intent intent = new Intent(PhoneService.this, TVServiceListActivity.class);
				startActivity(intent);
			}
		}); 
	}	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Layout_Initalize();

	}
}
