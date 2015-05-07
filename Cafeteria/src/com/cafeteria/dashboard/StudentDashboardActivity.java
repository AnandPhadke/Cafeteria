package com.cafeteria.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cafeteria.R;
import com.cafeteria.activity.HomeBaseActivity;
import com.cafeteria.student.ScheduleActivity;


public class StudentDashboardActivity extends HomeBaseActivity {

	
	private TextView tvSchedule;

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sideMenuTitles = getResources().getStringArray(R.array.side_menu_user);
		setContentView(R.layout.activity_student_dashboard);
		tvSchedule = (TextView)findViewById(R.id.tvSchedule);
		tvSchedule.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tvSchedule:
			startActivity(new Intent(this,ScheduleActivity.class));
			break;

		default:
			break;
		}
	}
}
