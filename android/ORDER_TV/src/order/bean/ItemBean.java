package order.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemBean implements Parcelable
{

	private String name;				// �丮 �̸�
	private String description;		// �丮 ����
	private boolean recommandFlag;	// ��õ �÷���
	
	private String price;		// ����
	private String category;	// ���� ī�װ�
	private String rating="0"; //����
	private String[] review; // ���� ����
	private Bitmap img;				// �丮 �׸�
	public static ItemBean obj;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean getRecommandFlag() {
		return recommandFlag;
	}
	public Bitmap getImg() {
		return img;
	}
	public void setImg(Bitmap img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isRecommandFlag() {
		return recommandFlag;
	}
	public void setRecommandFlag(Boolean recommandFlag) {
		this.recommandFlag = recommandFlag;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String[] getReview() {
		return review;
	}
	public void setReview(String[] review) {
		this.review = review;
	}
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	public static final Parcelable.Creator<ItemBean> CREATOR = new Parcelable.Creator<ItemBean>()
		    {
		        public ItemBean createFromParcel( Parcel in) { return obj; }
		        public ItemBean[] newArray(int size) {
		            return new ItemBean[size];
		        }
		    };
	
}
