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

public class Question extends ParseObject {
	ParseObject q = new ParseObject("questions");
	
	public User getUser() {
		return null;
	}

	public String getBody() {
		return null;
	}

	public String getTitle() {
		return q.getString("tittle");
	}

	public static ArrayList<Question> convertFromParseObjects(
			List<ParseObject> poQuestionList) {
		// Loop through the input, which is of type List, and put its
		// contents into an ArrayList.
		ArrayList<Question> ar = new ArrayList<Question>();
		
		for (ParseObject p : poQuestionList) {
			ar.add( (Question) p);
		}

		return ar;
	}
}
