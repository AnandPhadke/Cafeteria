package com.cafeteria.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.cafeteria.R;
import com.cafeteria.activity.HomeBaseActivity;

public class AdminDashboardActivity extends HomeBaseActivity {

	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sideMenuTitles = getResources().getStringArray(R.array.side_menu_admin);
		setContentView(R.layout.activity_admin_dashboard);
	}
}
