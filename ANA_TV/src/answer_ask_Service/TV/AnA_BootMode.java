package answer_ask_Service.TV;

import java.net.SocketException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import answer_ask_Service.TV.TVManagerLoGo.splashhandler;

public class AnA_BootMode extends Activity {
	Button statisticsGraph_btn,QuestionnaireInitial_btn,QuestionnaireImfo_btn;
	void init()
	{
		 statisticsGraph_btn=(Button)findViewById(R.id.statisticsGraph);
	     QuestionnaireInitial_btn=(Button)findViewById(R.id.QuestionnaireInitialSettinig);
	     QuestionnaireImfo_btn=(Button)findViewById(R.id.QuestionnaireSettingImfomation);
	}
	public void ButtonEvent()
	{
		   statisticsGraph_btn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Intent intent = new Intent(AnA_BootMode.this,AnA_Graph.class);
			          startActivity(intent);
				}
			});
		       QuestionnaireInitial_btn.setOnClickListener(new OnClickListener() {
		   		
		   		public void onClick(View v) {
		   		  Intent intent = new Intent(AnA_BootMode.this,QuestionnaireSettingFirst.class);
		            startActivity(intent);
		   		}
		   	});
		       QuestionnaireImfo_btn.setOnClickListener(new OnClickListener() {
		   		
		   		public void onClick(View v) {
		   			// TODO Auto-generated method stub
		   			
		   		}
		   	});
	}
	public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.bootting);
       init();
       ButtonEvent();
    } 


}
