package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterReviewActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayAdapter adapter;
    private Spinner spinner1;
    private ArrayAdapter adapter1;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_review);

        Intent intent=getIntent();
        final String userid = intent.getStringExtra("userid");

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
                Intent intent=new Intent(RegisterReviewActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        spinner=(Spinner)findViewById(R.id.mobilityname);
        adapter= ArrayAdapter.createFromResource(this,R.array.mobility,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner1=(Spinner)findViewById(R.id.mobilityscore);
        adapter1= ArrayAdapter.createFromResource(this,R.array.score,android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final EditText reviewcontent=(EditText)findViewById(R.id.reviewcontent);

        Button registerreview=(Button)findViewById(R.id.RegisterReview);
        registerreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=userid;
                String mobilityName=spinner.getSelectedItem().toString();
                String mobilityScore=spinner1.getSelectedItem().toString();
                String reviewContent=reviewcontent.getText().toString();
                if(reviewContent.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterReviewActivity.this);
                    dialog=builder.setMessage("리뷰를 작성해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(mobilityName.equals("모빌리티를 선택해주세요")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterReviewActivity.this);
                    dialog=builder.setMessage("모빌리티를 선택해주세요")
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterReviewActivity.this);
                                dialog=builder.setMessage("리뷰 등록에 성공했습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterReviewActivity.this);
                                dialog=builder.setMessage("리뷰 등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                RegisterReviewRequest registerRequest=new RegisterReviewRequest(userID,mobilityName,mobilityScore,reviewContent,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterReviewActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
