package com.cafeteria.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cafeteria.R;
import com.cafeteria.R.layout;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    private static final long DURATION = 2000;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_splash);
        
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                                   "com.cafeteria",
                                   PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Splash  ","key hash   :   " +Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
        
        
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
