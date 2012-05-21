package answer_ask_Service.TV;

import java.net.SocketException;
import java.util.Vector;

import sod.common.Packet;
import sod.smartphone.AccessManager;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;
import answer_ask_BeanTV.LayoutComponentBean;
import answer_ask_Service.TV.TVManagerLoGo.splashhandler;

public class AnA_BootMode extends Activity {

	void LayoutInitial() {

		LayoutComponentBean.statisticsGraph_btn = (Button) findViewById(R.id.statisticsGraph);
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

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(AnA_BootMode.this,
								PieChartBuild.class);
						startActivity(intent);

					}
				});
		LayoutComponentBean.QuestionnaireInitial_btn
				.setOnClickListener(new OnClickListener() {

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

					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bootting);
		LayoutInitial();
		ButtonEvent();
	}

}
