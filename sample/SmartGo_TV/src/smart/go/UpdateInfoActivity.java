package smart.go;


import smart.go.R;
import smart.go.R.id;
import smart.go.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class UpdateInfoActivity extends Activity{
	String check_id;
	String check_pw;
	String check_rpw;
	String check_name;

	TextView update_id;
	EditText update_pw;
	EditText update_rpw;
	EditText update_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.update_info);

		ImageButton update_btn = (ImageButton)findViewById(R.id.update_btn);
		ImageButton cancel_btn = (ImageButton)findViewById(R.id.cancel_btn);

		final DBAdapter db = new DBAdapter(getApplicationContext());
		db.open();
		Intent getintent = getIntent();

		check_id = getintent.getStringExtra("ID");
		check_pw = db.getPWEntry(check_id);
		check_rpw = check_pw;
		check_name = db.getNAMEEntry(check_id);

		update_id = (TextView)findViewById(R.id.static_id);
		update_pw = (EditText)findViewById(R.id.update_pw);
		update_rpw = (EditText)findViewById(R.id.update_rpw);
		update_name = (EditText)findViewById(R.id.update_name);

		update_id.setText(check_id);
		update_pw.setText("");
		update_rpw.setText("");
		update_name.setText("");	

		db.close();
		update_btn.setOnClickListener(listener);
		cancel_btn.setOnClickListener(listener);
	}

	ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.update_btn:
				DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				String spw = update_pw.getText().toString();
				String srpw = update_rpw.getText().toString();
				String sname = update_name.getText().toString();

				if(!spw.equals(srpw)) {
					new AlertDialog.Builder(UpdateInfoActivity.this)
					.setTitle("경고")
					.setMessage("Confirm_PW를 다시 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();					
				}
				else {
					boolean num = db.updateEntry(check_id, spw, srpw, sname);
					db.close();	
					finish();
				}	
				break;
			case R.id.cancel_btn:
				finish();
				break;
			}
		}
	};
}
