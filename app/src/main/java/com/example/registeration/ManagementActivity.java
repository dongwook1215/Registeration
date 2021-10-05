package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

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
                Intent intent=new Intent(ManagementActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        Button userListButton=(Button)findViewById(R.id.userListButton);
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        Button noticeButton=(Button)findViewById(R.id.noticemanage);
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask1().execute();
            }
        });
        Button licensButton=(Button)findViewById(R.id.licenseButton);
        licensButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask2().execute();
            }
        });

    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/UserList.php";
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
            Intent intent=new Intent(ManagementActivity.this,UserListActivity.class);
            intent.putExtra("userList",result);
            ManagementActivity.this.startActivity(intent);
        }
    }
    class BackgroundTask1 extends AsyncTask<Void, Void, String>
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
            Intent intent=new Intent(ManagementActivity.this,ManagerNoticeListActivity.class);
            intent.putExtra("noticeList",result);
            ManagementActivity.this.startActivity(intent);
        }
    }

    class BackgroundTask2 extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/LicenseList.php";
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
            Intent intent=new Intent(ManagementActivity.this,LicenseListActivity.class);
            intent.putExtra("licenseList",result);
            ManagementActivity.this.startActivity(intent);
        }
    }
}
