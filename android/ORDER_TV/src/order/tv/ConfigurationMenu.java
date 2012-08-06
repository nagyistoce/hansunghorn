package order.tv;

import order.bean.ItemBean;
import order.bean.LayoutComponentBean;
import ana.tv.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfigurationMenu extends Activity {
	private ItemBean bean;
	final static int MaxStringLengh = 20;
	String Form_price = "��    �� : ";
	String Form_description = "��    �� : ";
	String Form_category = "��    ��  : ";
	String Form_rating = "��    �� : ";
	String Form_recommandFlag = "��õ���� : ";
	String temp;
	int j = 0;

	public void Init() {
		Bundle bundle = getIntent().getExtras();
		bean = bundle.getParcelable("Item");
		LayoutComponentBean.Menutitle = (TextView) findViewById(R.id.Menutitle);
		LayoutComponentBean.MenuImg = (ImageView) findViewById(R.id.MenuImg);
		LayoutComponentBean.introduceMenu = (TextView) findViewById(R.id.introduceMenu);
		LayoutComponentBean.Menutitle.setText(bean.getName());
		if (bean.getRating().equals("0"))
			bean.setRating("0");
		if(!bean.getDescription().equals(null)){
		for (int i = 0; i < bean.getDescription().length(); i++) {
			temp += "" + bean.getDescription().charAt(i);
			if (bean.getDescription().length() % MaxStringLengh == 0) {
				temp += "\n";
			}
		}
		}
BitmapFactory.Options options = new BitmapFactory.Options();
options.inSampleSize = 4;
Bitmap orgImage = BitmapFactory.decodeFile("mnt/sdcard/"+bean.getUrl());
Bitmap resize = Bitmap.createScaledBitmap(orgImage, 300, 400, true);
LayoutComponentBean.MenuImg.setImageBitmap(orgImage);
		LayoutComponentBean.introduceMenu.setText(Form_category
				+ bean.getCategory() + "\n" + Form_price + bean.getPrice()
				+ "��\n" + Form_rating + bean.getRating() + "��\n"
				+ Form_recommandFlag + bean.getRecommandFlag() + "\n"
				+ Form_description + bean.getDescription() + "\n");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_item);
		Init();
	}
}
