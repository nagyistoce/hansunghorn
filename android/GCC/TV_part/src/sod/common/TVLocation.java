package sod.common;



import com.google.android.maps.GeoPoint;

/**
 * 
 * @author MB
 *
 */
public class TVLocation {

	AdminInformation admin;
	
	
	/**
	 * ����
	 */
	double latitude;
	
	/**
	 * �浵
	 */
	double longitude;
	
	
	public TVLocation(){
		latitude = 0;
		longitude = 0;
	}
	
	/**
	 * ���� �浵 ���� �޾Ƽ� TVLocation ��ü�� �����Ѵ�.
	 * @param latitude ����
	 * @param longitude �浵
	 * @param admin ������ ����
	 */
	TVLocation(double  latitude, double longitude, AdminInformation admin){
		this.latitude = latitude;
		this.longitude = longitude;
		this.admin = admin;
	}
	
	/**
	 * ���������� �����Ѵ�.
	 * @param inLatitude �Է��� ���� ����
	 */
	public void setLatitude(double inLatitude){
		latitude = inLatitude;
	}
	
	/**
	 * �浵 ������ �����Ѵ�.
	 * @param inLongitude �Է��� �浵 ����
	 */
	public void setLogitude(double inLongitude){
		longitude = inLongitude;
	}
	
	/**
	 * ������ �浵 ������ ���ÿ� �����Ѵ�.
	 * @param inLatitude �Է��� ��������
	 * @param inLongitude �Է��� �浵����
	 */
	public void setTVLocationGeoPoint(double inLatitude, double inLongitude){
		latitude = inLatitude;
		longitude = inLongitude;
	}
	
	/**
	 * ���� �浵 ������ ���ÿ� �����Ѵ�.
	 * @param point ������ �浵 ������ ��� GeoPoint ��ü
	 */
	public void setTVLocationGeoPoint(GeoPoint point){
		latitude = point.getLatitudeE6()/1E6;
		longitude = point.getLongitudeE6()/1E6;
	}
	
	/**
	 * ���� ������ ��´�.
	 * @return ����
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * �浵 ������ ��´�.
	 * @return �浵
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * ���� �浵 ������ GeoPoint ��ü ���·� �޴´�.
	 * @return ����, �浵 ������ ��� GeoPoint ��ü
	 */
	public GeoPoint getGeoPoint(){
		return new GeoPoint((int)(latitude*1E6) ,(int)(longitude*1E6) );
	}
	
	/**
	 * ������ ������ �޴´�.
	 * @return ������ ����
	 */
	public AdminInformation getAdminInformation (){
		return admin;
	}
}