package com.prettyradoctopus.arqs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
		setContentView(R.layout.activity_questions);
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
		Toast.makeText(this, "wierd bug - XXX", Toast.LENGTH_SHORT).show();

/*		Intent i = new Intent(this, SubmitQuestionActivity.class);
		startActivity(i);

*/	}

	/* 
	 * This is used when the page is first created and whenever the page is
	 * refreshed
	 */
	private void refreshQuestions() {
		Toast.makeText(this, "trying to paint page", Toast.LENGTH_SHORT).show();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("questions");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> poQuestionList, ParseException e) {
		        if (e == null) {
		            Toast.makeText(QuestionsListActivity.this, 
		            		"found: " + poQuestionList.size(),
		            		Toast.LENGTH_SHORT).show();
		            //drawPage(poQuestionList);
		        } else {
		        	Toast.makeText(QuestionsListActivity.this, 
		            		"Error pulling questions",
		            		Toast.LENGTH_LONG).show();
		        }
		    }

			private void drawPage(List<ParseObject> poQuestionList) {
				// We have to convert from ParseObject to Question
				ArrayList<Question> questionList = 
						Question.convertFromParseObjects(poQuestionList);
				
				QuestionsAdapter qa = new QuestionsAdapter(getBaseContext(),
						questionList);
				ListView lvQuestions = (ListView) findViewById(R.id.lvQuestions);
				lvQuestions.setAdapter(qa);
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
