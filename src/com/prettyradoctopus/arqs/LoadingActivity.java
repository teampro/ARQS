package com.prettyradoctopus.arqs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LoadingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
		return true;
	}
	
	public void onEnter(View v) {
		String u = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);

		// Check with parse to see if this user already exists.
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("username", u);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (object == null) {
				// If no, then go to AccountCreation activity
				Toast.makeText(LoadingActivity.this, "Hello Stranger, let's create an account for you", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(LoadingActivity.this, AccountCreationActivity.class);
				startActivity(i);
		    } else {
				// If yes, then go to Login activy 
				Toast.makeText(LoadingActivity.this, "Welcome back to ARQS", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(LoadingActivity.this, LoginActivity.class);
				startActivity(i);
		    }
		  }
		});
		
		// If yes, then go to Login activy else go to AccountCreation activity

	}

}
