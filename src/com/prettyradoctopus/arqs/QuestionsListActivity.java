package com.prettyradoctopus.arqs;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
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
	static ArrayList<String> VotesQuery = new ArrayList<String>();
//	String username;
	static String question_id, user, updown;
	public static Boolean up;
	public static Boolean down;
	
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
		question_id = "77EoWAn1fb";
	//	Toast.makeText(this, "trying to paint page", Toast.LENGTH_SHORT).show();
		//getVoteQuery(question_id);
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
				// QuestionsAdapter adapter = new QuestionsAdapter(getBaseContext(), questionList);
				String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
				QuestionsAdapter adapter = new QuestionsAdapter(getBaseContext(), questionList, username);
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
	
	public static void getVoteQuery(String getQuestionId, String getUserName, Boolean getUp, Boolean getDown) {
		question_id = getQuestionId;
		user = getUserName;
		//updown = getUpDown;
		final Boolean up = getUp;
		final Boolean down = getDown;
	//	if (updown == "up") {
	//		Boolean up = true;
	//		Boolean down = false;
	//	} else if (updown == "down") {
	//		Boolean up = false;
	//		Boolean down = true;
//		} else {
			//Nothing
//		}
		// private ListView lv;
		// lv = (ListView) findViewById(R.id.myList);
		 
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("votes");
		 query.whereEqualTo("qid", question_id);
		 query.whereEqualTo("username", user);
		    query.findInBackground(new FindCallback<ParseObject>() {
		        public void done(List<ParseObject> votesList, ParseException e) {
		            if (e == null) {
		                
		                for(int i=0;i<votesList.size();i++)  {
		                     Log.d("data","Retrieved Object is " + votesList.get(i).getString("Date"));  
		                     VotesQuery.add( votesList.get(i).getString("qid"));
		                     Log.d("DEBUG", votesList.get(i).getString("qid"));
		                    // String querysize = getString(votesList.size());
		                     
		                } 
		                Log.d("DEBUG", "Retrieved " + votesList.size() + " in list " );
		                int querysize = votesList.size();
		                if (querysize == 0) {
		                	 Log.d("DEBUG", "Yuppi, no records found, the size is " + votesList.size() + " in list " );
		                		//String u = Secure.getString(LoginActivity.getContentResolver(),Secure.ANDROID_ID);
		                 	//String username = Configuration.getUsername();
		                  Log.d("DEBUG", "Up Vote for qid " + question_id);
		                  
		                  
		                  
		                  ParseObject vote = new ParseObject("votes"); 
		                  vote.put("qid", question_id); 
		                  vote.put("username", user);
		                  vote.put("up", up);
		                  vote.put("down", down); 
		                  vote.saveInBackground();

		                 //    Toast.makeText(parent.getContext(), "button clicked: " + dataModel.getAnInt(), Toast.LENGTH_SHORT
		                
		                } else {
		                	Log.d("DEBUG", "Ohhh Ohhhh, records found, the size is " + votesList.size() + " in list " );
		                }
		                
		            } else {
		                Log.d("DEBUG", e.getMessage());
		            }
		        }
		    });
	//return result;
	}
	
	

}
