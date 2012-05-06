package sod.smarttv;

import java.util.List;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import sod.test.smarttv.R;
import sod.test.smarttv.R.drawable;
import sod.test.smarttv.R.id;
import sod.test.smarttv.R.layout;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class TVLocationManagerActivity extends MapActivity {
    /** Called when the activity is first created. */
	List<Overlay> listOfOverlays;
	PinOverlay pinOverlay;
	MapView map;
	
	class MapOverlay extends com.google.android.maps.Overlay{

		@Override
		public boolean onTouchEvent(MotionEvent e, MapView mapView) {
			// TODO Auto-generated method stub
			if(e.getAction() == 1){
				GeoPoint selectedGp = 
						mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
				
				Toast.makeText(getBaseContext(), selectedGp.getLatitudeE6() / 1E6 + "," + selectedGp.getLongitudeE6() , Toast.LENGTH_SHORT).show();
				
				OverlayItem overlayItem = new OverlayItem(selectedGp, "서울시청", "서울시청입니다.");
				
				pinOverlay.clearOverlay();
				pinOverlay.addOverlay(overlayItem);
			}
			
			return false;
		}
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        map = (MapView)findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
        MapController mc = map.getController();
        mc.setZoom(10);
        
        listOfOverlays = map.getOverlays();
        
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        pinOverlay = new PinOverlay(drawable, this);
        
        OverlayItem overlayItem=
        		new OverlayItem(new GeoPoint(37566535, 126977969),
        				"서울시청", "서울시청입니다");
        pinOverlay.addOverlay(overlayItem);
        
        MapOverlay mapOverlay = new MapOverlay();
        
        listOfOverlays.add(mapOverlay);
        map.postInvalidate();
        listOfOverlays.add(pinOverlay);
        
        LocationManager locationManager = 
        		(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location =
        		locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        double latitude,longitude;
        
        if(location != null){
        	latitude = location.getLatitude();
        	longitude = location.getLongitude();
        }else{
        	latitude = 37.565263;
        	longitude = 126.980667;
        }
        
        GeoPoint p = new GeoPoint( (int)(latitude*1E6), (int)(longitude*1E6));
        mc.animateTo(p);
        
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}