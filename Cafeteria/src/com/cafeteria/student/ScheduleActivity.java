package com.cafeteria.student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cafeteria.Constant;
import com.cafeteria.R;
import com.cafeteria.dashboard.CafeteriaAdapter;
import com.cafeteria.objects.CafeteriaSchedule;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ScheduleActivity extends Activity implements OnClickListener, OnPageChangeListener {

	private ImageView ivPrev;
	private ImageView ivNext;
	private TextView tvMonth;
	private Calendar calendar;
	private ViewPager pager;
	private ArrayList<String> dates;
	private int monthMaxDays;
	private int monthMinDays;
	private int currentMonth;
	private int sheduleMonth;
	private int currentYear;
	private int sheduleYear;
	private int currentDay;
	private int scheduleDay;
	private boolean onMonthChange;
	private ArrayList<CafeteriaSchedule> data;
	private ListView lstData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		init();
		data = new ArrayList<CafeteriaSchedule>();
		downloadData();
	}

	private void downloadData() {
		ParseQuery<ParseObject> query =ParseQuery.getQuery(Constant.CAFE_SCHEDULE);
		
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> results, ParseException e) {
				if(e==null){
					
					for (ParseObject x : results) {
						CafeteriaSchedule cafe = new CafeteriaSchedule();
						cafe.cafe_name = x.getString("cafe_name");
						cafe.date=x.getDate("schedule_date");
						Log.v(getClass().getName(), "date   "+x.getDate("schedule_date").toString());
						cafe.day=x.getInt("day");
						cafe.start_time = x.getString("start_time");
						cafe.end_time = x.getString("end_time");
						data.add(cafe);
					}
					
					Log.v(getClass().getName(), "size   "+data.size());
					ArrayList<CafeteriaSchedule> currentMonthDayData = getCurrentMonthData(data,currentDay);
					CafeteriaAdapter adapter = new CafeteriaAdapter(ScheduleActivity.this, currentMonthDayData);
					lstData.setAdapter(adapter);
				}
			}
		});
	}

	protected ArrayList<CafeteriaSchedule> getCurrentMonthData(ArrayList<CafeteriaSchedule> allData,int cDay) {
		ArrayList<CafeteriaSchedule> currentMonthData = new ArrayList<CafeteriaSchedule>();
		for (int i = 0; i < allData.size(); i++) {
			if(currentMonth == allData.get(i).date.getMonth()){
				Log.v(getClass().getName(), "day   "+cDay +"  date  "+allData.get(i).date.getDay());
				if(cDay==allData.get(i).date.getDay())
					currentMonthData.add(allData.get(i));
			}
		}
		return currentMonthData;
	}

	private void init() {
		ivPrev = (ImageView) findViewById(R.id.ivPrev);
		ivNext = (ImageView) findViewById(R.id.ivNext);
		tvMonth = (TextView) findViewById(R.id.tvMonth);
		ivNext.setOnClickListener(this);
		ivPrev.setOnClickListener(this);
		dates = new ArrayList<String>();
		calendar = Calendar.getInstance(Locale.getDefault());
		pager = (ViewPager) findViewById(R.id.pager);
		showCurrentMonthDates();
		pager.setOffscreenPageLimit(dates.size());
		pager.setOnPageChangeListener(this);
		lstData = (ListView) findViewById(R.id.lstData);

	}

	public void showCurrentMonthDates() {
		calendar = Calendar.getInstance();
		monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthMinDays = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		String monthName = new SimpleDateFormat("MMMM", Locale.getDefault())
				.format(calendar.getTime());
		currentMonth = calendar.get(Calendar.MONTH);
		sheduleMonth = currentMonth;
		sheduleMonth++;
		currentYear = calendar.get(Calendar.YEAR);
		sheduleYear = currentYear;
		// Log.v(TAG, "month   " + sheduleMonth);
		// Log.v(TAG, "year   " + sheduleYear);
		tvMonth.setText(monthName + " " + sheduleYear);

		dates.clear();
		addInitialsDate();
		for (int i = monthMinDays; i <= monthMaxDays; i++) {
			if (i < 10)
				dates.add("0" + i);
			else
				dates.add("" + i);
		}
		addPostDate();
		pager.setAdapter(new MyPageAdapter());
		pager.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1, true);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		scheduleDay = currentDay;
	}

	private void showNextPreviousMonthDates(boolean next) {
		if (next) {
			calendar.add(Calendar.MONTH, 1);
			if (sheduleMonth == 12) {
				sheduleYear++;
				sheduleMonth = 1;
			} else {
				sheduleMonth++;
			}
		} else {
			calendar.add(Calendar.MONTH, -1);
			if (sheduleMonth == 1) {
				sheduleYear--;
				sheduleMonth = 12;
			} else {
				sheduleMonth--;
			}
		}
		
		onMonthChange = true;
		
		monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthMinDays = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		String monthName = new SimpleDateFormat("MMMM", Locale.getDefault())
				.format(calendar.getTime());
		tvMonth.setText(monthName + " " + sheduleYear);
		dates.clear();
		addInitialsDate();
		for (int i = monthMinDays; i <= monthMaxDays; i++) {
			if (i < 10)
				dates.add("0" + i);
			else
				dates.add("" + i);
		}
		addPostDate();
		pager.setAdapter(new MyPageAdapter());

		int selectedIndex = 0;
		// check month is current or other month
		if (calendar.get(Calendar.MONTH) < currentMonth) {
			selectedIndex = dates.size();
		} else if (calendar.get(Calendar.MONTH) > currentMonth) {
			selectedIndex = 0;
		} else {
			selectedIndex = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		}
		scheduleDay = selectedIndex + 1;
		// ((OnPageChangeListener)(this)).onPageSelected(selectedIndex);
		pager.setCurrentItem(selectedIndex, true);

	}

	private class MyPageAdapter extends PagerAdapter {

		public MyPageAdapter() {
		}

		@Override
		public int getCount() {
			return dates.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// Log.d("Tag", "instantiateItem  "+position);
			TextView view = new TextView(ScheduleActivity.this);
			view.setText(dates.get(position));
			view.setTextSize(20);
			view.setGravity(Gravity.CENTER);
			if (!TextUtils.isEmpty(dates.get(position))) {

				if (calendar.get(Calendar.MONTH) < currentMonth)
					view.setTextColor(Color.parseColor("#8B8A85")); // previous
																	// month
				else if (calendar.get(Calendar.MONTH) > currentMonth)
					view.setTextColor(Color.BLACK); // next month
				else {
					if (Integer.parseInt(dates.get(position)) < Calendar
							.getInstance().get(Calendar.DAY_OF_MONTH))
						view.setTextColor(Color.parseColor("#8B8A85"));// previous
																		// days
					else if (Integer.parseInt(dates.get(position)) == Calendar
							.getInstance().get(Calendar.DAY_OF_MONTH)) {
						// current date
						view.setTextColor(Color.BLACK);
						// view.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						// 0,R.drawable.no_hz_strip);
					} else
						view.setTextColor(Color.BLACK); // next days
				}
			}

			view.setTag(position + "");
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if (TextUtils.isEmpty(((TextView) v).getText().toString())) {
						return;
					}
					pager.getParent().requestDisallowInterceptTouchEvent(true);
					int i = Integer.parseInt((String) v.getTag());
					Log.d("Tag", "onClick  " + i);
					pager.setCurrentItem(i - 3);
					pager.getParent().requestDisallowInterceptTouchEvent(false);
				}
			});
			((ViewPager) container).addView(view);
			return view;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public float getPageWidth(int position) {
			return (0.145f);
		}

	}

	void addInitialsDate() {
		for (int i = 0; i < 3; i++)
			dates.add(0, "");
	}

	void addPostDate() {
		for (int i = 0; i < 3; i++)
			dates.add("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivNext:
			showNextPreviousMonthDates(true);
			break;
		case R.id.ivPrev:
			showNextPreviousMonthDates(false);
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		
	}

}
