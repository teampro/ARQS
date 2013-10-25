package com.prettyradoctopus.arqs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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
		Intent i;
		
		// Check with parse to see if this user already exists.
		// If yes, then go to Login activy else go to AccountCreation activity
		// Go back to Menu for now
		i = new Intent(this, MenuActivity.class);
		startActivity(i);
	}

}
