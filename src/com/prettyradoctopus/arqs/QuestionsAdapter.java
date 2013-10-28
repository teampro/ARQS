package com.prettyradoctopus.arqs;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.prettyradoctopus.arqs.models.Question;

public class QuestionsAdapter extends ArrayAdapter<Question> {
	public QuestionsAdapter(Context context, List<Question> questionList) {
		super(context, 0, questionList);
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
		String formattedBody = question.getBody() + " - <i>" + question.getUser() + "</i>";
		bodyView.setText(Html.fromHtml(formattedBody));
		
		TextView usernameView = (TextView) view.findViewById(R.id.tvUsername);
		String formattedUsername = "<i>vote stuff goes here</i>";
		usernameView.setText(Html.fromHtml(formattedUsername));
		
		TextView qidView = (TextView) view.findViewById(R.id.tvQid);
		String formattedQid = "<b>" + question.getQId() + "</b>";
		qidView.setText(Html.fromHtml(formattedQid));
		
		Button btUp = (Button) view.findViewById(R.id.btUp);
		
		Button btDown = (Button) view.findViewById(R.id.btDown);
		
		
		final String question_id = question.getQId();
		
		
		
		//final String username = "somethin";
		btUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Log.d("DEBUG", "Up Vote for qid " + question_id);
             ParseObject vote = new ParseObject("votes"); 
             vote.put("qid", question_id); 
           //  vote.put("username", username);
             vote.put("up", true);
             vote.put("down", false); 
             vote.saveInBackground();

            //    Toast.makeText(parent.getContext(), "button clicked: " + dataModel.getAnInt(), Toast.LENGTH_SHORT).show();
            }
        });
		
		btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Down Vote for qid " + question_id);
                ParseObject vote = new ParseObject("votes"); 
                vote.put("qid", question_id); 
          //      vote.put("username", username);
                vote.put("up", false);
                vote.put("down", true); 
      			vote.saveInBackground();

            //    Toast.makeText(parent.getContext(), "button clicked: " + dataModel.getAnInt(), Toast.LENGTH_SHORT).show();
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
