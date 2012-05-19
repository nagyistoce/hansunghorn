package sod.activity;

import sod.smartphone.TVLocation;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class SODOverlayItem extends OverlayItem {
	
	TVLocation tvLocation;

	public SODOverlayItem(GeoPoint point, String title, String snippet, TVLocation tvLocation) {
		super(point, title, snippet);
		// TODO Auto-generated constructor stub
		this.tvLocation = tvLocation;
	}

}
