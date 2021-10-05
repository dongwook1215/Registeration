package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");

        noticeListView=(ListView)findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("","",""));
        adapter =new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);
        noticeList.remove(0);
        new BackgroundTask().execute();

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
                Intent intent=new Intent(NoticeActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton rent_things=(ImageButton)findViewById(R.id.rent_things);
        rent_things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoticeActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                NoticeActivity.this.startActivity(intent);
            }
        });
        ImageButton rent_ride=(ImageButton)findViewById(R.id.rent_ride);
        rent_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(NoticeActivity.this);
                                dialog=builder.setMessage("License를 등록한 후 모빌리티 대여가 가능합니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                Intent intent=new Intent(NoticeActivity.this,RentMobilityActivity.class);
                                intent.putExtra("userid",userid);
                                NoticeActivity.this.startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(userid,responseListener);
                RequestQueue queue= Volley.newRequestQueue(NoticeActivity.this);
                queue.add(validateRequest);

            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeActivity.this, MyPageActivity.class);
                intent.putExtra("userid",userid);
                NoticeActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                NoticeActivity.this.startActivity(intent);
            }
        });
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try{
                URL url= new URL(target);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp=bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent,noticeName,noticeDate);
                    noticeList.add(notice);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
