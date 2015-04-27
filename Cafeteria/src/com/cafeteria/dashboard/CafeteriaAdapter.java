package com.cafeteria.dashboard;

import java.util.ArrayList;

import com.cafeteria.R;
import com.cafeteria.objects.CafeteriaSchedule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CafeteriaAdapter extends BaseAdapter {

	
	private ArrayList<CafeteriaSchedule> arrayListSchedule;
	private Context mContext;

	public CafeteriaAdapter(Context context ,ArrayList<CafeteriaSchedule> arr ) {
		mContext =context;
		arrayListSchedule= arr;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.list_item_schedule, null);
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		TextView tvSubtitle1 = (TextView)view.findViewById(R.id.tvSubtitle1);
		tvTitle.setText(arrayListSchedule.get(position).cafe_name);
		tvSubtitle1.setText("start time : "+arrayListSchedule.get(position).start_time +"  end time : "+arrayListSchedule.get(position).end_time);
		return view;
	}
	@Override
	public int getCount() {
		return arrayListSchedule.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

}