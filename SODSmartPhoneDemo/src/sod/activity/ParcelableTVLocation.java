package sod.activity;

import sod.smartphone.TVLocation;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableTVLocation implements Parcelable{

	static private TVLocation mTVLocation = null;
	static ParcelableTVLocation obj = null;
	
	ParcelableTVLocation(TVLocation tvLocation){
		mTVLocation = tvLocation;
		obj = this;
	}
	
	public TVLocation getTvLocation(){
		return mTVLocation;
	}
	
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static final Parcelable.Creator<ParcelableTVLocation> CREATOR =
			new Parcelable.Creator<ParcelableTVLocation>() {

				public ParcelableTVLocation createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					return obj;
				}

				public ParcelableTVLocation[] newArray(int size) {
					// TODO Auto-generated method stub
					return new ParcelableTVLocation[size];
				}
		
			};

}
