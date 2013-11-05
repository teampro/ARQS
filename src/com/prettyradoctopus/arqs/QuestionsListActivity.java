package com.prettyradoctopus.arqs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
import com.prettyradoctopus.arqs.models.Vote;

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
	//public List<Question> votes;
	public List<ParseObject> poQuestionList;
	public List<ParseObject> voteList;
	public List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
	public static String result;
	public static ArrayList<String> votes_upped = new ArrayList<String>();
	public static ArrayList<String> votes_upped_all = new ArrayList<String>();
	public static ArrayList<String> votes_downed = new ArrayList<String>();
	public static ArrayList<String> votes_downed_all = new ArrayList<String>();
	public static int up_votes_size;
	public static int down_votes_size;
	//public static ArrayList<HashMap<String, String>> list_up_count = new ArrayList<HashMap<String, String>>();
	public static HashMap<String, String> list_up_count = new HashMap<String, String>();
	public static HashMap<String, String> list_down_count = new HashMap<String, String>();
	
	
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
	        case R.id.action_query_all:
	        	queryAll();
	        	return true;
	        case R.id.action_query_mine:
	        	queryMine();
	        	return true;	
	        case R.id.action_my_uped:
	        	queryUped();
	        	return true;
	        case R.id.action_my_downed:
	        	queryDowned();
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
	
	
	public void queryAll() {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("questions");
			query.orderByDescending("createdAt");
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> poQuestionList, ParseException e) {
			        if (e == null) {
			        	 for (Question test : Question.convertFromParseObjects(poQuestionList)) {
					        	final String qid_to_query = test.getQId();
					        	Log.d("DEBUG", qid_to_query);
					        	
					        	query_upped(qid_to_query);
					        	query_up_count(qid_to_query);
					        	
					        	query_downed(qid_to_query);
					        	query_down_count(qid_to_query);
					    		
					        	
					    		drawPage(Question.convertFromParseObjects(poQuestionList));
					    	//	drawPage(Vote.convertFromParseObjects(voteList));
					        	
					        }
			        //    drawPage(Question.convertFromParseObjects(poQuestionList));
			        	 
			        } else {
			        	Toast.makeText(QuestionsListActivity.this, 
			            		"Error pulling questions",
			            		Toast.LENGTH_LONG).show();
			        }
			     //   drawPage(Question.convertFromParseObjects(poQuestionList));
			       
			       
			    }

				
			});
			
			
	}
	
	
	protected void query_downed(String get_qid_to_query) {
		final String qid_to_query = get_qid_to_query;
		ParseQuery<ParseObject> downed_votes = ParseQuery.getQuery("votes");
    	downed_votes.whereEqualTo("username", user);
    	downed_votes.whereEqualTo("qid", qid_to_query);
    	downed_votes.whereEqualTo("down", true);
    	downed_votes.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteListDowned, ParseException e) {
		    	//ArrayList<String> votes_upped = new ArrayList<String>();
		        if (e == null) {
		        	final List<Question> votesdowned = Vote.convertFromParseObjects(voteListDowned);
		        	if (votesdowned.size() == 0) {
		        		Log.d("DEBUG", "NO UPPED");
		        		
		        		
		        	} else {
		        	//	Log.d("DEBUG", qid_to_query + " UPPED");
		        		votes_downed.add(qid_to_query);
		        	}
		        	
		        	} else {
		        	Toast.makeText(QuestionsListActivity.this, 
		            		"Error pulling votes",
		            		Toast.LENGTH_LONG).show();
		        }
		        
		    }
		    
		    	
		});
	
		
	}

	protected static void query_upped(String get_qid_to_query) {
		final String qid_to_query = get_qid_to_query;
		ParseQuery<ParseObject> uped_votes = ParseQuery.getQuery("votes");
    	uped_votes.whereEqualTo("username", user);
    	uped_votes.whereEqualTo("qid", qid_to_query);
		uped_votes.whereEqualTo("up", true);
		uped_votes.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteListUpped, ParseException e) {
		    	//ArrayList<String> votes_upped = new ArrayList<String>();
		        if (e == null) {
		        	final List<Question> votes = Vote.convertFromParseObjects(voteListUpped);
		        	if (votes.size() == 0) {
		        		Log.d("DEBUG", "NO UPPED");
		        		
		        		
		        	} else {
		        	//	Log.d("DEBUG", qid_to_query + " UPPED");
		        		votes_upped.add(qid_to_query);
		        	}
		        	
		        	} else {
		        	//Toast.makeText(QuestionsListActivity.this, 
		            //		"Error pulling votes",
		            //		Toast.LENGTH_LONG).show();
		        }
		        
		    }
		    
		    	
		});
	
		
	}

	public void queryMine() {

			String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("questions");
			query.whereEqualTo("username", username);
			query.orderByDescending("createdAt");
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> poQuestionList, ParseException e) {
			        if (e == null) {

			            drawPage(Question.convertFromParseObjects(poQuestionList));
			        } else {
			        	Toast.makeText(QuestionsListActivity.this, 
			            		"Error pulling questions",
			            		Toast.LENGTH_LONG).show();
			        }
			    }

				
			});
		
	}
	
	public void queryUped() {
		
		
		String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		
		ParseQuery<ParseObject> uped_votes = ParseQuery.getQuery("votes");
		uped_votes.whereEqualTo("username", username);
		uped_votes.whereEqualTo("up", true);
		uped_votes.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteList, ParseException e) {
		        if (e == null) {
		        
		     
		        	
		        	final List<Question> votes = Vote.convertFromParseObjects(voteList);
		        //	final List<Question> list = Question.convertFromParseObjects(voteList);
		        	queries.clear();
		        	for (final ParseObject po : voteList) {
		    			final Question q = new Question();
		    			q.addQId((String)  po.get("qid"));
		    			Log.d("DEBUG", po.get("qid").toString());
		    			final String qid_to_look = po.get("qid").toString();
		    		  	ParseQuery<ParseObject> queryqid = ParseQuery.getQuery("questions");
		    		  	queryqid.whereEqualTo("objectId", qid_to_look);
		    		  	
		    		  	//List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		    		  	queries.add(queryqid);
		    		  	
		    		  	

			    		//drawPage(Question.convertFromParseObjects(poQuestionList));
		    		}
		        	if (queries.size() == 0) {
		        		Toast.makeText(QuestionsListActivity.this, 
			            		"You dont have questions upped",
			            		Toast.LENGTH_LONG).show(); }
		        	else {
		        	
		        		ParseQuery<ParseObject> query = ParseQuery.or(queries);;
					
		        		query.orderByDescending("createdAt");
		        		query.findInBackground(new FindCallback<ParseObject>() {
		        			public void done(List<ParseObject> QuestionsList, ParseException e) {
		        				if (e == null) {
		        					if (QuestionsList.size() == 0) {
		        						Toast.makeText(QuestionsListActivity.this, 
		        								"Error pulling questions",
		        								Toast.LENGTH_LONG).show();
		        					} else {
		        						drawPage(Question.convertFromParseObjects(QuestionsList));
		        					}
					            
		        				} else {
					        	Toast.makeText(QuestionsListActivity.this, 
					            		"Error pulling questions",
					            		Toast.LENGTH_LONG).show();
					        }
					    }

						
					});
		        	
		        	
		        
		        	
		      
		        	}    	
		        	
		            
		        } else {
		        	Toast.makeText(QuestionsListActivity.this, 
		            		"Error pulling questions",
		            		Toast.LENGTH_LONG).show();
		        }
		        
		    }

		    	
		});
		
		
		
		
		 
		
		
	
}
	
	public void queryDowned() {

		String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		
		ParseQuery<ParseObject> uped_votes = ParseQuery.getQuery("votes");
		uped_votes.whereEqualTo("username", username);
		uped_votes.whereEqualTo("down", true);
		uped_votes.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteList, ParseException e) {
		        if (e == null) {
		        
		     
		        	
		        	final List<Question> votes = Vote.convertFromParseObjects(voteList);
		        //	final List<Question> list = Question.convertFromParseObjects(voteList);
		        	queries.clear();
		        	for (final ParseObject po : voteList) {
		    			final Question q = new Question();
		    			q.addQId((String)  po.get("qid"));
		    			Log.d("DEBUG", po.get("qid").toString());
		    			final String qid_to_look = po.get("qid").toString();
		    		  	ParseQuery<ParseObject> queryqid = ParseQuery.getQuery("questions");
		    		  	queryqid.whereEqualTo("objectId", qid_to_look);
		    		  	
		    		  	//List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		    		  	queries.add(queryqid);
		    		  	
		    		  	

			    		//drawPage(Question.convertFromParseObjects(poQuestionList));
		    		}
		        	
		        	if (queries.size() == 0) {
		        		Toast.makeText(QuestionsListActivity.this, 
			            		"You dont have questions downed",
			            		Toast.LENGTH_LONG).show(); }
		        	else {
		        	ParseQuery<ParseObject> query = ParseQuery.or(queries);;
					
					query.orderByDescending("createdAt");
					query.findInBackground(new FindCallback<ParseObject>() {
					    public void done(List<ParseObject> QuestionsList, ParseException e) {
					        if (e == null) {

					            drawPage(Question.convertFromParseObjects(QuestionsList));
					        } else {
					        	Toast.makeText(QuestionsListActivity.this, 
					            		"Error pulling questions",
					            		Toast.LENGTH_LONG).show();
					        }
					    }

						
					});
		        	
		        	
		        
		        	
		        	}
		        	
		        	
		            
		        } else {
		        	Toast.makeText(QuestionsListActivity.this, 
		            		"Error pulling questions",
		            		Toast.LENGTH_LONG).show();
		        }
		        
		    }

			
		});
		
		
		
	
}	
	
	
	
	
	private void refreshQuestions() {
		queryAll();
	
	}
	
	private void drawPage(List<Question> questionList) {

		String username = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
		QuestionsAdapter adapter = new QuestionsAdapter(getBaseContext(), questionList, username);
		ListView lvQuestions = (ListView) findViewById(R.id.lvQuestions);
		lvQuestions.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.questions, menu);
		return true;
	}
	
	public static String checkVoteUp(String getQuestionId) {
		question_id = getQuestionId;
		//user = getUserName;
		//Boolean checkboolean = getBoolean;
		
		
		
		   if( votes_upped.contains( question_id ) ) {
		     Log.d("DEBUG", "Votes upped");
		     result = "TRUE";
		   } else {
			   Log.d("DEBUG", "NOTHING");
			   result = "FALSE";
		   }
		   return result;
	
		   
	}
	
	public static String checkVoteDowned(String getQuestionId) {
		question_id = getQuestionId;
		//user = getUserName;
		//Boolean checkboolean = getBoolean;
		
		
		
		   if( votes_downed.contains( question_id ) ) {
		     Log.d("DEBUG", "Votes upped");
		     result = "TRUE";
		   } else {
			   Log.d("DEBUG", "NOTHING");
			   result = "FALSE";
		   }
		   return result;
	
		   
	}
	
	public static String getVoteQuery(String getQuestionId, String getUserName, Boolean getUp, Boolean getDown) {
		question_id = getQuestionId;
		user = getUserName;
		String result_from_vote = null;
	
		//final String result_from_vote;
		final Boolean up = getUp;
		final Boolean down = getDown;
		
		//String result_from_vote = null;

		 ParseQuery<ParseObject> query = ParseQuery.getQuery("votes");
		 query.whereEqualTo("qid", question_id);
		 query.whereEqualTo("username", user);
		    query.findInBackground(new FindCallback<ParseObject>() {
		        public void done(List<ParseObject> votesList, ParseException e) {
		            if (e == null) {
		                
		                for(int i=0;i<votesList.size();i++)  {
		                   
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
		                  final String result_from_vote = "TRUE";
		                  Log.d("DEBUG", result_from_vote);

		                 //    Toast.makeText(parent.getContext(), "button clicked: " + dataModel.getAnInt(), Toast.LENGTH_SHORT
		                
		                } else {
		                	Log.d("DEBUG", "Ohhh Ohhhh, records found, the size is " + votesList.size() + " in list " );
		                	final String result_from_vote = "FALSE";
		                	Log.d("DEBUG", result_from_vote);
		                	
		                }
		                
		            } else {
		                Log.d("DEBUG", e.getMessage());
		                
		            }
		        }
		    });
			return result_from_vote;
			
			//Log.d("DEBUG", result_from_vote);
			
			
		   
		    
	}
	
	public static void query_up_count(String get_qid_to_query) {
		//int up_votes_size = 0;
		final String qid_to_query = get_qid_to_query;
		ParseQuery<ParseObject> uped_votes_count = ParseQuery.getQuery("votes");
		uped_votes_count.whereEqualTo("qid", qid_to_query);
		uped_votes_count.whereEqualTo("up", true);
		uped_votes_count.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteListUpped, ParseException e) {
		    	//ArrayList<String> votes_upped = new ArrayList<String>();
		        if (e == null) {
		        	final List<Question> votes_up_count = Vote.convertFromParseObjects(voteListUpped);
		        	
		        	up_votes_size = votes_up_count.size();
		        	
		        	Log.d("DEBUG", qid_to_query + " " + up_votes_size);
		        //	votes_upped_all.add(qid_to_query + "|" + up_votes_size);
		        	String votes_up_string = Integer.toString(up_votes_size);
		        	list_up_count.put(qid_to_query, votes_up_string);
		        	
		        	} else {
		        	//Toast.makeText(QuestionsListActivity.this, 
		            //		"Error pulling votes",
		            //		Toast.LENGTH_LONG).show();
		        }
		        
		    }
		    
		    	
		});
		
		
		
	}
	
	public static void query_down_count(String get_qid_to_query) {
		
		final String qid_to_query = get_qid_to_query;
		ParseQuery<ParseObject> uped_votes_count = ParseQuery.getQuery("votes");
		uped_votes_count.whereEqualTo("qid", qid_to_query);
		uped_votes_count.whereEqualTo("down", true);
		uped_votes_count.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> voteListUpped, ParseException e) {
		    	//ArrayList<String> votes_upped = new ArrayList<String>();
		        if (e == null) {
		        	final List<Question> votes_down_count = Vote.convertFromParseObjects(voteListUpped);
		        	
		        	down_votes_size = votes_down_count.size();
		        	
		        	//votes_downed_all.add(qid_to_query + "|" + down_votes_size);
		        	String votes_down_string = Integer.toString(down_votes_size);
		        	list_down_count.put(qid_to_query, votes_down_string);
		        	Log.d("DEBUG", qid_to_query + " " + down_votes_size);
		        	
		        	} else {
		        	//Toast.makeText(QuestionsListActivity.this, 
		            //		"Error pulling votes",
		            //		Toast.LENGTH_LONG).show();
		        }
		        
		    }
		    
		    	
		});
		
		
		
	}
	
	

}
