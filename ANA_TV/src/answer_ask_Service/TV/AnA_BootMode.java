package answer_ask_Service.TV;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import answer_ask_BeanTV.LayoutComponentBean;

public class AnA_BootMode extends Activity {

	void LayoutInitial() {

		LayoutComponentBean.statisticsGraph_btn = (Button)findViewById(R.id.statisticsGraph);
		LayoutComponentBean.QuestionnaireInitial_btn = (Button) findViewById(R.id.QuestionnaireInitialSettinig);
		LayoutComponentBean.QuestionnaireImfo_btn = (Button) findViewById(R.id.QuestionnaireSettingImfomation);
		if (LayoutComponentBean.ScreenCount == 0) {
			LayoutComponentBean.statisticsGraph_btn.setEnabled(false);
			LayoutComponentBean.QuestionnaireImfo_btn.setEnabled(false);
		}

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
		setContentView(R.layout.bootting);
		LayoutInitial();
		ButtonEvent();
	}

}
