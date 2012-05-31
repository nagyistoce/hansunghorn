package order.tv;

import java.io.File;
import java.util.ArrayList;

import order.bean.ItemBean;
import order.control.StorageControl;
import order.control.parsingFile;
import order.tv.ModiFyMenuOrder.Iconinfo;
import ana.tv.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ToTalMenuOrder extends Activity {
	private ListView mList;
	private IconAdapter mAdapter;
	private TextView textview;
	private ImageView imageview;
	private StorageControl storagecontrol = new StorageControl();
	public static String[] items = null;
	public static int p = 0;
	public parsingFile psfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_item);

		mAdapter = new IconAdapter();
		mList = (ListView) findViewById(android.R.id.list);
		mList.setAdapter(mAdapter);
		try {
			setupListItem();
		} catch (Exception e) {
		}
		mList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adater, View view, int pos,
					long arg3) {
				StorageControl a = new StorageControl();
				try {
					p=pos;
					psfile = new parsingFile();
					ItemBean bean=new ItemBean();
					File file=a.Select(ToTalMenuOrder.items[ToTalMenuOrder.p]);
					bean=psfile.Parsing(file);
					ItemBean.obj=bean;		
					Toast.makeText(ToTalMenuOrder.this, "클릭해쓰요 : "+pos, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(ToTalMenuOrder.this,
							ConfigurationMenu.class);
					intent.putExtra("Item", bean);
					
					startActivity(intent);
				} catch (Exception e) {
				}
			}

		});
	}

	private void setupListItem() {
		try {
			this.items = storagecontrol.getStoreList("*");
			int[] icons = { android.R.drawable.ic_menu_add,

			android.R.drawable.ic_menu_agenda,
					android.R.drawable.ic_menu_camera,
					android.R.drawable.ic_menu_edit,
					android.R.drawable.ic_menu_add,
					android.R.drawable.ic_menu_compass,
					android.R.drawable.ic_menu_manage };

			mAdapter.clear();

			for (int i = 0; i < items.length; i++) {
				final Iconinfo info = new Iconinfo();
				info.icon = icons[i];
				if (items[i] == null) {
					info.text = items[i - 1];
				} else {
					info.text = items[i];
				}
				mAdapter.add(info);
			}

			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Toast.makeText(ToTalMenuOrder.this, "" + e, Toast.LENGTH_LONG)
					.show();
		}
	}

	private class IconAdapter extends BaseAdapter {
		private ArrayList<Iconinfo> mIconArrayList = new ArrayList<Iconinfo>();

		public void clear() {
			mIconArrayList.clear();
		}

		public void onListItemClick(ListView l, View v, int position, long id) {
			return;
		}

		public void add(Iconinfo item) {
			mIconArrayList.add(item);
		}

		public int getCount() {
			return mIconArrayList.size();
		}

		public Object getItem(int position) {
			return mIconArrayList.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.icon_list_item, parent, false);
			}

			Iconinfo info = mIconArrayList.get(position);
			textview = (TextView) convertView.findViewById(R.id.text1);
			textview.setText(info.text);
			imageview = (ImageView) convertView.findViewById(R.id.icon);
			imageview.setImageResource(info.icon);
			return convertView;
		}
	}

	public class Iconinfo {
		private int icon;
		private String text;
	}
}
