package sod.common.storagetest;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        EditText editText1  = (EditText)findViewById(R.id.editText1);
        EditText editText2  = (EditText)findViewById(R.id.editText2);
        EditText editText3  = (EditText)findViewById(R.id.editText3);
        
        try {
        	
        	
        	
        	Storage storage;
        	if(Storage.checkIsStorageExists("test"))
        		storage = Storage.getStorage("test");
        	else
        		storage = Storage.createStorage("test");
        	
        	storage.setImageStorage("asas");
        	
        	editText1.setText(storage.getImageStorageId());
        	
        	//
        	
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			//editText2.setText("IllegalArgumentException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//editText2.setText("IOException");
		} 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
