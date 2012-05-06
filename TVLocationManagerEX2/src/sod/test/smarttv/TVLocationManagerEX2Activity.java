package sod.test.smarttv;


import sod.test.smarttv.R;
import sod.test.smarttv.TVLocationManagerEX2Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TVLocationManagerEX2Activity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button button = (Button) findViewById(R.id.button);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(TVLocationManagerEX2Activity.this, sod.smarttv.TVLocationManagerActivity.class);
				
				startActivity(intent);;

			}
		});
	}
}