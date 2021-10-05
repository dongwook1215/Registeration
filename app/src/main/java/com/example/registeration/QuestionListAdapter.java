package com.example.registeration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class QuestionListAdapter extends BaseAdapter {
    private Context context;
    private List<Question> questionList;

    public QuestionListAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.question, null);
        TextView questionText= (TextView)v.findViewById(R.id.questionText);
        TextView emailText= (TextView)v.findViewById(R.id.emailText);
        TextView dateText= (TextView)v.findViewById(R.id.dateText);

        questionText.setText(questionList.get(position).getQuestion());
        emailText.setText(questionList.get(position).getEmail());
        dateText.setText(questionList.get(position).getDate());

        v.setTag(questionList.get(position).getQuestion());

        ImageButton www=(ImageButton)v.findViewById(R.id.wwwButton);
        www.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.naver.com/"));
                context.startActivity(intent);
            }
        });
        return v;
    }
}
