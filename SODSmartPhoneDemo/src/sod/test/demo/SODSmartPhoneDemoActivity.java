package sod.test.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SODSmartPhoneDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button mapViewerButton = (Button)findViewById(R.id.mapViewerButton);
        Button seachSmartTVButton = (Button)findViewById(R.id.seachSmartTVButton);
        Button serviceListButton = (Button)findViewById(R.id.serviceListButton);
        
        mapViewerButton.setOnClickListener(buttonListener);
        seachSmartTVButton.setOnClickListener(buttonListener);
        serviceListButton.setOnClickListener(buttonListener);
    }
    
    OnClickListener buttonListener = new OnClickListener() {
		
    	Intent intent= null;
    	
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.mapViewerButton:
				intent = new Intent(SODSmartPhoneDemoActivity.this, sod.activity.TVLocationViewerActivity.class);
				break;
			case R.id.seachSmartTVButton:
				break;
			case R.id.serviceListButton:
				break;
			}
			startActivity(intent);
		}
		
		
	};
}