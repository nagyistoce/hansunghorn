package order.tv;

import order.bean.ConnectionBean;
import order.bean.LayoutComponentBean;
import order.control.Networking;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ana.tv.R;
import android.view.View.OnClickListener;

public class ORDER_TVActivity extends Activity {
	/** Called when the activity is first created. */

	public void LayoutComponent() {
		LayoutComponentBean.Total_Menu = (Button) findViewById(R.id.Total_Menu);
		LayoutComponentBean.AddMenuItem = (Button) findViewById(R.id.AddMenuItem);
		LayoutComponentBean.updateMenuItem = (Button) findViewById(R.id.updateMenuItem);
		LayoutComponentBean.Total_Menu
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								ToTalMenuOrder.class);
						startActivity(intent);
	
					}
				});
		LayoutComponentBean.AddMenuItem
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								AddMenuOrder.class);
						startActivity(intent);
						//finish();
					}
				});
		LayoutComponentBean.updateMenuItem
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(ORDER_TVActivity.this,
								ModiFyMenuOrder.class);
						startActivity(intent);
	
					}
				});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_order);
		LayoutComponent();
		if(ConnectionBean.pagecount==0)
		{
		new Networking().TVServerIni();
		ConnectionBean.server.start(ConnectionBean.ServerConfig);
		ConnectionBean.pagecount++;
		}
	}
}