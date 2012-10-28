package sod.smarttv;

import android.util.Log;

public class TimeoutThread extends Thread {
	Object serviceMonitor;
	int timeout;
	boolean isStopRequested = false;
	
	final int sleepterm = 10;
	
	public TimeoutThread(Object obj, int time)
	{
		serviceMonitor = obj;
		timeout = time;
	}
	
	public void run(){
		try {
			int elapsed = 0;
			
			while(isStopRequested && elapsed < timeout){
				elapsed += sleepterm;
				this.sleep(sleepterm);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		if(!isStopRequested){
			synchronized (serviceMonitor) {
				serviceMonitor.notify();
				Log.i("down", "timeout notify() order hold");
			} // end synchronized...
		}//end if...
		
	}
	
	public void requestStop(){
		isStopRequested = true;
	}
}
