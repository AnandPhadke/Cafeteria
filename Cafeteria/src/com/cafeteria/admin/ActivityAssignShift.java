package com.cafeteria.admin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cafeteria.Constant;
import com.cafeteria.R;
import com.cafeteria.Utils;
import com.cafeteria.objects.UserObject;
import com.cafeteria.session_manager.UserSession;
import com.cafeteria.student.ActivityEnterAvaialibility;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ActivityAssignShift extends Activity {
	
	private Spinner spinnerUserList;
	private Spinner spinnerCafe;
	private ProgressDialog pd;
	private LinearLayout llAvailable;
	private TextView tvNotAvailable;
	private TextView tvDate;
	private TextView tvFrom;
	private TextView tvTo;
	protected ArrayList<UserObject> array;
	protected ArrayList<String> listOfCafe;
	protected String assignedCafe="";
	private Button btnAssign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Assign shift");
		setContentView(R.layout.activity_assign_shift);
		init();
		fetchAvailableUser();
		fetchCafeData();
	}

	private void init() {
		spinnerUserList = (Spinner)findViewById(R.id.spinnerUserList);
		spinnerCafe= (Spinner)findViewById(R.id.spinnerCafe);
		spinnerCafe.setPrompt("Select cafe");
		spinnerUserList.setPrompt("Select user");
		llAvailable = (LinearLayout)findViewById(R.id.llAvailable);
        tvNotAvailable= (TextView)findViewById(R.id.tvNotAvailable);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvFrom = (TextView)findViewById(R.id.tvFrom);
		tvTo = (TextView)findViewById(R.id.tvTo);
		//spinnerUserList.set
		
		spinnerUserList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
					UserObject user = array.get(pos);
					if (user==null){
						return;
					}
					if(user.a_date==null ||user.a_date.equals("")){
						tvNotAvailable.setVisibility(View.VISIBLE);
						llAvailable.setVisibility(View.GONE);
					}else{
						tvNotAvailable.setVisibility(View.GONE);
						llAvailable.setVisibility(View.VISIBLE);
						tvDate.setText(user.a_date);
						tvFrom.setText(user.a_from);
						tvTo.setText(user.a_to);
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		spinnerCafe.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				assignedCafe = listOfCafe.get(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		btnAssign = (Button)findViewById(R.id.btnAssign);
		btnAssign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				assignShift();
			}
		});
	}

	private void assignShift() {
		pd = Utils.showProgressDialog(this);
		ParseUser user = null;
		try {
			user = ParseUser.logIn(UserSession.getInstance(this).getUsername(),UserSession.getInstance(this).getPassword());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.put("assign_cafe", assignedCafe);
		user.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				pd.cancel();
				if(e ==null){
					Toast.makeText(ActivityAssignShift.this, "Successfully assign cafe!!!", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(ActivityAssignShift.this, "Error in assign shift", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void fetchAvailableUser() {
		pd = Utils.showProgressDialog(this);
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				pd.cancel();
				if(e==null){
					 array = new ArrayList<UserObject>();
					 array.clear();
					for (ParseUser parseUser : objects) {
						
						
						Log.v("assign shift", "username   :   "+parseUser.getUsername());
						Log.v("assign shift", "date   :   "+parseUser.getString("a_date"));
						if(!parseUser.getBoolean("is_admin")){
							UserObject userObject = new UserObject();
							userObject.a_date = parseUser.getString("a_date");
							userObject.a_from = parseUser.getString("a_from");
							userObject.a_to = parseUser.getString("a_to");
							userObject.userName =parseUser.getUsername();
							array.add(userObject);
						}
					}
					SpinnerAdapter<UserObject> adapter = new SpinnerAdapter<UserObject>(ActivityAssignShift.this,android.R.layout.simple_list_item_1,array);
					spinnerUserList.setAdapter(adapter);
					
					

					
				}else{
					Toast.makeText(ActivityAssignShift.this, "error in retrieving users", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	
	
	private void fetchCafeData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constant.CAFE);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				pd.cancel();
				if(e==null){
					listOfCafe = new ArrayList<String>();
					for (ParseObject parseObject : objects) {
							listOfCafe.add(parseObject.getString("cafe_name"));
					}
					SpinnerAdapter<String> adapter = new SpinnerAdapter<String>(ActivityAssignShift.this,android.R.layout.simple_list_item_1,listOfCafe);
					spinnerCafe.setAdapter(adapter);
				}else{
					Toast.makeText(ActivityAssignShift.this, "error in retrieving cafe", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	

}
