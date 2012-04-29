package com.phonegapmyplugin;

import com.phonegap.*;
import com.phonegapmyplugin.R.id;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import SOD.Common.*;
import SOD.Deprecated.*;
import SOD.Deprecated.Phone.*;
import SOD.Deprecated.TV.*;
import SOD.SmartPhone.*;
import SOD.SmartTV.*;

public class mypluginActivity extends DroidGap {
	Button TvSearch,ServiceLocation,InstallService;
	public void Initialize()
	{
		  super.loadUrl("file:///android_asset/www/index.html");
	}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        TvSearch=(Button)findViewById(R.id.TvSearch);
        ServiceLocation=(Button)findViewById(R.id.ServiceLocation);
        InstallService=(Button)findViewById(R.id.InstallService);
        
        TvSearch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//new AccessManager().searchServer();	 // TV 서버 찾기
			}
		});
        ServiceLocation.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
        InstallService.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Initialize();
			}
		});
      
    }
}
