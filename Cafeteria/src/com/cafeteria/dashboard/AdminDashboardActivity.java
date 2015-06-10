package com.cafeteria.dashboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cafeteria.Constant;
import com.cafeteria.R;
import com.cafeteria.Utils;
import com.cafeteria.activity.HomeBaseActivity;
import com.cafeteria.activity.SplashActivity;
import com.cafeteria.admin.ActivityAssignShift;
import com.cafeteria.admin.CafeOperatingScheduleActivity;
import com.cafeteria.admin.CancelOperatingHoursActivity;
import com.cafeteria.admin.CancelShiftActivity;
import com.cafeteria.admin.EnterOperatingHoursActivity;
import com.cafeteria.admin.SpinnerAdapter;
import com.cafeteria.session_manager.UserSession;
import com.cafeteria.student.ActivityEnterAvaialibility;
import com.cafeteria.student.ScheduleActivity;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

public class AdminDashboardActivity extends HomeBaseActivity {

	private TextView tvAssignShift;
	private TextView tvCancel;
	private TextView tvOperatingHours;
	private TextView tvCancelOperatingHours;
	private TextView tvOperatingHoursSchedule;
	
	private ProgressDialog pd;
	protected ArrayList<String> listOfCafe;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sideMenuTitles = getResources().getStringArray(R.array.side_menu_admin);
		setContentView(R.layout.activity_admin_dashboard);
		init();
		//fetchCafeData();
		
	}
	
//	private void assignCafeSchedule() {
//		
//		for (int i = 0; i < listOfCafe.size(); i++) {
//			for (int j = 0; j < 7; j++) {
//				ParseObject parseObject = new ParseObject(Constant.CAFE);
//				parseObject.put("", );
//			}
//		}
//	}

	private void fetchCafeData() {
		pd = Utils.showProgressDialog(this);
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constant.CAFE);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				pd.cancel();
				if(e==null){
					listOfCafe = new ArrayList<String>();
					for (ParseObject parseObject : objects) {
							listOfCafe.add(parseObject.getString("cafe_name"));
							HashSet hs = new HashSet();
							listOfCafe.addAll(hs);
							
					}
				}
			}
		});
	}
	private void init() {
		tvAssignShift = (TextView) findViewById(R.id.tvAssignShift);
		tvCancel = (TextView) findViewById(R.id.tvCancel);
		tvCancel.setOnClickListener(this);
		tvAssignShift.setOnClickListener(this);
		
		tvOperatingHours = (TextView) findViewById(R.id.tvOperatingHours);
		tvOperatingHours.setOnClickListener(this);
		tvCancelOperatingHours=(TextView) findViewById(R.id.tvCancelOperatingHours);
		tvCancelOperatingHours.setOnClickListener(this);
		
		tvOperatingHoursSchedule=(TextView) findViewById(R.id.tvOperatingHoursSchedule);
		tvOperatingHoursSchedule.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tvAssignShift:
			startActivity(new Intent(this, ActivityAssignShift.class));
			break;

		case R.id.tvCancel:
			startActivity(new Intent(this, CancelShiftActivity.class));
			break;
		case R.id.tvOperatingHours:
			startActivity(new Intent(this, EnterOperatingHoursActivity.class));
			break;
		case R.id.tvCancelOperatingHours:
			startActivity(new Intent(this,CancelOperatingHoursActivity.class));
			break;
			
		case R.id.tvOperatingHoursSchedule:
			startActivity(new Intent(this,CafeOperatingScheduleActivity.class));
			break;
		}
	}
	
	
	public class Cafe {
		String cafe_name ="";
		Date schedule_date ;
		Date start_time;
		Date end_time;
		boolean is_holiday;
		boolean is_valid;
	}
	
}
