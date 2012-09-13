package com.sgsong.main;

import sod.common.NetworkUtils;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

//import android.util.Log;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import android.opengl.GLSurfaceView;

/**
 * The initial Android Activity, setting and initiating
 * the OpenGL ES Renderer Class @see Lesson06.java
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Run extends Activity
//public class Run extends Activity implements OnGestureListener
 implements OnGestureListener, OnDoubleTapListener
{
	
	/** The OpenGL View */	
//	private MyGLView glSurface;
	
	private GLSurfaceView glSurface;
	
	private MainApp m_app;

	private GestureDetector detector = null;
	
	private boolean	m_bMouseDown = false;
	/**
	 * Initiate the OpenGL View and set our own
	 * Renderer (@see Lesson06.java)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//Create an Instance with this Activity		
		glSurface = new GLSurfaceView(this);
		
		//Set our own Renderer and hand the renderer this Activity Context
		
		m_app = new MainApp( this );
		glSurface.setRenderer(m_app);
	//	glSurface.setApp( m_app );	
		
	//	detector = new GestureDetector(this);
		
		
		//Set the GLSurface as View to this Activity
		setContentView(glSurface);
	}
	WifiManager getWifiManager() {
		return (WifiManager)getSystemService(Context.WIFI_SERVICE);
	}

	public String getLocalIpAddress() {
		// need to
		// <uses-permission
		// android:name="android.permission.ACCESS_WIFI_STATE"/>
		WifiManager wifiManager = getWifiManager();
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();

		byte[] bytes = int2byte(ipAddress);
		int[] values = new int[4];

		for (int i = 0; i < 4; i++)
			values[i] = bytes[i] & 0xFF;

		String ipStr = String.format("%d.%d.%d.%d", values[3], values[2],
				values[1], values[0]);

		return ipStr;

	}
	final byte[] int2byte(int i) {
		byte[] dest = new byte[4];
		dest[3] = (byte) (i & 0xff);
		dest[2] = (byte) (i >> 8 & 0xff);
		dest[1] = (byte) (i >> 16 & 0xff);
		dest[0] = (byte) (i >> 24 & 0xff);

		return dest;
	}
	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		glSurface.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurface.onPause();
	}
	
	@Override
	 public boolean onTouchEvent(MotionEvent event)  // Touch 이벤트
	{
	  if (detector != null)
	  {
	   return detector.onTouchEvent(event);
	  }
	  else
	  {
	//	  return super.onTouchEvent(event);
		  
		  switch (event.getAction())
		  {
			case MotionEvent.ACTION_DOWN:
				onTouchDown( (int) event.getX(), (int) event.getY() );				
				break;
				
			case MotionEvent.ACTION_UP:
				onTouchUp( (int) event.getX(), (int) event.getY() );				
				break;

		//	case MotionEvent.ACTION_MOVE:
		//		onTouchMove( (int) event.getX(), (int) event.getY() );				
		//		break;
				
			default:
				return super.onTouchEvent(event);
			}
		  
			return true;
	  }
	 }
	
	private void onTouchDown( int nX, int nY )
	{
		// 두번째 터치는 걸름
		if( true == m_bMouseDown )
		{
			return;
		}
		
		m_bMouseDown = true;
		
		Log.i("[SGSG]", "onTouchDown" );
		
		m_app.onTouchDown(nX, nY);
	}
	
	private void onTouchUp( int nX, int nY )
	{
		if( m_bMouseDown == true )
		{
			Log.i("[SGSG]", "ACTION_UP" );
			m_app.onTouchUp(nX, nY);
			
			m_bMouseDown = false;
		}		
	}
	
	private void onTouchMove( int nX, int nY )
	{
		if( m_bMouseDown == true )
		{
		//	Log.i("[SGSG]", "onTouchMove" );
			m_app.onTouchMove(nX, nY);
		}		
	}
	
//	@Override
	public boolean onDown(MotionEvent e)
	{
	//	Log.e("[SGSG]", "onDown" );
		return false;
	}
	
//	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
	//	Log.e("[SGSG]", "onFling" );		
		
		m_app.onFling( (int) e1.getX(), (int) e1.getY(), (int) e2.getX(), (int) e2.getY(), velocityX, velocityY );
		
		return false;
	}
	
//	@Override
	public void onLongPress( MotionEvent e )
	{
	//	Log.e("[SGSG]", "onLongPress" );
		
		m_app.onLongClick( (int) e.getX(), (int) e.getY() );
	}
	
//	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		return false;
	}
	
//	@Override
	public void onShowPress(MotionEvent e)
	{
	
	}
	
//	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
	//	Log.e("[SGSG]", "onSingleTapUp" );
		
		m_app.onSingleTapUp( (int) e.getX(), (int) e.getY() );
		
		return false;
	}
	
	public boolean onDoubleTap(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		return false;
	}
	
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		
	//	Log.e("[SGSG]", "onDoubleTapEvent" );
		
		m_app.onDoubleTap( (int) e.getX(), (int) e.getY() );
		
		return false;
	}

//	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	 
	
	
	
	
	
	
	
	
		
	
	
}