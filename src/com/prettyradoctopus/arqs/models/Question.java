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
<<<<<<< HEAD
	String user, body, title;
=======
	String  user, body, title, qid;
	
	public String getQId() {
		return qid;
	}
>>>>>>> Including up and down vote button on the question adapter.
	
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

<<<<<<< HEAD
=======
	public void addQId(String s) {
		qid = s;
	}
	
>>>>>>> Including up and down vote button on the question adapter.
	public static ArrayList<Question> convertFromParseObjects(
			List<ParseObject> poQuestionList) {
		// Loop through the input, which is of type List, and put its
		// contents into an ArrayList.
		ArrayList<Question> al = new ArrayList<Question>();
		
		for (ParseObject po : poQuestionList) {
			Question q = new Question();
<<<<<<< HEAD
			q.addTitle((String) po.get("tittle"));
			q.addBody((String) po.get("body_content"));
			q.addUser((String) po.get("username"));
			
=======
			q.addQId((String) po.getObjectId());
			q.addTitle((String) po.get("tittle"));
			q.addBody((String) po.get("body_content"));
			q.addUser((String) po.get("username"));
		
>>>>>>> Including up and down vote button on the question adapter.
			al.add(q);
		}

		return al;
	}
}
