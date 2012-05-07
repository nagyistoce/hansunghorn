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
	
	TVLocation(){
		latitude = 0;
		longitude = 0;
	}
	
	void setLatitude(double inLatitude){
		latitude = inLatitude;
	}
	
	void setLogitude(double inLongitude){
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