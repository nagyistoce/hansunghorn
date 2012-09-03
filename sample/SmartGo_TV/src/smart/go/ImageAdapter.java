package smart.go;


import smart.go.R;
import smart.go.R.drawable;
import smart.go.R.styleable;

import android.content.*;
import android.content.res.*;
import android.view.*;
import android.widget.*;

public class ImageAdapter extends BaseAdapter {
	int mGalleryItemBackground;
	private Context mContext;
	private Integer[] mImageIds = { R.drawable.go01, R.drawable.go02,
			R.drawable.go03, R.drawable.go04, R.drawable.go05,
			R.drawable.go06, R.drawable.go07 };

	public ImageAdapter(Context c) {
		mContext = c;
		TypedArray a = c.obtainStyledAttributes(R.styleable.GalleryTheme);
		mGalleryItemBackground = a.getResourceId(
				R.styleable.GalleryTheme_android_galleryItemBackground, 0);
		a.recycle();
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		i.setImageResource(mImageIds[position]);
		i.setLayoutParams(new Gallery.LayoutParams(400, 670));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setBackgroundResource(mGalleryItemBackground);
		
		
		return i;
	}
	
	
}
