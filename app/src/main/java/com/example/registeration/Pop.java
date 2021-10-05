package com.example.registeration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pop extends Activity {
    private ListView reviewListView;
    private ReviewAdapter adapter;
    private List<ReviewList> reviewList;
    private List<ReviewList> saveList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);

        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.85));

        Intent intent=getIntent();
        reviewListView=(ListView)findViewById(R.id.reviewListView);
        reviewList = new ArrayList<ReviewList>();
        saveList=new ArrayList<ReviewList>();
        adapter =new ReviewAdapter(getApplicationContext(),reviewList,this,saveList);
        reviewListView.setAdapter(adapter);


        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("reviewList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count=0;
            String userID,mobilityName,mobilityScore,reviewContent;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                mobilityName = object.getString("mobilityName");
                mobilityScore = object.getString("mobilityScore");
                reviewContent = object.getString("reviewContent");
                ReviewList user = new ReviewList(userID,mobilityName,mobilityScore,reviewContent);
                reviewList.add(user);
                saveList.add(user);
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        final EditText search=(EditText)findViewById(R.id.mobilitysearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchUser(String search){
        reviewList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getMobilityName().contains(search))
            {
                reviewList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
