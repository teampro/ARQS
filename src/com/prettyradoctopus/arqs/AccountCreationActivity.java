package com.prettyradoctopus.arqs;


import java.util.UUID;


import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.prettyradoctopus.arqs.R;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;




public class AccountCreationActivity extends Activity {

	EditText etPassphrase;
	public String go_no_go;
	public UUID uuid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_creation);
		
		
		etPassphrase = (EditText) findViewById(R.id.etPassphrase);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_creation, menu);
		return true;
	}
	
	public void onSignUp(View v){
		String u = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
	//	String u = getDeviceUuid().toString();
		
		// String u = "02afd98d089897e";
		
		 String passphrase = etPassphrase.getText().toString();
		 Log.d("DEBUG", u);
		 if(!"".equals(passphrase) && passphrase != null) {
			 Toast.makeText(this, u, Toast.LENGTH_LONG).show();
			 
			 ParseUser user = new ParseUser();
			 user.setUsername(u);
			 user.setPassword(passphrase);
		//	 user.setEmail("email@example.com");
			 
			 
			 
			 user.signUpInBackground(new SignUpCallback() {
				  public void done(ParseException e) {
				    if (e == null) {
				      // Hooray! Let them use the app now.
				    //	 String success = "success";
				    	myUserSignedUpSuccessfully();
				    	
				    } else {
				      // Sign up didn't succeed. Look at the ParseException
				      // to figure out what went wrong
				    //	Log.d("DEBUG", e.toString());
				    	String error = e.toString();
				    	myUserSignUpDidNotSucceed(error);
				    	
				    }
				  }
				});
			 
		 }
		 else {
			 Toast.makeText(this, "Nothing here", Toast.LENGTH_LONG).show();
			 

			 
				
		 }
	}

	protected void myUserSignUpDidNotSucceed(String error) {
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		
	}

	protected void myUserSignedUpSuccessfully() {
		Toast.makeText(this, "Hurray, you have signed up successfully", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, LoginActivity.class);
   	 	startActivity(i);
		
	}
	
	  
	
	
	

}
