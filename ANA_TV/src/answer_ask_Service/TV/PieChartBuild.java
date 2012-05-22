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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PieChartBuild extends Activity {

	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE,
			Color.MAGENTA, Color.CYAN, Color.RED ,Color.YELLOW,Color.rgb(99,184,255)};

	private CategorySeries mSeries = new CategorySeries("");

	private DefaultRenderer mRenderer = new DefaultRenderer();
	
	private String mDateFormat;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statics_graph);
		mRenderer.setApplyBackgroundColor(true);
		//mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 0));
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15,0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(90);
		
		// mAdd.setEnabled(true);
		// mX.setEnabled(true);
		int i=10;
		while (i != 0) {
			// mAdd.setOnClickListener(new View.OnClickListener() {
			// public void onClick(View v) {
			double x = 0;
			// try {
			x = Double.parseDouble(""+i );
			// } catch (NumberFormatException e) {
			// // TODO
			// mX.requestFocus();
			// return;
			// }
			mSeries.add("Series " + (mSeries.getItemCount() + 1), x); // 문항
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();

			renderer.setColor(COLORS[(mSeries.getItemCount() - 1)
					% COLORS.length]);
			mRenderer.setChartTitle("한성대남녀설문조사");
			mRenderer.setChartTitleTextSize(40);
			mRenderer.addSeriesRenderer(renderer);
			// mX.setText("");
			// mX.requestFocus();
			if (mChartView != null) {
				mChartView.repaint();
			}
			--i;
		}
		// }
		// });
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
