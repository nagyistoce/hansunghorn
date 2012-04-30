package Hansung.ac;

import java.util.Vector;

import Hansung.ac.TvManActivity.splashhandler;
import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class TvSetting extends Activity {
	EditText Company,during;
	CheckBox userimfo;
	Button fileadd,next;
	Boolean userTip;
	public void Interfaceinital() 
	{
		userTip=false;
        Company=(EditText)findViewById(R.id.companyName);
        during=(EditText)findViewById(R.id.during);
        fileadd=(Button)findViewById(R.id.fileadd);
        userimfo=(CheckBox)findViewById(R.id.userimfo);
        next=(Button)findViewById(R.id.Next);
        DataStructure.vec.removeAllElements();
	}
	public void ActionPlease()
	{
	next.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			DataStructure.vec.add("\nCompany\n"+Company.getText()+"\nDuring\n"+during.getText()+"\nUserImfo\n"+userTip);
			 Intent intent = new Intent(TvSetting.this,MainFrame.class);
	         startActivity(intent);
		}
		
	});
	userimfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			userTip=isChecked;
		}
	});
	}
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvsetting);
        Interfaceinital();
        ActionPlease();
    }


}
