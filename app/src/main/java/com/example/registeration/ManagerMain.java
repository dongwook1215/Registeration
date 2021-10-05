package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManagerMain extends AppCompatActivity {
    private ListView questionListView;
    private QuestionListAdapter adapter;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        questionListView=(ListView)findViewById(R.id.questionListView);
        questionList = new ArrayList<Question>();
        questionList.add(new Question("","",""));
        adapter =new QuestionListAdapter(getApplicationContext(),questionList);
        questionListView.setAdapter(adapter);
        questionList.remove(0);
        new BackgroundTask().execute();

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManagerMain.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        TextView deleteThings=(TextView)findViewById(R.id.deleteThings);
        deleteThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask1().execute();
            }
        });

        TextView deleteMobility=(TextView)findViewById(R.id.deleteMobility);
        deleteMobility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask2().execute();
            }
        });

        TextView notice_register=(TextView)findViewById(R.id.notice_register);
        notice_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ManagerMain.this,NoticeRegister.class);
                ManagerMain.this.startActivity(intent);
            }
        });
        TextView user_management=(TextView)findViewById(R.id.userManagement);
        user_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ManagerMain.this,ManagementActivity.class);
                ManagerMain.this.startActivity(intent);
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/QuestionList.php";
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
                String questionContent, questionEmail, questionDate;
                while (count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    questionContent = object.getString("questionContent");
                    questionEmail = object.getString("questionEmail");
                    questionDate = object.getString("questionDate");
                    Question question = new Question(questionContent,questionEmail,questionDate);
                    questionList.add(question);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask1 extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/ThingsList.php";
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
            Intent intent=new Intent(ManagerMain.this,ReturnThingsActivity.class);
            intent.putExtra("thingsList",result);
            ManagerMain.this.startActivity(intent);
        }
    }
    class BackgroundTask2 extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/MobilityList.php";
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
            Intent intent=new Intent(ManagerMain.this,ReturnMobilityActivity.class);
            intent.putExtra("mobilityList",result);
            ManagerMain.this.startActivity(intent);
        }
    }
}
