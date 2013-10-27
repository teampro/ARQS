package com.prettyradoctopus.arqs.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
 * This is the Parse equivalent of the models we used in the Twitter
 * exercise.  There, we made BaseModel.java and it gets extended by 
 * Tweet.java and User.java.  Here, Parse provides the base model. We 
 * will extend it in Question.java, User.java, Vote.java.
 * 
 * For more details, see https://parse.com/docs/android_guide#subclasses
 */
@ParseClassName("votes")
public class Vote extends ParseObject {

}
