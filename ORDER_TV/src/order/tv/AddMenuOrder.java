package order.tv;


import order.bean.LayoutComponentBean;
import order.control.StorageControl;
import ana.tv.R;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMenuOrder extends Activity{
	static final CharSequence[] categorys=new String[]{
		"세트메뉴","덮밥류","면  류","사이드_주류"
	};
	public static CharSequence[] imgItem;
	ArrayAdapter<CharSequence> adapter;
	String category_str="",name="",description="",price="", imgFolder="이미지" ,imgUrl="";
	boolean recommandCheck=false;
	public void LayoutComponent()
    {
    	LayoutComponentBean.nameEdit=(EditText)findViewById(R.id.nameEditText);
    	LayoutComponentBean.priceEdit=(EditText)findViewById(R.id.priceEditText);
    	LayoutComponentBean.descriptionEdit=(EditText)findViewById(R.id.descriptionEditText);
    	LayoutComponentBean.sendIteminfo=(Button)findViewById(R.id.sendItemInfo);
    	LayoutComponentBean.uploadBitmap=(Button)findViewById(R.id.upLoadImgbtn);
    	LayoutComponentBean.categorySpinner=(Spinner)findViewById(R.id.categorySpinner);
    	LayoutComponentBean.imgEditText=(EditText)findViewById(R.id.imgEditText);
    	LayoutComponentBean.checkbox=(CheckBox)findViewById(R.id.RecommandCheckBox);
    	adapter=new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item, categorys);
    	LayoutComponentBean.categorySpinner.setAdapter(adapter);
    	LayoutComponentBean.categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    	

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				category_str=""+parent.getItemAtPosition(pos);
			Toast.makeText(parent.getContext(), "You have selected item : "+ parent.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}});
    	
    	LayoutComponentBean.sendIteminfo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				StorageControl storagecontrol=new StorageControl();
				try {
					storagecontrol.Store(""+LayoutComponentBean.nameEdit.getText());
					name=""+LayoutComponentBean.nameEdit.getText();
					description=""+LayoutComponentBean.descriptionEdit.getText();
					price=""+LayoutComponentBean.priceEdit.getText();
					imgUrl=""+LayoutComponentBean.imgEditText.getText();
					recommandCheck=LayoutComponentBean.checkbox.isChecked();
					storagecontrol.StoreSave(storagecontrol.StoreFileInitConfig(name, description, price, category_str,imgUrl,recommandCheck));
					Intent intent = new Intent(AddMenuOrder.this,ORDER_TVActivity.class);
				    startActivity(intent);
				    finish();
				} catch (Exception e) {
					Toast.makeText(AddMenuOrder.this, "파일저장이 안되었어요"+e, Toast.LENGTH_LONG).show();
				}
				
				//iteminfo.setImg(img)();
			}
		});
    	LayoutComponentBean.uploadBitmap.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				StorageControl storagecontrol=new StorageControl();
				try{
					storagecontrol.Store(imgFolder);
					imgItem=storagecontrol.getStoreList(imgFolder,"bmp");
					if(imgItem.equals(null))
					{
						Toast.makeText(AddMenuOrder.this, "이미지가 없습니다.", Toast.LENGTH_LONG).show();
					}
					else
					new AlertDialog.Builder(AddMenuOrder.this).setTitle("이미지").setItems(imgItem,new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							LayoutComponentBean.imgEditText.setText("/sod/order/이미지/"+imgItem[which]);
						}
					}).show();
				}catch(Exception e){}
			}
		});
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_item);
	        LayoutComponent();
	    }
}
