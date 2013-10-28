package com.prettyradoctopus.arqs;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.prettyradoctopus.arqs.models.Question;

/*
 * This activity shows all the questions asked so far.  It will
 * allow the user to compose a new question and vote for the
 * existing questions.  
 * 
 * She can also refresh the page to see new questions.
 */
public class QuestionsListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_list);
		refreshQuestions();
	}

	// This is called when user hits refresh or compose from action bar.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_refresh:
	            refreshQuestions();
	            return true;
	        case R.id.action_compose:
	            composeQuestion();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void composeQuestion() {
		Intent i = new Intent(this, SubmitQuestionActivity.class);
		i.putExtra("subject", "");
		i.putExtra("question", "");
		startActivity(i);
	}
	

	/* 
	 * This is used when the page is first created and whenever the page is
	 * refreshed.  We display questions in choronological order with the newest
	 * questions on top.  This gives the user the satisfaction of seeing their
	 * newly created question on top.  Top is almost as good as pro.
	 */
	private void refreshQuestions() {
	//	Toast.makeText(this, "trying to paint page", Toast.LENGTH_SHORT).show();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("questions");
		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> poQuestionList, ParseException e) {
		        if (e == null) {
/*		            Toast.makeText(QuestionsListActivity.this, 
		            		"found: " + poQuestionList.size(),
		            		Toast.LENGTH_SHORT).show();*/
		            drawPage(Question.convertFromParseObjects(poQuestionList));
		        } else {
		        	Toast.makeText(QuestionsListActivity.this, 
		            		"Error pulling questions",
		            		Toast.LENGTH_LONG).show();
		        }
		    }

			private void drawPage(List<Question> questionList) {
				// We have to convert from ParseObject to Question
				QuestionsAdapter adapter = new QuestionsAdapter(getBaseContext(), questionList);
				ListView lvQuestions = (ListView) findViewById(R.id.lvQuestions);
				lvQuestions.setAdapter(adapter);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}
	
	

}
