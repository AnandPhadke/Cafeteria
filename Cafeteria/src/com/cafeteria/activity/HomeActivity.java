package com.cafeteria.activity;

import com.cafeteria.R;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] sideMenuTitles;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.left_drawer);
	        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	        getSupportActionBar().setHomeButtonEnabled(true);
	        sideMenuTitles = getResources().getStringArray(R.array.side_menu);

	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.drawer_list_item, sideMenuTitles));
	        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
			        mDrawerLayout.closeDrawer(mDrawerList);
				}
			});
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  
	                mDrawerLayout,         
	                R.drawable.logo,  
	                R.string.drawer_open, 
	                R.string.drawer_close 
	                ) {
	            public void onDrawerClosed(View view) {
	                getSupportActionBar().setTitle("Cafeteria");
	            }

	            public void onDrawerOpened(View drawerView) {
	            	getSupportActionBar().setTitle("Cafeteria");
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        return super.onOptionsItemSelected(item);
	}
}
