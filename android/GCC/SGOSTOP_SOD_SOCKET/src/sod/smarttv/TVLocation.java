package sod.smarttv;

/**
 * 
 * @author MB
 * 
 */
public class TVLocation {

	/**
	 * 위도
	 */
	double latitude;
	
	/**
	 * 경도
	 */
	double longitude;
	
	public TVLocation(){
		latitude = 0;
		longitude = 0;
	}
	
	public void setLatitude(double inLatitude){
		latitude = inLatitude;
	}
	
	public void setLogitude(double inLongitude){
		longitude = inLongitude;
	}
	
	void setTVLocationGeoPoint(double inLatitude, double inLongitude){
		latitude = inLatitude;
		longitude = inLongitude;
	}
	
	double getLatitude(){
		return latitude;
	}
	
	double getLongitude(){
		return longitude;
	}

}