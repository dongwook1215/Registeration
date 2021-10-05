package com.example.registeration;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private List<ReviewList> reviewList;
    private Activity parentActiviy;
    private List<ReviewList> saveList;

    public ReviewAdapter(Context context, List<ReviewList> reviewList, Activity parentActivity, List<ReviewList> saveList) {
        this.context = context;
        this.reviewList = reviewList;
        this.parentActiviy=parentActivity;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v= View.inflate(context, R.layout.review, null);
        TextView idText= (TextView)v.findViewById(R.id.idText);
        TextView mobilitynameText= (TextView)v.findViewById(R.id.mobilitynameText);
        TextView scoreText= (TextView)v.findViewById(R.id.scoreText);
        final TextView reviewText= (TextView)v.findViewById(R.id.reviewText);


        idText.setText(reviewList.get(position).getUserID());
        mobilitynameText.setText(reviewList.get(position).getMobilityName());
        scoreText.setText(reviewList.get(position).getMobilityScore());
        reviewText.setText(reviewList.get(position).getReviewContent());

        v.setTag(reviewList.get(position).getReviewContent());

        return v;
    }
}
