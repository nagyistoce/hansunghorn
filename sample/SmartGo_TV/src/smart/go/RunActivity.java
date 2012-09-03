package smart.go;

import smart.go.Draw.SG;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * The initial Android Activity, setting and initiating
 * the OpenGL ES Renderer Class @see Lesson06.java
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
 
public class RunActivity extends Activity implements OnGestureListener, OnDoubleTapListener{	
	/** The OpenGL View */	
	//	private MyGLView glSurface;	
	private GLSurfaceView glSurface;	
	private MainApp m_app;
	private GestureDetector detector = null;	
	private boolean	m_bMouseDown = false;
	int size = 0;


	/**
	 * Initiate the OpenGL View and set our own
	 * Renderer (@see Lesson06.java)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//Create an Instance with this Activity	


		glSurface = new GLSurfaceView(this);

		//Set our own Renderer and hand the renderer this Activity Context		
		m_app = new MainApp( this );
		glSurface.setRenderer(m_app);
		//	glSurface.setApp(m_app);			
		//	detector = new GestureDetector(this);		
		//Set the GLSurface as View to this Activity
		setContentView(glSurface);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		size = SG.getSizeFlag();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.setQwertyMode(true);
		menu.add(0, 1, 1, "N-Screen");
		menu.add(0, 2, 2, "Remote");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(m_app.getMain().whoTurn()) {
			switch (item.getItemId()) {
			case 1:
				m_app.getMain().KARAMset();			
				break;
			case 2:
				m_app.getMain().KARAMremote();
				break;
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (detector != null)
		{
			return detector.onTouchEvent(event);
		}
		else
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				if(SG.getSizeFlag() == 1){
					onTouchDown( (int)( event.getX()*0.42), (int)( event.getY()*0.42) + 40 );
				}else{
					onTouchDown((int) event.getX(), (int) event.getY());
				}break;

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

		m_app.onTouchDown(nX, nY);
	}

	private void onTouchUp( int nX, int nY )
	{
		if( m_bMouseDown == true )
		{
			m_app.onTouchUp(nX, nY);

			m_bMouseDown = false;
		}		
	}

	private void onTouchMove( int nX, int nY )
	{
		if( m_bMouseDown == true )
		{
			m_app.onTouchMove(nX, nY);
		}		
	}

	//	@Override
	public boolean onDown(MotionEvent e)
	{
		return false;
	}

	//	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{	
		m_app.onFling( (int) e1.getX(), (int) e1.getY(), (int) e2.getX(), (int) e2.getY(), velocityX, velocityY );

		return false;
	}

	//	@Override
	public void onLongPress( MotionEvent e )
	{
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
		m_app.onSingleTapUp( (int) e.getX(), (int) e.getY() );

		return false;
	}

	public boolean onDoubleTap(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		m_app.onDoubleTap( (int) e.getX(), (int) e.getY() );

		return false;
	}

	//	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	DialogInterface.OnClickListener dlistener = new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog, int which) {

			if (which == Dialog.BUTTON_POSITIVE) {

				android.os.Process.killProcess(android.os.Process.myPid());
			}

		}

	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog alert = new AlertDialog.Builder(RunActivity.this)

		.setTitle("종료확인").setMessage("프로그램을 종료 하시겠습니까?")
		.setPositiveButton("네", dlistener)
		.setNegativeButton("아니오", dlistener).show();
	}
}