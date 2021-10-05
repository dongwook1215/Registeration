package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RentThingsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_rent_things);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");

        spinner=(Spinner)findViewById(R.id.thingschoice);
        adapter= ArrayAdapter.createFromResource(this,R.array.things,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1=(Spinner)findViewById(R.id.thingswhere);
        adapter1= ArrayAdapter.createFromResource(this,R.array.things_where,android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2=(Spinner)findViewById(R.id.thingswhen);
        adapter2= ArrayAdapter.createFromResource(this,R.array.things_when,android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        final EditText thingsrequest=(EditText)findViewById(R.id.things_request);

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentThingsActivity.this,NoticeActivity.class);
                intent.putExtra("userid",userid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentThingsActivity.this,LoginActivity.class);
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
                Intent intent=new Intent(RentThingsActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                RentThingsActivity.this.startActivity(intent);
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(RentThingsActivity.this);
                                dialog=builder.setMessage("License를 등록한 후 모빌리티 대여가 가능합니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                Intent intent=new Intent(RentThingsActivity.this,RentMobilityActivity.class);
                                intent.putExtra("userid",userid);
                                RentThingsActivity.this.startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(userid,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RentThingsActivity.this);
                queue.add(validateRequest);
            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RentThingsActivity.this,MyPageActivity.class);
                intent.putExtra("userid",userid);
                RentThingsActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentThingsActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                RentThingsActivity.this.startActivity(intent);
            }
        });

        Button ThingsRent=(Button)findViewById(R.id.ThingsRent);
        ThingsRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check="0";
                String thingschoic=spinner.getSelectedItem().toString();
                String thingswhere=spinner1.getSelectedItem().toString();
                String thingswhen=spinner2.getSelectedItem().toString();
                if(thingschoic.equals("물품을 선택해주세요")||thingswhere.equals("대여 장소를 선택해주세요")||thingswhen.equals("대여 기간을 선택해주세요"))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(RentThingsActivity.this);
                    dialog=builder.setMessage("물품, 장소, 기간 모두 선택해주세요")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }else {
                    Intent intent = new Intent(RentThingsActivity.this, PayActivity.class);
                    intent.putExtra("check",check);
                    intent.putExtra("userid", userid);
                    intent.putExtra("thingschoice", spinner.getSelectedItem().toString());
                    intent.putExtra("thingswhere", spinner1.getSelectedItem().toString());
                    intent.putExtra("thingswhen", spinner2.getSelectedItem().toString());
                    intent.putExtra("thingscontent", thingsrequest.getText().toString());
                    RentThingsActivity.this.startActivity(intent);
                }
            }
        });
    }
}
