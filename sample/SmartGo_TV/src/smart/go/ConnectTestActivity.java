package smart.go;

import java.util.ArrayList;

import smart.go.Game.Client;
import smart.go.Game.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConnectTestActivity extends Activity implements SensorEventListener {

	/** Called when the activity is first created. */

	ImageView[] imageView;
	// 서버와 소켓통신을 위한 클래스 호출(패키지안에 있음)
	Client connect;
	Common c;
	//int check_sun = 0;
	// 모션인식을 위해 필요한 인자
	private long lastTime = 0;
	int lastPitch=0;

	int num = 0; // 패 번호
	int imgId;
	int getNum = 0;

	public ArrayList<Integer> m_remoteList = new ArrayList<Integer>();
	// 서버에서 가져온 번호들을 저장하는 배열
	ArrayList<Integer> getCardNumber;

	// 모션 카운트
	private int motionCnt = 0;

	private int ShutdownCount;

	// 어떤 패가 선택됐는지 확인하는 배열
	private String str;

	private Gallery g;
	// 갤러리를 사용하기 위한 이미지 어댑터
	private ImageAdapter imageAdapter;

	// 밑에 받은패 전체를 보여주기 위한 레이아웃(dotpannel)
	private LinearLayout layout;

	// 서버에서 가져온 숫자로 이미지를 만들기 위한 인티져형 배열
	private Integer[] mImageIds = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 타이틀바 없애기 위함
		setContentView(R.layout.gallerytest);

		connect = new Client();
		c = new Common();

		connect.init(c);
		c.Wait();


		// 서버로 부터 받아온 1~48까지의 숫자 10개를 가지고
		// R.drawable.cardimage01(일광)을 기준으로 +getCardNumber[i]-1을 하여
		// 해당하는 숫자의 패 이미지 아이디를 가져온다.(drawable내에서 처음이 1이고 그다음 차곡차곡 +1이므로

		// 여기다 if문 걸어서 선 후 패 설정해할듯.
		imgId = R.drawable.cardimage01;
		int j = 0;

		m_remoteList = connect.getRemoteList();
		//check_sun = 1;
		mImageIds = new Integer[m_remoteList.size()];

		for(int i=0; i<m_remoteList.size(); i++) {
			mImageIds[i] = imgId + (m_remoteList.get(i) - 400);
		}
		// 어댑터 등록
		imageAdapter = new ImageAdapter(this);

		// 센서값이 바뀔때마다 수정되야 하므로 리스너를 등록한다.

		SensorManager sensorM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// 센서 리스너 객체(센서이벤트리스너 임플리먼츠), 센서타입, 센서 민감도를 매니져에 등록한다.
		sensorM.registerListener(
				this,// Activity가 직접 리스너를 구현

				sensorM.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);

		g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(imageAdapter);

		/*
		 * g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * public void onItemClick(AdapterView parent, View v, int position,
		 * long id) {
		 * 
		 * } });
		 */

		// 이건 왜있는건지 모르겠음 셀렉트 만들려다가 그만둔거 같음.
		g.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// selectGallery(position);
				num = position;

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		layout = (LinearLayout) findViewById(R.id.dotPanel);

		// 패널 붙이고 끝
		createDotPanel(this, layout, m_remoteList.size());

	}

	// 밑에꺼
	private void createDotPanel(Context context, LinearLayout layout, int count) {

		imageView = new ImageView[count];

		for (int i = 0; i < count; i++) {

			imageView[i] = new ImageView(context);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(3,
					0);
			// 이미지 작업이 수월하지 않아서 그냥 하드코딩으로 크기 줘버렸음.
			params.width = 46;
			params.height = 65;

			/*
			 * 이게 원래 코드임 params.width = LayoutParams.WRAP_CONTENT; params.height
			 * = LayoutParams.WRAP_CONTENT;
			 */
			params.topMargin = 2;
			params.leftMargin = 2;
			params.gravity = Gravity.CENTER;
			imageView[i].setId(i);

			imageView[i].setLayoutParams(params);
			imageView[i].setImageResource(R.drawable.cardimage01
					+ (m_remoteList.get(i) - 400));

			layout.addView(imageView[i]);
		}

		for (int i = 0; i < count; i++) {
			imageView[i].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					switch (v.getId()) {
					case 0:
						g.setSelection(0);
						break;
					case 1:
						g.setSelection(1);
						break;
					case 2:
						g.setSelection(2);
						break;
					case 3:
						g.setSelection(3);
						break;
					case 4:
						g.setSelection(4);
						break;
					case 5:
						g.setSelection(5);
						break;
					case 6:
						g.setSelection(6);
						break;
					case 7:
						g.setSelection(7);
						break;
					case 8:
						g.setSelection(8);
						break;
					case 9:
						g.setSelection(9);
						break;
					default:
						break;

					}
					// Toast.makeText(ConnectTestActivity.this, imgId,
					// Toast.LENGTH_SHORT).show();

				}
			});
		}
	}

	// 패 선택후 난다음에 지우고 다시 리페인트
	private void reCreateDotPanel(Context context, LinearLayout layout,
			int count, int position) {
		ImageView[] newImageView = new ImageView[count];
		int pos = position;
		for (int i = 0; i < count; i++) {
			if (pos != i) {
				newImageView[i] = imageView[i];
			} else {
				imageView[i + 1].setId(i);
				newImageView[i] = imageView[i + 1];

				pos++;
			}
		}
		imageView = new ImageView[count];
		imageView = newImageView;

		for (int i = 0; i < imageView.length; i++) {
			layout.addView(imageView[i]);
		}

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		switch (event.sensor.getType()) {
		case Sensor.TYPE_ORIENTATION: // 방향센서 값이 바뀌었을때
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime > 400) {
				int pitch = (int) event.values[1];

				long gabOfPitch = (pitch - lastPitch);

				// TextView MyText = new TextView(this) ;

				lastTime = currentTime;
				Log.e("gab", ""+gabOfPitch);
				if (gabOfPitch > 60) {
					motionCnt++;
					lastPitch = pitch;					
					deleteGallery(num);
					connect.setMotion(num);
					sleep(500);
					c.WakeUp();
					c.Wait();
					Toast.makeText(ConnectTestActivity.this, "패를 선택하셨습니다!!!!!",
							Toast.LENGTH_SHORT).show();

				}
				lastPitch = pitch;
			}
			break;
		default:
			break;

		}

	}

	// 선택한패 지우는거
	private void deleteGallery(int shake) {
		int pos = shake;

		mImageIds[shake] = 0;
		Integer[] newImageIds = new Integer[mImageIds.length - 1];

		for (int i = 0; i < mImageIds.length - 1; i++) {
			if (i == pos) {
				newImageIds[i] = mImageIds[i + 1];
				mImageIds[i + 1] = 0;
				pos++;
			} else {
				newImageIds[i] = mImageIds[i];

			}
		}
		mImageIds = new Integer[mImageIds.length - 1];
		mImageIds = newImageIds;
		layout.removeAllViews();

		reCreateDotPanel(this, layout, mImageIds.length, shake);

		// selectGallery(shake);
		// layout.removeViewAt(shake);

		// imageView[shake].setVisibility(View.INVISIBLE);
		imageAdapter.notifyDataSetChanged();

	}

	// 갤러리에 이미지 작업을 위한 이미지 어댑터~
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private ImageView i;

		public ImageAdapter(Context c) {
			mContext = c;
			TypedArray a = c.obtainStyledAttributes(R.styleable.GalleryTheme);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			i = new ImageView(mContext);
			i.setImageResource(mImageIds[position]);
			i.setLayoutParams(new Gallery.LayoutParams(400, 670));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryItemBackground);

			return i;
		}

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
		AlertDialog alert = new AlertDialog.Builder(ConnectTestActivity.this)

		.setTitle("종료확인").setMessage("프로그램을 종료 하시겠습니까?")
		.setPositiveButton("네", dlistener)
		.setNegativeButton("아니오", dlistener).show();
	}
	
	public void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
