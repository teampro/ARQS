package com.prettyradoctopus.arqs;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.prettyradoctopus.arqs.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	EditText etLoginPassphrase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etLoginPassphrase = (EditText) findViewById(R.id.etLoginPassphrase);
		Parse.initialize(this, "sOb0qqC2Cetj5Aiw4RAQlLvF4lQEz4tJTobQBG7D", 
				"U6jMtz0vkTqtICvZhvrfKAsKadx56XRi0UfO3yii"); 
		ParseAnalytics.trackAppOpened(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	public void onLogin(View v){
		String u = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		getPreferences(MODE_PRIVATE).edit().putString("uuid",u).commit();
		String loginpassphrase = etLoginPassphrase.getText().toString();
		// Log.d("DEBUG", u);
		if(!"".equals(loginpassphrase) && loginpassphrase != null) {
			Toast.makeText(this, u, Toast.LENGTH_LONG).show();
			ParseUser.logInInBackground(u, loginpassphrase, new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						// Hooray! The user is logged in.
						myUserLogInSuccessfully();
					} else {
						// Signup failed. Look at the ParseException to see what happened.
						String error = e.toString();
						myUserLogInDidNotSucceed(error);
					}
				}
			});
		}
		else {
			Toast.makeText(this, "Empty passphrases are not allowed", 
					Toast.LENGTH_LONG).show();
		}
	}

	protected void myUserLogInDidNotSucceed(String error) {
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		
	}

	protected void myUserLogInSuccessfully() {
		Toast.makeText(this, "Hurrah, you have logged in successfully", 
				Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, QuestionsListActivity.class);
   	 	startActivity(i);
	}
	
	public void onAccountCreation(View v) {
		Toast.makeText(this, "Switching to Account Creation", Toast.LENGTH_SHORT).show();
		Intent acIntent = new Intent(this, AccountCreationActivity.class);
		startActivity(acIntent);
	}
}
