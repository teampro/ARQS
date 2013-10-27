package com.prettyradoctopus.arqs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.prettyradoctopus.arqs.R;

public class SubmitQuestionActivity extends Activity {
	String subject = null;
	String question = null;
	String body = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_question);

		Bundle extras = getIntent().getExtras();
		subject = extras.getString("subject");
		question = extras.getString("question");

		if((subject != "") && (question != "")){
			EditText etSubject = (EditText) findViewById(R.id.etSubject);
			etSubject.setText(subject);
			EditText etQuestion = (EditText) findViewById(R.id.etQuestion);
			etQuestion.setText(question);	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_question, menu);
		return true;
	}

	public void onPreview(View v){
		String subject = getSubject(v);
		String question = getQuestion(v);
		
		Intent i = new Intent(this, PreviewQuestionActivity.class);
	   	i.putExtra("subject", subject);
	   	i.putExtra("question", question);
		startActivity(i);
		
	}
	
	public void onSubmit(View v){
		String u = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		
		String subject = getSubject(v);
		String question = getQuestion(v);
		
		
		 if(!"".equals(subject) && subject != null && !"".equals(question) && question != null) {
			 //Toast.makeText(this, subject, Toast.LENGTH_SHORT).show();
			 //Toast.makeText(this, question, Toast.LENGTH_SHORT).show();
			 
			 ParseObject topic = new ParseObject("questions"); // XXX change to Questions
			 topic.put("tittle", subject); // XXX change to "title"
			 topic.put("body_content", question); // XXX change to bodyContent
			 topic.put("username", u); 
			 topic.saveInBackground();
			 
			 //Toast.makeText(this, "Saved! Sending you back to the list of questions", Toast.LENGTH_SHORT).show();
			 Intent i = new Intent(this, QuestionsListActivity.class);
		   	 startActivity(i);
		 }
		 else if(!"".equals(subject) && subject != null) {
			 Toast.makeText(this, "You forgot to put something in the body!", 
					 Toast.LENGTH_SHORT).show();
		 }
		 else {
			 Toast.makeText(this, "You forgot to put something in the subject!", 
					 Toast.LENGTH_SHORT).show();
		 }
	}
	
	public void onCancel(View v){
		 Intent i = new Intent(this, QuestionsListActivity.class);
	   	 startActivity(i);
	}
	
	public String getSubject(View v){
		EditText tempsubject = (EditText) findViewById(R.id.etSubject);
		String subject = tempsubject.getText().toString();
		return subject;
	}
	
	public String getQuestion(View v){
		EditText tempbody = (EditText) findViewById(R.id.etQuestion);
		String body = tempbody.getText().toString();
		return body;
	}
}
