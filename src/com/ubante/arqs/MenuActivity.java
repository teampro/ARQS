package com.ubante.arqs;

import java.util.UUID;

import com.parse.Parse;
import com.parse.ParseAnalytics;

import android.telephony.TelephonyManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		 Parse.initialize(this, "WBVUf4oOq6cftucsL3mNG0qONvDCeHoNXPVhCkL4", "nHkSpjqlb75oOpY3rT6cZIXzpeChYguSgnMBva33"); 
			ParseAnalytics.trackAppOpened(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	public void onLoading(View v) {
		Toast.makeText(this, "Switching to Loading", Toast.LENGTH_SHORT).show();
		Intent loadingIntent = new Intent(this, LoadingActivity.class);
		startActivity(loadingIntent);
	}

	public void onAccountCreation(View v) {
		Toast.makeText(this, "Switching to Account Creation", Toast.LENGTH_SHORT).show();
		Intent acIntent = new Intent(this, AccountCreationActivity.class);
		startActivity(acIntent);
	}

	public void onLogin(View v) {
		Toast.makeText(this, "Switching to Login", Toast.LENGTH_SHORT).show();
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
	}

	public void onList(View v) {
		Toast.makeText(this, "Switching to List", Toast.LENGTH_SHORT).show();
		Intent listIntent = new Intent(this, ListActivity.class);
		startActivity(listIntent);
	}

	public void onSubmit(View v) {
		Toast.makeText(this, "Switching to Submit", Toast.LENGTH_SHORT).show();
		Intent submitIntent = new Intent(this, SubmitQuestionActivity.class);
		startActivity(submitIntent);
	}
}
