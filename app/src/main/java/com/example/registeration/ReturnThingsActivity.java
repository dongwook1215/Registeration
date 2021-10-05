package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReturnThingsActivity extends AppCompatActivity {
    private ListView returnthingsListView;
    private ReturnThingsAdapter adapter;
    private List<ReturnThings> returnthingsList;
    private List<ReturnThings> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_things);
        Intent intent=getIntent();
        returnthingsListView=(ListView)findViewById(R.id.ThingsListView);
        returnthingsList = new ArrayList<ReturnThings>();
        saveList=new ArrayList<ReturnThings>();
        adapter =new ReturnThingsAdapter(getApplicationContext(),returnthingsList,this,saveList);
        returnthingsListView.setAdapter(adapter);

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReturnThingsActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("thingsList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count=0;
            int thingsID;
            String userID,thingsName,thingsWhere,thingsWhen;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                thingsID = object.getInt("thingsID");
                thingsName = object.getString("thingsName");
                thingsWhere = object.getString("thingsWhere");
                thingsWhen = object.getString("thingsWhen");
                ReturnThings user = new ReturnThings(thingsID,userID,thingsName,thingsWhere,thingsWhen);

                    returnthingsList.add(user);
                    saveList.add(user);

                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        final EditText search=(EditText)findViewById(R.id.idsearch);
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
        returnthingsList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getUserID().contains(search))
            {
                returnthingsList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
