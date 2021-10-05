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

public class MyMobilityActivity extends AppCompatActivity {
    private ListView mymobilityListView;
    private MyMobilityAdapter adapter;
    private List<MyMobility> mymobilityList;
    private List<MyMobility> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mobility);

        Intent intent=getIntent();
        mymobilityListView=(ListView)findViewById(R.id.mymobilityListView);
        mymobilityList = new ArrayList<MyMobility>();
        saveList=new ArrayList<MyMobility>();
        adapter =new MyMobilityAdapter(getApplicationContext(),mymobilityList,this,saveList);
        mymobilityListView.setAdapter(adapter);

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
                Intent intent=new Intent(MyMobilityActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("mymobilityList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count=0;
            int mobilityID;
            String userID,mobilityName,mobilityWhere,mobilityWhen;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                mobilityID = object.getInt("mobilityID");
                mobilityName = object.getString("mobilityName");
                mobilityWhere = object.getString("mobilityWhere");
                mobilityWhen = object.getString("mobilityWhen");
                MyMobility user = new MyMobility(mobilityID,userID,mobilityName,mobilityWhere,mobilityWhen);

                mymobilityList.add(user);
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
        mymobilityList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getMobilityName().contains(search))
            {
                mymobilityList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
