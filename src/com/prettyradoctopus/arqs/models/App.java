package com.prettyradoctopus.arqs.models;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		ParseObject.registerSubclass(Question.class);
		Parse.initialize(this, "sOb0qqC2Cetj5Aiw4RAQlLvF4lQEz4tJTobQBG7D", 
				"U6jMtz0vkTqtICvZhvrfKAsKadx56XRi0UfO3yii"); 
	}

}
