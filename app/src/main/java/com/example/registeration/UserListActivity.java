package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private ListView userListView;
    private UserListAdapter adapter;
    private List<UserList> userList;
    private List<UserList> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent=getIntent();
        userListView=(ListView)findViewById(R.id.userListView);
        userList = new ArrayList<UserList>();
        saveList=new ArrayList<UserList>();
        adapter =new UserListAdapter(getApplicationContext(),userList,this,saveList);
        userListView.setAdapter(adapter);

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
                Intent intent=new Intent(UserListActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count=0;
            String userID, userPassword, userGender,userMajor,userEmail;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword = object.getString("userPassword");
                userGender = object.getString("userGender");
                userMajor = object.getString("userMajor");
                userEmail = object.getString("userEmail");
                UserList user = new UserList(userID,userPassword,userGender,userMajor,userEmail);
                if(!userID.equals("MANAGER")){
                    userList.add(user);
                    saveList.add(user);
                }
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
        userList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getUserID().contains(search))
            {
                userList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
