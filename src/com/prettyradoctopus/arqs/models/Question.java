package com.prettyradoctopus.arqs.models;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;

/*
 * This is the Parse equivalent of the models we used in the Twitter
 * exercise.  There, we made BaseModel.java and it gets extended by 
 * Tweet.java and User.java.  Here, Parse provides the base model. We 
 * will extend it in Question.java, User.java, Vote.java.
 * 
 * For more details, see https://parse.com/docs/android_guide#subclasses
 */

public class Question {
	//ParseObject q = new ParseObject("questions");
	String  user, body, title, qid;
	
	public String getQId() {
		return qid;
	}
	
	public String getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public void addTitle(String s) {
		title = s;
	}

	public void addBody(String s) {
		body = s;
	}

	public void addUser(String s) {
		user = s;
	}

	public void addQId(String s) {
		qid = s;
	}
	
	public static ArrayList<Question> convertFromParseObjects(
			List<ParseObject> poQuestionList) {
		// Loop through the input, which is of type List, and put its
		// contents into an ArrayList.
		ArrayList<Question> al = new ArrayList<Question>();
		
		for (ParseObject po : poQuestionList) {
			Question q = new Question();
			q.addQId((String) po.getObjectId());
			q.addTitle((String) po.get("tittle"));
			q.addBody((String) po.get("body_content"));
			q.addUser((String) po.get("username"));
		
			al.add(q);
		}

		return al;
	}
}
