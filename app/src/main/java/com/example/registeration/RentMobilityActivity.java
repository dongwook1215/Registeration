package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RentMobilityActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayAdapter adapter1;
    private ArrayAdapter adapter2;
    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner2;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_mobility);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentMobilityActivity.this,NoticeActivity.class);
                intent.putExtra("userid",userid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentMobilityActivity.this,LoginActivity.class);
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
                Intent intent=new Intent(RentMobilityActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                RentMobilityActivity.this.startActivity(intent);
            }
        });
        ImageButton rent_ride=(ImageButton)findViewById(R.id.rent_ride);
        rent_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentMobilityActivity.this,RentMobilityActivity.class);
                intent.putExtra("userid",userid);
                RentMobilityActivity.this.startActivity(intent);
            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentMobilityActivity.this,MyPageActivity.class);
                intent.putExtra("userid",userid);
                RentMobilityActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentMobilityActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                RentMobilityActivity.this.startActivity(intent);
            }
        });

        spinner=(Spinner)findViewById(R.id.moblitychoice);
        adapter= ArrayAdapter.createFromResource(this,R.array.mobility,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1=(Spinner)findViewById(R.id.moblitywhere);
        adapter1= ArrayAdapter.createFromResource(this,R.array.mobility_where,android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2=(Spinner)findViewById(R.id.moblitywhen);
        adapter2= ArrayAdapter.createFromResource(this,R.array.mobility_when,android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final EditText mobilitycontent=(EditText)findViewById(R.id.Moblity_request);

        Button MobilityRent=(Button)findViewById(R.id.MoblityRent);
        MobilityRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check="1";
                String thingschoic=spinner.getSelectedItem().toString();
                String thingswhere=spinner1.getSelectedItem().toString();
                String thingswhen=spinner2.getSelectedItem().toString();
                if(thingschoic.equals("모빌리티를 선택해주세요")||thingswhere.equals("대여 장소를 선택해주세요")||thingswhen.equals("대여 기간을 선택해주세요"))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RentMobilityActivity.this);
                    dialog=builder.setMessage("모빌리티, 장소, 기간 모두 선택해주세요")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }else {
                    Intent intent = new Intent(RentMobilityActivity.this, PayActivity.class);
                    intent.putExtra("check",check);
                    intent.putExtra("userid", userid);
                    intent.putExtra("thingschoice", spinner.getSelectedItem().toString());
                    intent.putExtra("thingswhere", spinner1.getSelectedItem().toString());
                    intent.putExtra("thingswhen", spinner2.getSelectedItem().toString());
                    intent.putExtra("thingscontent", mobilitycontent.getText().toString());
                    RentMobilityActivity.this.startActivity(intent);
                }
            }
        });
        ImageButton showreview=(ImageButton)findViewById(R.id.showreview);
        showreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        protected void onPreExecute(){
            target="http://kdo6338.cafe24.com/ReviewList.php";
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
            Intent intent=new Intent(RentMobilityActivity.this,Pop.class);
            intent.putExtra("reviewList",result);
            RentMobilityActivity.this.startActivity(intent);
        }
    }
}
