package com.prettyradoctopus.arqs;

import java.util.List;

import android.content.Context;
import android.graphics.LightingColorFilter;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.prettyradoctopus.arqs.models.Question;

public class QuestionsAdapter extends ArrayAdapter<Question> {
	String username = "defaultuser";
	
	public QuestionsAdapter(Context context, List<Question> questionList) {
		super(context, 0, questionList);
	}

	/**
	 * This constructor accepts the username from QuestionsListActivity (or anywhere else in the future)
	 * @param context
	 * @param questionList
	 * @param username		String that represents the ANDROID_ID for now - will be something 
	 * 						more human later.
	 */
	public QuestionsAdapter(Context context, List<Question> questionList,
			String username) {
		super(context, 0, questionList);
		this.username = username;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) 
					getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.question_item, null);
		}
	
		Question question = getItem(position);
		
		TextView titleView = (TextView) view.findViewById(R.id.tvTitle);
		String formattedTitle = "<b>" + question.getTitle() + "</b>";
		titleView.setText(Html.fromHtml(formattedTitle));

		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		String formattedBody = question.getBody() + "";
		bodyView.setText(Html.fromHtml(formattedBody));
		
		
		
		
	//	TextView usernameView = (TextView) view.findViewById(R.id.tvUsername);
	//	String formattedUsername = "<i>vote stuff goes here</i>";
		//usernameView.setText(Html.fromHtml(formattedUsername));
		
	//	TextView qidView = (TextView) view.findViewById(R.id.tvQid);
	//	String formattedQid = "<b>" + question.getQId() + "</b>";
	//	qidView.setText(Html.fromHtml(formattedQid));
		
		final Button btUp = (Button) view.findViewById(R.id.btUp);
		
		final Button btDown = (Button) view.findViewById(R.id.btDown);
		
		
		
		final String question_id = question.getQId();
		
		TextView countUp = (TextView) view.findViewById(R.id.tvCountUp);
		//int vote_up_count = QuestionsListActivity.query_up_count(question_id);
		//String voteup = Integer.toString(vote_up_count);
		//String formattedcountUp =  vote_up_count;
	//	countUp.setText(voteup);
		
		TextView countDown = (TextView) view.findViewById(R.id.tvCountDown);
		//int vote_down_count = QuestionsListActivity.query_down_count(question_id);
		//String votedown = Integer.toString(vote_down_count);
		//String formattedcountUp =  vote_up_count;
		//countDown.setText(votedown);
		
		
	//	QuestionsListActivity.checkVote(question_id, username);
		
	//	Boolean result = QuestionsListActivity.checkVote(question_id, username);
		
		
		//for up button
		
		//String s = "xeqDvd1px1";
		
		String up_result = QuestionsListActivity.checkVoteUp(question_id);
		String down_result = QuestionsListActivity.checkVoteDowned(question_id);
		if (up_result == "TRUE"){
			btUp.setBackgroundColor(0xFFFFF00);
			
			
		} else {
			btUp.setBackgroundColor(0xCCCCCCCC);
		}
		
		if (down_result == "TRUE"){
			btDown.setBackgroundColor(0xFFFFF00);
			
			
		} else {
			btDown.setBackgroundColor(0xCCCCCCCC);
		}


		
		
	//	for down buton
				
		
		
		
		
		
		
	
		btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		
             Log.d("DEBUG", "Up Vote for qid " + question_id);
           
             Boolean up = true;
             Boolean down = false;
             QuestionsListActivity.getVoteQuery(question_id, username, up, down);
             
            String up_result = QuestionsListActivity.checkVoteUp(question_id);
     		String down_result = QuestionsListActivity.checkVoteDowned(question_id);
     		if (up_result == "TRUE" | down_result == "TRUE"){
     			
     		} else {
     			btUp.setBackgroundColor(0xFFFFF00);
     		}
     		
     			
     			
     	
             
             
         
            }
        });
		
		btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Down Vote for qid " + question_id);
             
                Boolean up = false;
                Boolean down = true;
                QuestionsListActivity.getVoteQuery(question_id, username, up, down);
                
                String up_result = QuestionsListActivity.checkVoteUp(question_id);
         		String down_result = QuestionsListActivity.checkVoteDowned(question_id);
         		if (up_result == "TRUE" | down_result == "TRUE"){
         			
         		} else {
         			btDown.setBackgroundColor(0xFFFFF00);
         		}
             //   String vote_result =  QuestionsListActivity.getVoteQuery(question_id, username, up, down);
             //   Log.d("DEBUG", vote_result);
            
            }
        });
		
		
		view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	 Log.d("DEBUG", "Clicked the Item");
            }
        });
		
		
		return view;
	}


	
	
}
