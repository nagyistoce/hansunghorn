package smart.go;

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
import android.widget.LinearLayout;

public class SmartGoActivity extends Activity {
	/** Called when the activity is first created. */
	LinearLayout Game_ll;
	LinearLayout Login_ll;
	int nWidth; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		nWidth = this.getWindowManager().getDefaultDisplay().getWidth();
		Log.e("aa", ""+nWidth);
		if(nWidth == 1920)
			setContentView(R.layout.maintv);
		else
			setContentView(R.layout.main);

		Game_ll = (LinearLayout)findViewById(R.id.GameMenu);
		Login_ll = (LinearLayout)findViewById(R.id.login_layout);

		ImageButton join_btn = (ImageButton)findViewById(R.id.join_us);
		ImageButton login_btn = (ImageButton)findViewById(R.id.Login_btn);

		ImageButton game_btn = (ImageButton)findViewById(R.id.gamestart_btn);
		ImageButton userinfo_btn = (ImageButton)findViewById(R.id.userinfo_btn);
		ImageButton option_btn = (ImageButton)findViewById(R.id.option_btn);
		ImageButton logout_btn = (ImageButton)findViewById(R.id.logout_btn);

		join_btn.setOnClickListener(listener);
		login_btn.setOnClickListener(listener);

		game_btn.setOnClickListener(listener);
		userinfo_btn.setOnClickListener(listener);
		option_btn.setOnClickListener(listener);
		logout_btn.setOnClickListener(listener);


	}

	ImageButton.OnClickListener listener = new ImageButton.OnClickListener() {
		EditText loginid;
		EditText loginpw;
		String con_id;
		String con_pw;

		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.join_us:
				Intent intent = new Intent(SmartGoActivity.this, JoinActivity.class);
				startActivity(intent);
				break;
			case R.id.Login_btn: 
				/*	int login_idok = 0;
				int login_pwok = 0;
				DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();

				loginid = (EditText)findViewById(R.id.Login_id);
				loginpw = (EditText)findViewById(R.id.Login_pw);
				con_id = loginid.getText().toString();
				con_pw = loginpw.getText().toString();

				int idyesno = db.getIDEntry(con_id);
				int pwyesno = db.getIDPWEntry(con_id, con_pw);

				if(idyesno == 0) {
					new AlertDialog.Builder(SmartGoActivity.this)
					.setTitle("경고")
					.setMessage("존재하지않는 ID입니다..\n 다시 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				}

				else if(pwyesno == 0) {
					new AlertDialog.Builder(SmartGoActivity.this)
					.setTitle("경고")
					.setMessage("비밀번호가 틀렸습니다..\n 다시 입력해 주세요")
					.setPositiveButton("다시", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				} else  {
					login_idok = 1;login_pwok = 1;
				}

				if(login_idok == 1 && login_pwok == 1) {
					Login_ll.setVisibility(View.GONE);
					Game_ll.setVisibility(View.VISIBLE);
				}
				db.close();*/

				Login_ll.setVisibility(View.GONE);
				Game_ll.setVisibility(View.VISIBLE);

				break;

			case R.id.gamestart_btn:
				if(nWidth == 1920) {
					Intent intent11 = new Intent(SmartGoActivity.this, RunActivity.class);
					startActivity(intent11);
				}
				
				else {
					String items[] = {"TV로 게임할래요", "Phone으로 게임할래요"};
					new AlertDialog.Builder(SmartGoActivity.this)
					.setTitle("Choose the Device")
					.setSingleChoiceItems(items, 1,	new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if(which == 0) {
								Intent intent1 = new Intent(SmartGoActivity.this, ConnectTestActivity.class);
								startActivity(intent1);
							}
							else if(which == 1) {
								Intent intent11 = new Intent(SmartGoActivity.this, RunActivity.class);
								startActivity(intent11);
							}
						}
					})
					.show();
				}
				break;
			case R.id.userinfo_btn:
				Intent intent2 = new Intent(SmartGoActivity.this, UserInfoActivity.class);
				intent2.putExtra("ID", con_id);
				startActivity(intent2);
				break;
			case R.id.option_btn:
				Intent intent3 = new Intent(SmartGoActivity.this, OptionActivity.class);
				startActivity(intent3);
				break;
			case R.id.logout_btn:
				Login_ll.setVisibility(View.VISIBLE);
				Game_ll.setVisibility(View.GONE);

				loginid.setText(""); loginpw.setText("");
				break;
			}

		}
	};

}