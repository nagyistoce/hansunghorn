package smart.go;


import smart.go.R;
import smart.go.R.id;
import smart.go.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class JoinActivity extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.join_us); 	

		ImageButton join_btn = (ImageButton)findViewById(R.id.join_btn);
		ImageButton cancel_btn = (ImageButton)findViewById(R.id.cancel_btn);
		
		join_btn.setOnClickListener(new ImageButton.OnClickListener() {

			public void onClick(View v) {
				EditText id = (EditText)findViewById(R.id.idEdit);
				EditText pw = (EditText)findViewById(R.id.pwEdit);
				EditText rpw = (EditText)findViewById(R.id.rpwEdit);
				EditText name = (EditText)findViewById(R.id.nameEdit);

				DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();

				String sid = id.getText().toString();
				String spw = pw.getText().toString();
				String srpw = rpw.getText().toString();
				String sname = name.getText().toString();
		
				int yesno = db.getIDEntry(sid);
					
				// null일때,
				if(sid.equals("") || spw.equals("") || srpw.equals("") || sname.equals("")) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("경고")
					.setMessage("빈칸을 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				} // pw와 rpw가 일치하지 않을때 
				else if(!spw.equals(srpw)) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("경고")
					.setMessage("Confirm_PW를 다시 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();					
				}
				else if(yesno == 1) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("경고")
					.setMessage("사용중인 ID입니다.\n 다시 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				}
				else {
					long num = db.insertEntry(sid, spw, srpw, sname);
					db.close();	
					finish();
				}
			}
		});

		cancel_btn.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
