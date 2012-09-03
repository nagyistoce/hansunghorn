package phone.service;

import sod.service.ServiceStart;
import sod.service.TVLocationViewerActivity;
import sod.service.TVServerListActivity;
import sod.service.TVServiceListActivity;

import com.phonegap.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PhoneServiceActivity extends DroidGap {
	ImageButton TvSearch, ServiceLocation, ServiceList;		//  layout component in main.xml // 메인.xml의 ImageButton

	private ProgressDialog dialog;

	public void Layout_Initalize() {
		TvSearch = (ImageButton) findViewById(R.id.TvSearch);
		ServiceLocation = (ImageButton) findViewById(R.id.ServiceLocation);
		ServiceList = (ImageButton) findViewById(R.id.InstallService);
		TvSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {		// Look up ServerTv Service// ServerTV의 서비스 찾기
				// new AccessManager().searchServer(); // TV 서버 찾기
				Intent intent = new Intent(PhoneServiceActivity.this, TVServerListActivity.class);
				startActivity(intent);

				}
			}
		);
		ServiceLocation.setOnClickListener(new OnClickListener() {		// Look up location ServerTV //ServerTv 위치 찾기

			public void onClick(View v) {
				Intent intent = new Intent(PhoneServiceActivity.this, TVLocationViewerActivity.class);
				startActivity(intent);
			}
		});
		ServiceList.setOnClickListener(new OnClickListener() { // View install Service // 설치된 서비스 보기 

			public void onClick(View v) {
				Intent intent = new Intent(PhoneServiceActivity.this, TVServiceListActivity.class);
				startActivity(intent);
			}
		}); 
	}	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Layout_Initalize();
		//Initialize layout component  in main.xml //main.xml 레이아웃 초기화
	}
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.loadUrl("file:///android_asset/www/index.html");
//    }
}
