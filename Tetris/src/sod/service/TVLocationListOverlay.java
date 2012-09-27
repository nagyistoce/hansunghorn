package sod.service;

import java.util.ArrayList;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

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

	//��Ŀ�� Ŭ���Ҷ� ������ �̺�Ʈ
	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		SODOverlayItem item = (SODOverlayItem)mOverlays.get(index);
		
		//Activity �� context�� ������ Intent�� �����Ѵ�.
		Intent intent = new Intent(mContext, TVinformationActivity.class);
		
		//���õ� ��ũ(�������̾������� ��ü)�� �����´�.
		SODOverlayItem sodOverlayItem = (SODOverlayItem) mOverlays.get(index);
		//��Ƽ��Ƽ�� ��ü ������ ���� Parcelable�� �̿��Ѵ�.
		ParcelableTVLocation parcelableTVLocation = new ParcelableTVLocation(sodOverlayItem.getTVLocation());
		intent.putExtra("tvlocation", parcelableTVLocation);
		//��Ƽ��Ƽ ȣ��
		mContext.startActivity(intent);
//		Toast.makeText(mContext, item.getSnippet(), Toast.LENGTH_SHORT).show();
		
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
