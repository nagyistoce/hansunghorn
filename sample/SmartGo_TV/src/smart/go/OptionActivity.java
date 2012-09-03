package smart.go;


import smart.go.R;
import smart.go.R.id;
import smart.go.R.layout;
import smart.go.R.raw;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class OptionActivity extends Activity {
	int vibeonoff = 0;
	int soundonoff = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.option);

		ImageButton vibeon = (ImageButton)findViewById(R.id.vibon);
		ImageButton vibeoff = (ImageButton)findViewById(R.id.viboff);
		ImageButton soundon = (ImageButton)findViewById(R.id.soundon);
		ImageButton soundoff = (ImageButton)findViewById(R.id.soundoff);		
		ImageButton back_btn = (ImageButton)findViewById(R.id.back_btn);

		vibeon.setOnClickListener(listener);
		vibeoff.setOnClickListener(listener);
		soundon.setOnClickListener(listener);
		soundoff.setOnClickListener(listener);
		back_btn.setOnClickListener(listener);
	
	}	

	ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.vibon:
				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
				vibe.vibrate(500);		
				vibeonoff = 1;  // vibration on
				break;
			case R.id.viboff:
				vibeonoff = 0;
				break;
			case R.id.soundon:
				MediaPlayer player;
				player = MediaPlayer.create(OptionActivity.this, R.raw.dingdong);
				player.start();						
				soundonoff = 1; // sound on	
				break;
			case R.id.soundoff:
				soundonoff = 0;
				break;
			case R.id.back_btn:
				finish();
				break;
				
			}
			
		}
	};
}
