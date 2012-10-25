/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package answer_ask_Service.TV;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import answer_ask_BeanTV.DataBean;
import answer_ask_BeanTV.DataSturct;
import answer_ask_BeanTV.FileBean;
import answer_ask_BeanTV.GraphBean;
import answer_ask_BeanTV.LayoutComponentBean;
import answer_ask_Service.TV.TVManagerLoGo.splashhandler;

public class PieChartBuild extends Activity {
	HashMap<String, Integer> value0 = null;
	Integer value1 = 0;
	String key0 = "";
	String key1;
	int GET_CODE = 0;
	Set keylist1 = null;
	Set keylist2 = null;
	String title = "";
	Handler handle;
	splashhandler splash;
	Vector<GraphBean> vec;
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.MAGENTA, Color.CYAN, Color.RED, Color.YELLOW,
			Color.rgb(99, 184, 255) };

	private CategorySeries mSeries = new CategorySeries(DataBean.Topic);

	private DefaultRenderer mRenderer = new DefaultRenderer();

	private String mDateFormat;
	double x = 0;
	// private Button mAdd;

	// private EditText mX;

	private GraphicalView mChartView;

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		mSeries = (CategorySeries) savedState.getSerializable("current_series");
		mRenderer = (DefaultRenderer) savedState
				.getSerializable("current_renderer");
		mDateFormat = savedState.getString("date_format");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("current_series", mSeries);
		outState.putSerializable("current_renderer", mRenderer);
		outState.putString("date_format", mDateFormat);
	}

	public void SortingHashmap() {

		boolean flag = false;

		keylist1 = FileBean.hashmap.keySet();
		int i = 0;
		for (Iterator iterator1 = keylist1.iterator(); iterator1.hasNext(); i++) {
			key0 = (String) iterator1.next();
			value0 = FileBean.hashmap.get(key0);
			keylist2 = value0.keySet();
			for (Iterator iterator2 = keylist2.iterator(); iterator2.hasNext();) {
				GraphBean graphbean = new GraphBean();
				key1 = (String) iterator2.next();
				value1 = value0.get(key1);
				graphbean.setPage(i);
				graphbean.setAnswer(key0);
				graphbean.setResponse(key1);
				graphbean.setCount(value1);
				if (DataBean.data_index == 0) {

					for (int j = 0; j < DataSturct.dataVector.size(); j++) {
						if (DataSturct.dataVector.get(j).getAnswer()
								.equals(key0)
								&& DataSturct.dataVector.get(j).getResponse()
										.equals(key1)) {
							DataSturct.dataVector.remove(j);
							flag = true;
						}
					}
					// if(flag=false)
					// {
					DataSturct.dataVector.add(graphbean);
					// }
				}
				// }

			}
			// FileBean.hashmap.clear();

		}
	}

	public Vector<GraphBean> CountVector() {

		Vector<GraphBean> vector = new Vector<GraphBean>();
		for (int i = 0; i < DataSturct.dataVector.size(); i++) {
			if (DataBean.ChartPage == DataSturct.dataVector.get(i).getPage()) {
				GraphBean graphbean = new GraphBean();
				graphbean.setResponse(DataSturct.dataVector.get(i)
						.getResponse());
				graphbean.setAnswer(DataSturct.dataVector.get(i).getAnswer());
				graphbean.setCount(DataSturct.dataVector.get(i).getCount());
				vector.add(graphbean);
			}
		}
		System.out.println("왜 안넘어가 여기서? ");
		// if(DataBean.data_index!=0)
		// {
		// DataSturct.dataVector.removeAllElements();
		// }
		return vector;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statics_graph);
		DataBean.MaxPage = FileBean.hashmap.size() - 1;
		if (DataBean.ChartPage == DataBean.MaxPage) {
			DataBean.ChartPage = 0;
		} else {
			++DataBean.ChartPage;
		}
		mRenderer.setApplyBackgroundColor(true);
		// mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 0));
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(90);
		// mAdd.setEnabled(true);
		// mX.setEnabled(true);
		// vec = new Vector<GraphBean>();
		SortingHashmap();
		vec = CountVector();
		DataBean.data_index = -1;
		// Iterator iterator2=keylist2.iterator();iterator.hasNext();
		if (!vec.isEmpty()) {
			for (int i = 0; i < vec.size(); i++) {
				// mAdd.setOnClickListener(new View.OnClickListener() {
				// public void onClick(View v) {

				// try {
				x = Double.parseDouble("" + vec.get(i).getCount());
				// } catch (NumberFormatException e) {
				// // TODO
				// mX.requestFocus();
				// return;
				// }
				mSeries.add(vec.get(i).getResponse(), x); // 문항
				SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();

				renderer.setColor(COLORS[(mSeries.getItemCount() - 1)
						% COLORS.length]);
				if (vec.get(i).getAnswer().length() > 15) {
					title = vec.get(i).getAnswer().substring(0, 15)
							.concat("...");

					// title+=vec.get(i).getAnswer().substring(12,vec.get(i).getAnswer().length());
				} else {
					title = vec.get(i).getAnswer();
				}
				mRenderer.setChartTitle("" + DataBean.ChartPage + ". " + title);
				mRenderer.setChartTitleTextSize(30);
				mRenderer.addSeriesRenderer(renderer);
				// mX.setText("");
				// mX.requestFocus();
				if (mChartView != null) {
					mChartView.repaint();
				}

			}
			if (GET_CODE == 0) {
				handle= new Handler();
				if(splash==null)
				splash=new splashhandler();
				handle.postDelayed(splash, 3000);
			}
		}
		// vec=null;
		// mSeries=null;
		// mRenderer=null;
		// mChartView=null;
		// }
		// });
		// mChartView.repaint();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
			handle.removeCallbacks(splash);
			splash.stop();
			Intent intent = new Intent(PieChartBuild.this, AnA_BootMode.class);
			startActivity(intent);
			finish();
			
			Toast.makeText(this, "Back키를 누르셨군요", Toast.LENGTH_SHORT).show();
		} else if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) { // 검색버튼
			Toast.makeText(this, "검색키를 누르셨군요", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	  class splashhandler extends Thread{//implements Runnable {
		  
		
		
		public  void run() {
			
			Intent intent = new Intent(PieChartBuild.this, PieChartBuild.class);

			startActivity(intent);
			finish();
			// startActivityForResult(intent, GET_CODE);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
			mRenderer.setClickEnabled(true);
			mRenderer.setSelectableBuffer(10);
			mChartView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(PieChartBuild.this,
								"No chart element was clicked",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								PieChartBuild.this,
								"Chart element data point index "
										+ seriesSelection.getPointIndex()
										+ " was clicked" + " point value="
										+ seriesSelection.getValue(),
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			mChartView.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					SeriesSelection seriesSelection = mChartView
							.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(PieChartBuild.this,
								"No chart element was long pressed",
								Toast.LENGTH_SHORT);
						return false; // no chart element was long pressed, so
										// let something
						// else handle the event
					} else {
						Toast.makeText(PieChartBuild.this,
								"Chart element data point index "
										+ seriesSelection.getPointIndex()
										+ " was long pressed",
								Toast.LENGTH_SHORT);
						return true; // the element was long pressed - the event
										// has been
						// handled
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		} else {
			mChartView.repaint();
		}
	}
}
