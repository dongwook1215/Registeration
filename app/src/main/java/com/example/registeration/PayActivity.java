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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class PayActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayAdapter adapter;
    private AlertDialog dialog;
    private boolean validate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent=getIntent();
        final String check=intent.getStringExtra("check");
        final String userid = intent.getStringExtra("userid");
        final String ThingsChoice=intent.getStringExtra("thingschoice");
        final String ThingsWhere=intent.getStringExtra("thingswhere");
        final String ThingsWhen=intent.getStringExtra("thingswhen");
        final String ThingsContent=intent.getStringExtra("thingscontent");

        final TextView thingsname=(TextView)findViewById(R.id.thingsname);
        final TextView thingswhere=(TextView)findViewById(R.id.thingswhere);
        final TextView thingswhen=(TextView)findViewById(R.id.thingswhen);
        final TextView thingscontent=(TextView)findViewById(R.id.thingscontent);
        thingsname.setText(ThingsChoice);
        thingswhere.setText(ThingsWhere);
        thingswhen.setText(ThingsWhen);
        thingscontent.setText(ThingsContent);

        spinner=(Spinner)findViewById(R.id.bank);
        adapter= ArrayAdapter.createFromResource(this,R.array.bank,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView money=(TextView)findViewById(R.id.thingsmoney);

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
                Intent intent=new Intent(PayActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        if(thingswhen.getText().toString().equals("1 months")){
            money.setText("10000원");
        }
        if(thingswhen.getText().toString().equals("2 months")){
            money.setText("20000원");
        }
        if(thingswhen.getText().toString().equals("3 months")){
            money.setText("30000원");
        }
        if(thingswhen.getText().toString().equals("4 months")){
            money.setText("40000원");
        }
        if(thingswhen.getText().toString().equals("1days")){
            money.setText("10000원");
        }
        if(thingswhen.getText().toString().equals("7days")){
            money.setText("70000원");
        }
        if(thingswhen.getText().toString().equals("1months")){
            money.setText("30만원");
        }
        if(thingswhen.getText().toString().equals("2months")){
            money.setText("60만원");
        }

        Button paybutton=(Button)findViewById(R.id.paybutton);
        final EditText banknumber=(EditText)findViewById(R.id.banknumber);
        final EditText bankpassword=(EditText)findViewById(R.id.bankpassword);
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bankName=spinner.getSelectedItem().toString();
                final String bankNumber=banknumber.getText().toString();
                final String bankPassword=bankpassword.getText().toString();
                final String thingsName=thingsname.getText().toString();
                final String thingsWhere=thingswhere.getText().toString();
                final String thingsWhen=thingswhen.getText().toString();
                final String thingsContent=thingscontent.getText().toString();
                if(validate)
                {
                    return;
                }
                if(bankNumber.equals("")||bankPassword.equals(""))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                    dialog=builder.setMessage("카드번호 또는 비밀번호가 입력되지 않았습니다")
                            .setPositiveButton("확인",null)
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                                dialog=builder.setMessage("사용 불가능한 카드입니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                if(check.equals("0")){
                                    Rent(userid,thingsName,thingsWhere,thingsWhen,thingsContent,bankName,bankNumber,bankPassword);
                                }
                                if(check.equals("1")){
                                    MobilityRent(userid,thingsName,thingsWhere,thingsWhen,thingsContent,bankName,bankNumber,bankPassword);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                PayValidateRequest validateRequest=new PayValidateRequest(bankName,bankNumber,bankPassword,responseListener);
                RequestQueue queue= Volley.newRequestQueue(PayActivity.this);
                queue.add(validateRequest);
            }
        });
    }
    public void Rent(String userID,String thingsName,String thingsWhere, String thingsWhen,String thingsContent,String bankName,String bankNumber, String bankPassword){
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse=new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    if(success)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                        dialog=builder.setMessage("물품 대여에 성공했습니다.")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                        finish();
                    }
                    else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                        dialog=builder.setMessage("물품 대여에 실패했습니다.")
                                .setNegativeButton("확인",null)
                                .create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        RentThingsRequest registerRequest=new RentThingsRequest(userID,thingsName,thingsWhere,thingsWhen,thingsContent,bankName,bankNumber,bankPassword,responseListener);
        RequestQueue queue= Volley.newRequestQueue(PayActivity.this);
        queue.add(registerRequest);
    }
    public void MobilityRent(String userID,String thingsName,String thingsWhere, String thingsWhen,String thingsContent,String bankName,String bankNumber, String bankPassword){
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse=new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    if(success)
                    {
                        AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                        dialog=builder.setMessage("모빌리티 대여에 성공했습니다.")
                                .setPositiveButton("확인",null)
                                .create();
                        dialog.show();
                        finish();
                    }
                    else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(PayActivity.this);
                        dialog=builder.setMessage("모빌리티 대여에 실패했습니다.")
                                .setNegativeButton("확인",null)
                                .create();
                        dialog.show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        RentMobilityRequest registerRequest=new RentMobilityRequest(userID,thingsName,thingsWhere,thingsWhen,thingsContent,bankName,bankNumber,bankPassword,responseListener);
        RequestQueue queue= Volley.newRequestQueue(PayActivity.this);
        queue.add(registerRequest);
    }
}
