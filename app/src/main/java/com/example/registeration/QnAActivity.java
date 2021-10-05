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

public class QnAActivity extends AppCompatActivity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");

        spinner=(Spinner)findViewById(R.id.QnA_kind);
        adapter= ArrayAdapter.createFromResource(this,R.array.QnA_kind,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText QnA_content=(EditText)findViewById(R.id.QnA_content);
        final EditText QnA_Email=(EditText)findViewById(R.id.QnA_Email);
        final EditText QnA_date=(EditText)findViewById(R.id.QnA_date);
        Button QnA_Register=(Button)findViewById(R.id.QnA_Register);

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QnAActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        QnA_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionContent=QnA_content.getText().toString();
                String questionEmail=QnA_Email.getText().toString();
                String questionDate=QnA_date.getText().toString();
                if(questionContent.equals("")||questionEmail.equals("")||questionDate.equals(""))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(QnAActivity.this);
                    dialog=builder.setMessage("빈칸 없이 입력해주세요!!")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(spinner.getSelectedItem().toString().equals("문의 종류를 선택해주세요"))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(QnAActivity.this);
                    dialog=builder.setMessage("문의 종류를 선택해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(QnAActivity.this);
                                dialog=builder.setMessage("문의 등록에 성공했습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(QnAActivity.this);
                                dialog=builder.setMessage("문의 등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                QnARequest qnaRequest=new QnARequest(questionContent,questionEmail,questionDate,responseListener);
                RequestQueue queue= Volley.newRequestQueue(QnAActivity.this);
                queue.add(qnaRequest);
            }
        });

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QnAActivity.this,NoticeActivity.class);
                intent.putExtra("userid",userid);
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
                Intent intent=new Intent(QnAActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                QnAActivity.this.startActivity(intent);
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(QnAActivity.this);
                                dialog=builder.setMessage("License를 등록한 후 모빌리티 대여가 가능합니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                Intent intent=new Intent(QnAActivity.this,RentMobilityActivity.class);
                                intent.putExtra("userid",userid);
                                QnAActivity.this.startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(userid,responseListener);
                RequestQueue queue= Volley.newRequestQueue(QnAActivity.this);
                queue.add(validateRequest);
            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QnAActivity.this,MyPageActivity.class);
                intent.putExtra("userid",userid);
                QnAActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QnAActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                QnAActivity.this.startActivity(intent);
            }
        });

    }
    protected void onStop(){
        super.onStop();
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog=null;
        }
    }
}
