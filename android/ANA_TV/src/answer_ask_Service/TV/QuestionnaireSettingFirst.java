package answer_ask_Service.TV;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import answer_ask_BeanTV.DataSturct;
import answer_ask_BeanTV.LayoutComponentBean;

public class QuestionnaireSettingFirst extends Activity implements DataSturct {

	private Boolean UserFlag;
	final static String SEPARATOR = "|";

	public void Interfaceinital() {
		UserFlag = false;
		LayoutComponentBean.Company = (EditText) findViewById(R.id.CompanyNameEditText);
		LayoutComponentBean.During = (EditText) findViewById(R.id.DuringEditText);
		LayoutComponentBean.FileAdd = (ImageButton) findViewById(R.id.FileAddButton);
		LayoutComponentBean.UserImfo = (CheckBox) findViewById(R.id.UserInfoCheckBox);
		LayoutComponentBean.Next = (ImageButton) findViewById(R.id.NextButton);

		// 이부분 데모 때문에 넣은 부분/////////////////////////////
		LayoutComponentBean.Company.setText("Hansung University");
		LayoutComponentBean.During.setText("2012.01.01~2012.05.23");
		LayoutComponentBean.UserImfo.setChecked(true);
		// ///////////////////////////////////////////////////////
	}

	public void ButtonEvent() {
		LayoutComponentBean.Next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vector.add(SEPARATOR + "Company" + SEPARATOR
						+ LayoutComponentBean.Company.getText() + SEPARATOR
						+ "During" + SEPARATOR
						+ LayoutComponentBean.During.getText() + SEPARATOR
						+ "UserImfo" + SEPARATOR + UserFlag);
				Intent intent = new Intent(QuestionnaireSettingFirst.this,
						QuestionnairesSettingSecond.class);
				startActivity(intent);
				finish();
			}

		});
		LayoutComponentBean.UserImfo
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						UserFlag = isChecked;
					}
				});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.questionnaire1);
		setContentView(R.layout.questionnaire1);
		Interfaceinital();
		ButtonEvent();
	}

}
