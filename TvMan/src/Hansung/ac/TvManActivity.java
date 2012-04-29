package Hansung.ac;

import Hansung.ac.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class TvManActivity extends Activity {
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       
        setContentView(R.layout.main);
        
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 3000); //1.5占십뒤울옙 占쌕몌옙 占쏙옙티占쏙옙티占쏙옙...
    }

    //占쏙옙占시뤄옙占쏙옙 
    class splashhandler implements Runnable {
        public void run() {
            Intent intent = new Intent(TvManActivity.this,TvSetting.class);
            startActivity(intent);
           finish();
        }        
    }
}