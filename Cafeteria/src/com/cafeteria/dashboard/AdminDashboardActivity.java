package com.cafeteria.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cafeteria.R;
import com.cafeteria.activity.HomeBaseActivity;
import com.cafeteria.admin.ActivityAssignShift;
import com.cafeteria.student.ActivityEnterAvaialibility;
import com.cafeteria.student.ScheduleActivity;

public class AdminDashboardActivity extends HomeBaseActivity {

	private TextView tvAssignShift;
	private TextView tvOperatingHours;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sideMenuTitles = getResources().getStringArray(R.array.side_menu_admin);
		setContentView(R.layout.activity_admin_dashboard);
		init();
	}
	
	private void init() {
		tvAssignShift = (TextView) findViewById(R.id.tvAssignShift);
		tvOperatingHours = (TextView) findViewById(R.id.tvOperatingHours);
		tvOperatingHours.setOnClickListener(this);
		tvAssignShift.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tvAssignShift:
			startActivity(new Intent(this, ActivityAssignShift.class));
			break;

		case R.id.tvOperatingHours:
			//startActivity(new Intent(this, ActivityEnterAvaialibility.class));
			break;
		}
	}
}
