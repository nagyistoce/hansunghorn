package answer_ask_Service.TV;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import answer_ask_BeanTV.LayoutComponentBean;

public class QuestionnaireSettingFirst extends Activity implements DataSturct {

	private Boolean UserFlag;
	final static String SEPARATOR = "|";

	public void Interfaceinital() {
		UserFlag = false;
		LayoutComponentBean.Company = (EditText) findViewById(R.id.CompanyNameEditText);
		LayoutComponentBean.During = (EditText) findViewById(R.id.DuringEditText);
		LayoutComponentBean.FileAdd = (Button) findViewById(R.id.FileAddButton);
		LayoutComponentBean.UserImfo = (CheckBox) findViewById(R.id.UserInfoCheckBox);
		LayoutComponentBean.Next = (Button) findViewById(R.id.NextButton);

	}



	public void ButtonEvent() {
		LayoutComponentBean.Next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vector.add(SEPARATOR + "Company" + SEPARATOR
						+ LayoutComponentBean.Company.getText() + SEPARATOR + "During" + SEPARATOR
						+ LayoutComponentBean.During.getText() + SEPARATOR + "UserImfo" + SEPARATOR
						+ UserFlag);
				Intent intent = new Intent(QuestionnaireSettingFirst.this,
						QuestionnairesSettingSecond.class);
				startActivity(intent);
			}

		});
		LayoutComponentBean.UserImfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		setContentView(R.layout.questionnaire1);
		Interfaceinital();
		ButtonEvent();
	}

}
