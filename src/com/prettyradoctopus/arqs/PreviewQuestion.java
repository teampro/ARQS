package com.prettyradoctopus.arqs;

import com.parse.ParseObject;
import com.prettyradoctopus.arqs.R;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewQuestion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview_question);
		
		Bundle extras = getIntent().getExtras();
		String subject = extras.getString("subject");
		String question = extras.getString("question");
		
		TextView tvSubject = (TextView) findViewById(R.id.tvPreviewSubject);
		tvSubject.setText(subject);
		
		TextView tvQuestion = (TextView) findViewById(R.id.tvPreviewQuestion);
		tvQuestion.setText(question);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview_question, menu);
		return true;
	}
	
	public void onSubmit(View v){
	String u = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		
		String subject = getSubject(v);
		String question = getQuestion(v);
		
		
		 if(!"".equals(subject) && subject != null && !"".equals(question) && question != null) {
			 Toast.makeText(this, subject, Toast.LENGTH_SHORT).show();
			 Toast.makeText(this, question, Toast.LENGTH_SHORT).show();
			 
			 ParseObject topic = new ParseObject("questions");
			 topic.put("tittle", subject);
			 topic.put("body_content", question);
			 topic.put("username", u);
			 topic.saveInBackground();
			 
			 Toast.makeText(this, "Saved! Sending you back to the list of questions", Toast.LENGTH_SHORT).show();
			 Intent i = new Intent(this, ListActivity.class);
		   	 startActivity(i);
		 }
		 else if(!"".equals(subject) && subject != null) {
			 Toast.makeText(this, "You forgot to put something in the body!", Toast.LENGTH_SHORT).show();
		 }
		 else {
			 Toast.makeText(this, "You forgot to put something in the subject!", Toast.LENGTH_SHORT).show();
		 }	
	}
	
	public void onEdit(View v){
		String subject = getSubject(v);
		String question = getQuestion(v);
		
		Intent i = new Intent(this, SubmitQuestionActivity.class);
	   	i.putExtra("subject", subject);
	   	i.putExtra("question", question);
		startActivity(i);
		
	}
	
	public void onCancel(View v){
		 Intent i = new Intent(this, ListActivity.class);
	   	 startActivity(i);
	}
	
	public String getSubject(View v){
		TextView tempsubject = (TextView) findViewById(R.id.tvPreviewSubject);
		String subject = tempsubject.getText().toString();
		return subject;
	}
	
	public String getQuestion(View v){
		TextView tempbody = (TextView) findViewById(R.id.tvPreviewQuestion);
		String body = tempbody.getText().toString();
		return body;
	}

}
