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

public class MyThingsActivity extends AppCompatActivity {
    private ListView mythingsListView;
    private MyThingsAdapter adapter;
    private List<MyThings> mythingsList;
    private List<MyThings> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);
        Intent intent=getIntent();
        mythingsListView=(ListView)findViewById(R.id.mythingsListView);
        mythingsList = new ArrayList<MyThings>();
        saveList=new ArrayList<MyThings>();
        adapter =new MyThingsAdapter(getApplicationContext(),mythingsList,this,saveList);
        mythingsListView.setAdapter(adapter);

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
                Intent intent=new Intent(MyThingsActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("mythingsList"));
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
                MyThings user = new MyThings(thingsID,userID,thingsName,thingsWhere,thingsWhen);

                mythingsList.add(user);
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
        mythingsList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getThingsName().contains(search))
            {
                mythingsList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
