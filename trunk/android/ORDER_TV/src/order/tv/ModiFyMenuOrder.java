package order.tv;

import java.io.File;
import java.util.ArrayList;

import order.bean.ConnectionBean;
import order.bean.ItemBean;
import order.control.StorageControl;
import order.control.parsingFile;
import ana.tv.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ModiFyMenuOrder extends Activity {
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
				p = pos;
				new AlertDialog.Builder(ModiFyMenuOrder.this)
						.setTitle("확인창")
						.setMessage("해당 메뉴를 삭제 하기겠습니까?")
						.setPositiveButton("예",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										StorageControl a = new StorageControl();
										try {
											a.DeleteStore(ModiFyMenuOrder.items[ModiFyMenuOrder.p+1]);
											Intent intent = new Intent(
													ModiFyMenuOrder.this,
													ModiFyMenuOrder.class);
											startActivity(intent);
											finish();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								})
						.setNegativeButton("아니오",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

										Intent intent = new Intent(
												ModiFyMenuOrder.this,
												ModiFyMenuOrder.class);
										startActivity(intent);
										finish();
									}
								}).show();
			}

		});
	}
	public Bitmap setupListImg(String item,ItemBean bean,parsingFile psfile){
		String temp = item;
		if (temp.equals("이미지")) {
			return null;
		} 
		else {
			temp = storagecontrol.Select(temp);

			try {
				bean = psfile.Parsing(temp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
BitmapFactory.Options options = new BitmapFactory.Options();
options.inSampleSize = 4;
Bitmap orgImage = BitmapFactory.decodeFile("mnt/sdcard/"+bean.getUrl(), options);
Bitmap resize = Bitmap.createScaledBitmap(orgImage, 300, 400, true);
		
return orgImage;
		}
		
	}
	private void setupListItem()throws Exception {
			try{
			ModiFyMenuOrder.items = storagecontrol.getStoreList("*");
			Bitmap [] icons=new Bitmap[ModiFyMenuOrder.items.length];
			ItemBean bean = new ItemBean();
			psfile=new parsingFile();
			for(int i=0;i<ModiFyMenuOrder.items.length;i++)
			{
				
				icons[i]=setupListImg(ModiFyMenuOrder.items[i],bean,psfile);

			}
				
			
			
			mAdapter.clear();

			for (int i = 0; i < items.length; i++) {
				final Iconinfo info = new Iconinfo();
				info.icon = icons[i];
				info.text = items[i];
				if (info.text.equals("이미지")) {
				} else {
					mAdapter.add(info);
				}
			}

			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Toast.makeText(ModiFyMenuOrder.this, "" + e, Toast.LENGTH_LONG)
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
			imageview.setImageBitmap(info.icon);
			return convertView;
		}
	}

	public class Iconinfo {
		private Bitmap icon;
		private String text;
	}
}
