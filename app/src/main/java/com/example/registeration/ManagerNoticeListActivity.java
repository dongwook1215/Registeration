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

public class ManagerNoticeListActivity extends AppCompatActivity {
    private ListView noticeListView;
    private ManagerNoticeListAdapter adapter;
    private List<Notice> noticeList;
    private List<Notice> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notice_list);
        Intent intent=getIntent();
        noticeListView=(ListView)findViewById(R.id.ManagerNoticeListView);
        noticeList = new ArrayList<Notice>();
        saveList=new ArrayList<Notice>();
        adapter =new ManagerNoticeListAdapter(getApplicationContext(),noticeList,this,saveList);
        noticeListView.setAdapter(adapter);

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
                Intent intent=new Intent(ManagerNoticeListActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("noticeList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count=0;
            String noticeContent,noticeName,noticeDate;
            while (count < jsonArray.length())
            {
                JSONObject object = jsonArray.getJSONObject(count);
                noticeContent = object.getString("noticeContent");
                noticeName = object.getString("noticeName");
                noticeDate = object.getString("noticeDate");;
                Notice notice = new Notice(noticeContent,noticeName,noticeDate);

                noticeList.add(notice);
                saveList.add(notice);

                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        final EditText search=(EditText)findViewById(R.id.noticesearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNotice(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void searchNotice(String search){
        noticeList.clear();
        for(int i=0;i<saveList.size();i++)
        {
            if(saveList.get(i).getName().contains(search))
            {
                noticeList.add(saveList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
