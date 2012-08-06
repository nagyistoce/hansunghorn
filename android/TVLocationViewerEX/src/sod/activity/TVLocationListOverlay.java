package sod.activity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class TVLocationListOverlay extends ItemizedOverlay<OverlayItem> {

	private Context mContext;
	private ArrayList<OverlayItem> mOverlays =
			new ArrayList<OverlayItem>();
	
	public TVLocationListOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	public TVLocationListOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	//마커를 클릭하면 대응하는 토스트 메세지를 출력
	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		SODOverlayItem item = (SODOverlayItem)mOverlays.get(index);
		Toast.makeText(mContext, item.getSnippet(), Toast.LENGTH_SHORT).show();
		
		return true;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}

}
