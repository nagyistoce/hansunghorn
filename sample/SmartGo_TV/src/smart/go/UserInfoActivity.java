package smart.go;


import smart.go.R;
import smart.go.R.id;
import smart.go.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserInfoActivity extends Activity{
	static TextView id_view;
	static TextView name_view;
	static TextView record_view;
	static TextView winning_rate_view;
	static TextView have_money_view;
	static TextView max_score_view;	
	String con_id;
	String con_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.userinfo);
    	
    	DBAdapter db = new DBAdapter(getApplicationContext());
    	db.open();        
    			
		ImageButton back_btn = (ImageButton)findViewById(R.id.back_btn);
		ImageButton change_btn = (ImageButton)findViewById(R.id.change_btn);
		
		id_view = (TextView)findViewById(R.id.id_view);
		name_view = (TextView)findViewById(R.id.name_view);
		record_view = (TextView)findViewById(R.id.record_view);
		winning_rate_view = (TextView)findViewById(R.id.winning_rate_view);
		have_money_view = (TextView)findViewById(R.id.have_money_view);
		max_score_view = (TextView)findViewById(R.id.max_score_view);
		 
		// DB에서 Data를 가져와 각  TextView에 채운다.	
		
		Intent getintent = getIntent();
		con_id = getintent.getStringExtra("ID");
		con_name = db.getNAMEEntry(con_id);
		
		id_view.setText(con_id);
		name_view.setText(con_name);		
		
		db.close();
		
		change_btn.setOnClickListener(listener);
		back_btn.setOnClickListener(listener);		
	}
	
	ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.change_btn:
				Intent intent = new Intent(UserInfoActivity.this, UpdateInfoActivity.class);
				intent.putExtra("ID", con_id);
				startActivity(intent);
				break;
			case R.id.back_btn:
				finish();
				break;
			}
			
		}
		
	};

}
