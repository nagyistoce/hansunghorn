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

public class QuestionnaireSetting extends Activity implements DataSturct {
	private EditText Company, During;
	private CheckBox UserImfo;
	private Button FileAdd, Next;
	private Boolean UserFlag;
	final static String SEPARATOR = "|";

	public void Interfaceinital() {
		UserFlag = false;
		Company = (EditText) findViewById(R.id.CompanyNameEditText);
		During = (EditText) findViewById(R.id.DuringEditText);
		FileAdd = (Button) findViewById(R.id.FileAddButton);
		UserImfo = (CheckBox) findViewById(R.id.UserInfoCheckBox);
		Next = (Button) findViewById(R.id.NextButton);

	}

	public void ButtonEvent() {
		Next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vector.add(SEPARATOR + "Company" + SEPARATOR
						+ Company.getText() + SEPARATOR + "During" + SEPARATOR
						+ During.getText() + SEPARATOR + "UserImfo" + SEPARATOR
						+ UserFlag);
				Intent intent = new Intent(QuestionnaireSetting.this,
						QuestionnairesMain.class);
				startActivity(intent);
			}

		});
		UserImfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		setContentView(R.layout.tvsetting);
		Interfaceinital();
		ButtonEvent();
	}

}
