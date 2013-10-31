package com.prettyradoctopus.arqs;

import java.util.ArrayList;
import java.util.List;

import com.prettyradoctopus.arqs.models.Question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "ARQS";
 
    // Table name
    private static final String TABLE_USER = "User";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_VOTES = "votes";
    
    // Common column names
    private static final String KEY_OBJECTID = "objectId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_CREATED_AT = "createdAt";
    private static final String KEY_UPDATED_AT = "updatedAt";

    // USER Table - column names
    private static final String KEY_PASSWORD = "password";
    
    // questions Table - column names
    private static final String KEY_TITLE = "tittle";
    private static final String KEY_BODYCONTENT = "body_content";
    
    // votes Table - column names
    private static final String KEY_QID = "qid";
    private static final String KEY_UP = "up";
    private static final String KEY_DOWN = "down";
 
    // Table Create Statements
    // USER table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_OBJECTID + " INTEGER PRIMARY KEY," 
        + KEY_USERNAME + " TEXT,"
        + KEY_PASSWORD + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" 
            + KEY_UPDATED_AT + " DATETIME" +")";
 
    // QUESTION table create statement
    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTIONS
            + "(" + KEY_OBJECTID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
            + KEY_BODYCONTENT + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" 
            + KEY_UPDATED_AT + " DATETIME" +")";
 
    // VOTES table create statement
    private static final String CREATE_TABLE_VOTES = "CREATE TABLE "
            + TABLE_VOTES + "(" + KEY_OBJECTID + " INTEGER PRIMARY KEY,"
            + KEY_QID + " TEXT," + KEY_UP + " BOOLEAN,"
            + KEY_DOWN + " BOOLEAN," 
            + KEY_USERNAME + " TEXT," 
            + KEY_CREATED_AT + " DATETIME" 
            + KEY_UPDATED_AT + " DATETIME" +")";
 
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_VOTES);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_VOTES);
 
        // create new tables
        onCreate(db);
    }

    
    
    // CRUD FUNCTIONS
    
// Create a user
    public void createUser(String u, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, u);
        values.put(KEY_PASSWORD, password);

        db.insert(TABLE_USER, null, values);
    }

// Creating a question
    public void createQuestion(String u, String tittle, String body) {
        SQLiteDatabase db = this.getWritableDatabase();    
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, u);
        values.put(KEY_TITLE, tittle);
        values.put(KEY_BODYCONTENT, body);
        
        // insert row
        db.insert(TABLE_QUESTIONS, null, values);
    }
    
// Get all questions, put into local db
    public void writeQuestionList(List<Question> questionList){
      SQLiteDatabase db = this.getWritableDatabase();    
        ContentValues values = new ContentValues();
      
      for (int i = 0; i < questionList.size(); i++ ){
            values.put(KEY_USERNAME, questionList.get(i).getUser());
          values.put(KEY_TITLE, questionList.get(i).getTitle());
          values.put(KEY_BODYCONTENT, questionList.get(i).getBody());
          db.insert(TABLE_QUESTIONS, null, values);
          
      }
      
      
    }
    
 // Read all questions in local DB
 // SELECT * FROM QUESTIONS
    public List<Question> getAllQuestions(){
      List<Question> questions = new ArrayList<Question>();
        SQLiteDatabase db = this.getReadableDatabase();
        
       String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;
       
          Log.e("DEBUG", selectQuery);
       
          Cursor c = db.rawQuery(selectQuery, null);
       
          // looping through all rows and adding to list
          if (c.moveToFirst()) {
              do {
                  Question question = new Question();
                  question.addUser(c.getString((c.getColumnIndex(KEY_USERNAME))));
                  question.addTitle((c.getString(c.getColumnIndex(KEY_TITLE))));
                  question.addBody((c.getString(c.getColumnIndex(KEY_BODYCONTENT))));
                  question.addQId(c.getString(c.getColumnIndex(KEY_QID)));
       
                  // adding to todo list
                  questions.add(question);
              } while (c.moveToNext());
          }
       
          return questions;
      
    }
    

 // Updating a vote to a question
    public void voteQuestion(String u, String qid, String vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, u);
        values.put(KEY_QID, qid);
        
        // insert row
        db.insert(TABLE_VOTES, null, values);
    }
    
 // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    
}

