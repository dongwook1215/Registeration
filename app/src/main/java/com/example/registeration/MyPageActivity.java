package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyPageActivity extends AppCompatActivity {
    String id;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");
        TextView user_id=(TextView)findViewById(R.id.user_id);
        user_id.setText(userid +"님");
        id=userid;

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyPageActivity.this,NoticeActivity.class);
                intent.putExtra("userid",userid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyPageActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ImageButton rent_things=(ImageButton)findViewById(R.id.rent_things);
        rent_things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyPageActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                MyPageActivity.this.startActivity(intent);
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(MyPageActivity.this);
                                dialog=builder.setMessage("License를 등록한 후 모빌리티 대여가 가능합니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                Intent intent=new Intent(MyPageActivity.this,RentMobilityActivity.class);
                                intent.putExtra("userid",userid);
                                MyPageActivity.this.startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(id,responseListener);
                RequestQueue queue= Volley.newRequestQueue(MyPageActivity.this);
                queue.add(validateRequest);
            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, MyPageActivity.class);
                intent.putExtra("userid",userid);
                MyPageActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                MyPageActivity.this.startActivity(intent);
            }
        });
        TextView licenseRegister=(TextView)findViewById(R.id.licenseRegister);
        licenseRegister.setOnClickListener(new View.OnClickListener() {
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
                                Intent intent = new Intent(MyPageActivity.this, LicenseActivity.class);
                                intent.putExtra("userid",userid);
                                MyPageActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(MyPageActivity.this);
                                dialog=builder.setMessage("이미 License를 등록한 회원입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(id,responseListener);
                RequestQueue queue= Volley.newRequestQueue(MyPageActivity.this);
                queue.add(validateRequest);

            }
        });
        TextView readme=(TextView)findViewById(R.id.readme);
        readme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                intent.putExtra("userid",userid);
                MyPageActivity.this.startActivity(intent);
            }
        });
        TextView mythings=(TextView)findViewById(R.id.mythings);
        mythings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
        TextView mymobility=(TextView)findViewById(R.id.mymobility);
        mymobility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask1().execute();
            }
        });
        TextView writereview=(TextView)findViewById(R.id.writereview);
        writereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, RegisterReviewActivity.class);
                intent.putExtra("userid",userid);
                MyPageActivity.this.startActivity(intent);
            }
        });
        TextView googlemap=(TextView)findViewById(R.id.googlemap);
        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, GoogleMapActivity.class);
                MyPageActivity.this.startActivity(intent);
            }
        });
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/MyThingsList.php?id="+ id;
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
            Intent intent=new Intent(MyPageActivity.this,MyThingsActivity.class);
            intent.putExtra("mythingsList",result);
            MyPageActivity.this.startActivity(intent);
        }
    }
    class BackgroundTask1 extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/MyMobilityList.php?id="+ id;
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
            Intent intent=new Intent(MyPageActivity.this,MyMobilityActivity.class);
            intent.putExtra("mymobilityList",result);
            MyPageActivity.this.startActivity(intent);
        }
    }
}
