package com.cafeteria.activity;

import com.cafeteria.R;
import com.cafeteria.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    private static final long DURATION = 2000;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, DURATION	);
        
    }


  
}
