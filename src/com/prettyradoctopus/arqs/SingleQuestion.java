package com.prettyradoctopus.arqs;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SingleQuestion extends Activity {
	
	String qid, tittle, body, voteup, votedown, username;
	//String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_question);
		
		Bundle extras = getIntent().getExtras();
		qid = extras.getString("qid");
		tittle = extras.getString("tittle");
		body = extras.getString("body");
		username = extras.getString("username");

		
		TextView tvTittle = (TextView) findViewById(R.id.tvTitle);
		String formattedTitle = "<b>" + tittle + "</b><p></p>";
		tvTittle.setText(Html.fromHtml(formattedTitle));
		
		TextView tvBody = (TextView) findViewById(R.id.tvBody);
		String formattedBody = body + "<p></p>";
		tvBody.setText(Html.fromHtml(formattedBody));
		
		
		final TextView countUp = (TextView) findViewById(R.id.tvCountUp);
		//int vote_up_count = QuestionsListActivity.query_up_count(question_id);
		final String voteup = QuestionsListActivity.get_up_count(qid);
		//String formattedcountUp =  vote_up_count;
		countUp.setText(voteup);
		
		final TextView countDown = (TextView) findViewById(R.id.tvCountDown);
		//int vote_down_count = QuestionsListActivity.query_down_count(question_id);
		final String votedown = QuestionsListActivity.get_down_count(qid);
		//String formattedcountUp =  vote_up_count;
		countDown.setText(votedown);
		
		final Button btUp = (Button) findViewById(R.id.btUp);
		
		final Button btDown = (Button) findViewById(R.id.btDown);
		
		final String up_result = QuestionsListActivity.checkVoteUp(qid);
		final String down_result = QuestionsListActivity.checkVoteDowned(qid);
		if (up_result == "TRUE"){
			btUp.setBackgroundColor(Color.GREEN);
			
			
		} else {
			btUp.setBackgroundColor(0xCCCCCCCC);
		}
		
		

		
		if (down_result == "TRUE"){
			btDown.setBackgroundColor(Color.RED);
			
			
		} else {
			btDown.setBackgroundColor(0xCCCCCCCC);
		}
		
		
		btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		
             Log.d("DEBUG", "Up Vote for qid " + qid);
           
             Boolean up = true;
             Boolean down = false;
          ///   QuestionsListActivity.getVoteQuery(question_id, username, up, down);
             
            String up_result = QuestionsListActivity.checkVoteUp(qid);
     		String down_result = QuestionsListActivity.checkVoteDowned(qid);
     		if (up_result == "TRUE" | down_result == "TRUE"){
     			
     		} else {
     			QuestionsListActivity.getVoteQuery(qid, username, up, down);
     			btUp.setBackgroundColor(Color.GREEN);
     			int numUp = Integer.parseInt(voteup);
          	  int numTotalUp = numUp + 1;
          	  String strUp = String.valueOf(numTotalUp);
          	  countUp.setText(strUp);
     		}
     		
     			
     			
     	
             
             
         
            }
        });
		
		btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Down Vote for qid " + qid);
             
                Boolean up = false;
                Boolean down = true;
                
                
                String up_result = QuestionsListActivity.checkVoteUp(qid);
         		String down_result = QuestionsListActivity.checkVoteDowned(qid);
         		if (up_result == "TRUE" | down_result == "TRUE"){
         			
         		} else {
         			QuestionsListActivity.getVoteQuery(qid, username, up, down);
         			btDown.setBackgroundColor(Color.RED);
         			
         		int numDown = Integer.parseInt(votedown);
              	  int numTotalDown = numDown + 1;
              	  String strDown = String.valueOf(numTotalDown);
              	  countDown.setText(strDown);
         			
         		}
             //   String vote_result =  QuestionsListActivity.getVoteQuery(question_id, username, up, down);
             //   Log.d("DEBUG", vote_result);
            
            }
        });
		

		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_question, menu);
		return true;
	}

}
