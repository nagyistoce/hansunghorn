package sod.service;

import java.net.SocketException;
import java.util.List;

import sod.smartphone.TVLocation;
import sod.smartphone.TVLocationViewer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TVLocationViewerActivity extends MapActivity {

	LocationListener listener;
	Location location;

	List<Overlay> mapOverlays;
	CurrentPinOverlay currentPinOverlay = null;

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.mapviewer);

		// 현재위치를 가져오기 위해 셋팅..////////////////////////////////////////////
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		updateLocation(location);

		// 맵뷰 설정////////////////////////////////////////////////
		final MapView mapViewer = (MapView) findViewById(R.id.mapviewer);
		mapViewer.setBuiltInZoomControls(true);
		mapViewer.setSatellite(false);

		MapController mapController = mapViewer.getController();

		mapController.setZoom(20);

		mapOverlays = mapViewer.getOverlays();
		Drawable drawable = getResources().getDrawable(R.drawable.pin);

		TVLocationListOverlay tvLocationListOverlay = new TVLocationListOverlay(
				drawable, this);

		TVLocationViewer tvLocationViewer = new TVLocationViewer();

		// 위치정보를 받아온다.
		TVLocation[] tvLocations = null;
		try {
			tvLocations = tvLocationViewer.getTVLocationList(0);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 맵에 받아온 위치정보들을 배치한다...////////////////////////////////////////////
		SODOverlayItem[] overItems = new SODOverlayItem[tvLocations.length];

		// TV
		for (int i = 0; i < tvLocations.length; i++) {
			overItems[i] = new SODOverlayItem(tvLocations[i].getGeoPoint(),
					"검색된것", "검색된것", tvLocations[i]);
			tvLocationListOverlay.addOverlay(overItems[i]);
		}
		// ///////////////////////////////////////////////////////////////////////

		drawable = getResources().getDrawable(R.drawable.ic_launcher);
		currentPinOverlay = new CurrentPinOverlay(this, mapViewer);

		// TV들의 위치들을 추가한다.
		mapOverlays.add(tvLocationListOverlay);
		mapOverlays.add(currentPinOverlay);

		currentPinOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				mapViewer.getController().animateTo(
						currentPinOverlay.getMyLocation());
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		currentPinOverlay.disableMyLocation();
		currentPinOverlay.disableCompass();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		currentPinOverlay.enableMyLocation();
		currentPinOverlay.enableCompass();
	}

	private void updateLocation(Location location) {

		String s = null;
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();

			this.location.setLatitude(lat);
			this.location.setLongitude(lng);

		} else {
			s = "위치파악 안됨";
		}
	}

}
