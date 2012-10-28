package answer_ask_Service.TV;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import answer_ask_BeanTV.DataSturct;
import answer_ask_BeanTV.LayoutComponentBean;

public class AnA_BootMode extends Activity {
	void LayoutInitial() {

		LayoutComponentBean.statisticsGraph_btn = (ImageButton) findViewById(R.id.statisticsGraph);
		LayoutComponentBean.QuestionnaireInitial_btn = (ImageButton) findViewById(R.id.QuestionnaireInitialSettinig);
		LayoutComponentBean.QuestionnaireImfo_btn = (ImageButton) findViewById(R.id.QuestionnaireSettingImfomation);
		if (LayoutComponentBean.ScreenCount == 0) {
			LayoutComponentBean.statisticsGraph_btn.setEnabled(false);
			LayoutComponentBean.QuestionnaireImfo_btn.setEnabled(false);
		}

		// /////////////추가/////////////
		if (LayoutComponentBean.resetFlag) {
			LayoutComponentBean.QuestionnaireInitial_btn
					.setBackgroundResource(R.drawable.ana_bootmode_re_setting);
		} else {
			LayoutComponentBean.QuestionnaireInitial_btn
					.setBackgroundResource(R.drawable.ana_bootmode_setting);
		}

		// ///////////////////////////////

	}
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
			Toast.makeText(this, "Back키를 누르셨군요 시스템이 종료될수 있으니 BackButton은 삼가해주세요", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	public void ButtonEvent() {
		LayoutComponentBean.statisticsGraph_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(AnA_BootMode.this,
								PieChartBuild.class);
						startActivity(intent);

					}
				});
		LayoutComponentBean.QuestionnaireInitial_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (LayoutComponentBean.ScreenCount != 0)
							DataSturct.vector.clear();
						Intent intent = new Intent(AnA_BootMode.this,
								QuestionnaireSettingFirst.class);
						startActivity(intent);

					}
				});
		LayoutComponentBean.QuestionnaireImfo_btn
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.bootting);
		setContentView(R.layout.bootting_optimus);
		LayoutInitial();
		ButtonEvent();
	}

}
