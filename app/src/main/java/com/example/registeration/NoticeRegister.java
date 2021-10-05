package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NoticeRegister extends AppCompatActivity {
    private boolean validate=false;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_register);
        final EditText notice_date=(EditText)findViewById(R.id.notice_date);
        final EditText notice_name=(EditText)findViewById(R.id.notice_name);
        final EditText notice_content=(EditText)findViewById(R.id.notice_content);
        Button notice_Register=(Button)findViewById(R.id.notice_Register);
        final Button notice_Validate=(Button)findViewById(R.id.noticeValidateButton);

        ImageButton logout=(ImageButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NoticeRegister.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ImageButton finish=(ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        notice_Validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noticeName=notice_name.getText().toString();
                if(validate)
                {
                    return;
                }
                char check;
                boolean checknumber=true;
                for(int i = 0; i<noticeName.length(); i++){
                    check = noticeName.charAt(i);
                    if( check < 48 || check > 58)
                    {
                        checknumber=false;
                    }
                }
                if(noticeName.equals(""))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(NoticeRegister.this);
                    dialog=builder.setMessage("공지번호가 입력되지 않았습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(checknumber==true) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeRegister.this);
                                    dialog = builder.setMessage("사용가능한 공지번호입니다")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    notice_name.setEnabled(false);
                                    validate = true;
                                    notice_name.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                    notice_Validate.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeRegister.this);
                                    dialog = builder.setMessage("사용할수 없는 공지번호입니다")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    NoticeValidateRequest noticevalidateRequest = new NoticeValidateRequest(noticeName, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(NoticeRegister.this);
                    queue.add(noticevalidateRequest);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeRegister.this);
                    dialog = builder.setMessage("공지번호는 숫자만을 입력해주세요!!")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        notice_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noticeContent=notice_content.getText().toString();
                String noticeName=notice_name.getText().toString();
                String noticeDate=notice_date.getText().toString();
                if(!validate)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(NoticeRegister.this);
                    dialog=builder.setMessage("공지번호 중복체크를 하지 않았습니다.")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(noticeContent.equals("")||noticeName.equals("")||noticeDate.equals(""))
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(NoticeRegister.this);
                    dialog=builder.setMessage("빈칸 없이 입력해주세요!!")
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(NoticeRegister.this);
                                dialog=builder.setMessage("공지 등록에 성공했습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(NoticeRegister.this);
                                dialog=builder.setMessage("공지 등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                NoticeRegisterRequest noticeRequest=new NoticeRegisterRequest(noticeContent,noticeName,noticeDate,responseListener);
                RequestQueue queue= Volley.newRequestQueue(NoticeRegister.this);
                queue.add(noticeRequest);
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
