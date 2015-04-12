package com.cafeteria.activity;

import com.cafeteria.R;
import com.cafeteria.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity implements OnClickListener {

	
	private Button btnSignup;
	private Button btnLogin;
	private String usernametxt="";
	private String passwordtxt="";
	private EditText etUsername;
	private EditText etPassword;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setTitle("Login");
		btnSignup = (Button)findViewById(R.id.btnSignup);
		btnSignup.setOnClickListener(this);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		etUsername = (EditText)findViewById(R.id.etUsername);
		etPassword = (EditText)findViewById(R.id.etPassword);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btnSignup:
			intent = new Intent(this,SignupActivity.class);
			startActivity(intent);
			break;
			
		case R.id.btnLogin:
			if(TextUtils.isEmpty(usernametxt) || TextUtils.isEmpty(passwordtxt)){
				Toast.makeText(this, "Please insert all fields", Toast.LENGTH_SHORT).show();
				return;
			}
			
			login();
			break;

		default:
			break;
		}
	}

	private void login() {
		usernametxt = etUsername.getText().toString();
		passwordtxt = etPassword.getText().toString();
		progressDialog = Utils.showProgressDialog(LoginActivity.this);
		// Send data to Parse.com for verification
		ParseUser.logInInBackground(usernametxt, passwordtxt,
				new LogInCallback() {
					public void done(ParseUser user, ParseException e) {
						progressDialog.dismiss();
						if (user != null) {
							Log.v(getClass().getName(),user.get("employee_name").toString());
							// If user exist and authenticated, send user to Welcome.class
//							Intent intent = new Intent(
//									LoginSignupActivity.this,
//									Welcome.class);
//							startActivity(intent);
							
							Toast.makeText(getApplicationContext(),
									"Successfully Logged in",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(
									getApplicationContext(),
									"No such user exist, please signup",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}
}
