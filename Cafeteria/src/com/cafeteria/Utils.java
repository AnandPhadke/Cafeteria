package com.cafeteria;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {

	
	
	public static ProgressDialog showProgressDialog(Context c){
		return ProgressDialog.show(c, "Cafetria",
			    "Loading...", false,false);
	}
	
}
