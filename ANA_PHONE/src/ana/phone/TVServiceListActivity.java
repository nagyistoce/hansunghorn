package ana.phone;

import java.io.IOException;
import java.util.ArrayList;

import sod.common.Storage;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class TVServiceListActivity extends ListActivity {
	
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	Storage rootStorage;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		list = new ArrayList<String>();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		
		updateServiceList();
		
		
	}//end onCreate
	
	void updateServiceList() {
		try {

			if (Storage.checkIsStorageExists(""))
				rootStorage = Storage.getStorage("");
			else
				rootStorage = Storage.createStorage("");

			String[] serviceNameList = rootStorage.getFileList();

			for (String serviceName : serviceNameList) {
				list.add(serviceName);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter.notifyDataSetChanged();
	}

}
