package com.prettyradoctopus.arqs.models;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;



/*
 * This is the Parse equivalent of the models we used in the Twitter
 * exercise.  There, we made BaseModel.java and it gets extended by 
 * Tweet.java and User.java.  Here, Parse provides the base model. We 
 * will extend it in Question.java, User.java, Vote.java.
 * 
 * For more details, see https://parse.com/docs/android_guide#subclasses
 */

public class Vote  {
	String  user, body, title, qid, username;

	
	public String getQId() {
		return qid;
	}
	


	public void addQId(String s) {
		qid = s;
	}
	
	public static ArrayList<Question> convertFromParseObjects(
			List<ParseObject> voteList) {
		// Loop through the input, which is of type List, and put its
		// contents into an ArrayList.
		ArrayList<Question> al = new ArrayList<Question>();
		
		for (ParseObject po : voteList) {
			Question q = new Question();
			q.addQId((String)  po.get("qid"));
		//	Log.d("DEBUG", po.get("qid").toString());
			al.add(q);
		}

		return al;
	}
}
	
	


