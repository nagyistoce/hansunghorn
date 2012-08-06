package sod.activity;



import android.content.Context;
import android.widget.Toast;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;


public class CurrentPinOverlay extends 	MyLocationOverlay{
	
	Context mContext;

	public CurrentPinOverlay(Context arg0, MapView arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		mContext = arg0;
	}

	@Override
	protected boolean dispatchTap() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "여기가 현재위치입니다.",  Toast.LENGTH_SHORT).show();
		return false;
	}
	
	
	
	/*
	private Context mContext;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public CurrentPinOverlay(Drawable defaultMarker) {
		super( boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public CurrentPinOverlay(Drawable defaultMarker, Context context) {
		super( boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	public void addOverlay(OverlayItem overlay){
		mOverlays.add(overlay);
		populate();
	}
	
	public void clearOverlay(){
		mOverlays.clear();
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	*/
}
