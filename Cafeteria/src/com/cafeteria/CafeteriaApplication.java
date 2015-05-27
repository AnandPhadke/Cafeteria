package com.cafeteria;

import com.cafeteria.activity.SplashActivity;
import com.cafeteria.dashboard.AdminDashboardActivity;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.app.Application;
import android.util.Log;

public class CafeteriaApplication extends Application {
	 private static final String YOUR_APPLICATION_ID = "61Zw6zggyxZWauUrSx18xrilUl7KJ3pZ3SPDa1PV";
		private static final String YOUR_CLIENT_KEY = "lBT9l0y5U654QxGUM2m8iK1X7vq50ys6LqjvSh2g";
	@Override
	public void onCreate() {
		super.onCreate();
		 // Add your initialization code here
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
 
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
 
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
 
        ParseACL.setDefaultACL(defaultACL, true);
        
        PushService.setDefaultPushCallback(this, AdminDashboardActivity.class);
        
       
	}
}
