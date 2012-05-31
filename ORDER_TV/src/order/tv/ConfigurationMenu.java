package order.tv;

import order.bean.ItemBean;
import order.bean.LayoutComponentBean;
import ana.tv.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigurationMenu extends Activity {
	private ItemBean bean;
	final static int MaxStringLengh = 20;
	String Form_price = "가    격 : ";
	String Form_description = "설    명 : ";
	String Form_category = "분    류  : ";
	String Form_rating = "평    점 : ";
	String Form_recommandFlag = "추천여부 : ";
	String temp;
	int j = 0;

	public void Init() {
		Bundle bundle = getIntent().getExtras();
		bean = bundle.getParcelable("Item");
		LayoutComponentBean.Menutitle = (TextView) findViewById(R.id.Menutitle);
		LayoutComponentBean.MenuImg = (ImageView) findViewById(R.id.MenuImg);
		LayoutComponentBean.introduceMenu = (TextView) findViewById(R.id.introduceMenu);
		LayoutComponentBean.Menutitle.setText(bean.getName());
		if (bean.getRating() == -1)
			bean.setRating(0);
		if(!bean.getDescription().equals(null)){
		for (int i = 0; i < bean.getDescription().length(); i++) {
			temp += "" + bean.getDescription().charAt(i);
			if (bean.getDescription().length() % MaxStringLengh == 0) {
				temp += "\n";
			}
		}
		}
		LayoutComponentBean.introduceMenu.setText(Form_category
				+ bean.getCategory() + "\n" + Form_price + bean.getPrice()
				+ "원\n" + Form_rating + bean.getRating() + "점\n"
				+ Form_recommandFlag + bean.getRecommandFlag() + "개\n"
				+ Form_description + bean.getDescription() + "\n");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_item);
		Init();
	}
}
