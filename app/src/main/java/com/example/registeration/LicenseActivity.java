package com.example.registeration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LicenseActivity extends AppCompatActivity {

    public boolean validate=false;
    private AlertDialog dialog;
    private String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        requirePermisson();

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
                Intent intent=new Intent(LicenseActivity.this,LoginActivity.class);
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
                Intent intent=new Intent(LicenseActivity.this,RentThingsActivity.class);
                intent.putExtra("userid",userid);
                LicenseActivity.this.startActivity(intent);
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
                                AlertDialog.Builder builder=new AlertDialog.Builder(LicenseActivity.this);
                                dialog=builder.setMessage("License를 등록한 후 모빌리티 대여가 가능합니다")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                            else{
                                Intent intent=new Intent(LicenseActivity.this,RentMobilityActivity.class);
                                intent.putExtra("userid",userid);
                                LicenseActivity.this.startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LicenseValidateRequest validateRequest=new LicenseValidateRequest(userid,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LicenseActivity.this);
                queue.add(validateRequest);
            }
        });
        ImageButton userpage=(ImageButton)findViewById(R.id.userpage);
        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LicenseActivity.this,MyPageActivity.class);
                intent.putExtra("userid",userid);
                LicenseActivity.this.startActivity(intent);
            }
        });
        ImageButton QnA=(ImageButton)findViewById(R.id.Qna);
        QnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LicenseActivity.this, QnAActivity.class);
                intent.putExtra("userid",userid);
                LicenseActivity.this.startActivity(intent);
            }
        });

        //카메라 관련 코드
        ImageButton camera_button=(ImageButton)findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean camera = ContextCompat.checkSelfPermission
                        (view.getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean write = ContextCompat.checkSelfPermission
                        (view.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if(camera && write){
                    //사진 찍은 인텐트 코드
                    takePicture();
                    validate=true;
                }else {
                    Toast.makeText(LicenseActivity.this,"카메라 권한을 주지 않았습니다",Toast.LENGTH_LONG).show();
                }
            }
        });
        Button licenseRegister =(Button)findViewById(R.id.licenseRegister);
        licenseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate==true)
                {
                    Response.Listener<String> responseListener=new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse=new JSONObject(response);
                                boolean success=jsonResponse.getBoolean("success");
                                if(success)
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(LicenseActivity.this);
                                    dialog=builder.setMessage("License 등록에 성공했습니다.")
                                            .setPositiveButton("확인",null)
                                            .create();
                                    dialog.show();
                                    finish();
                                }
                                else{
                                    AlertDialog.Builder builder=new AlertDialog.Builder(LicenseActivity.this);
                                    dialog=builder.setMessage("License 등록에 실패했습니다.")
                                            .setNegativeButton("확인",null)
                                            .create();
                                    dialog.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    LicenseRegisterRequest registerRequest=new LicenseRegisterRequest(userid,responseListener);
                    RequestQueue queue= Volley.newRequestQueue(LicenseActivity.this);
                    queue.add(registerRequest);
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(LicenseActivity.this);
                    dialog=builder.setMessage("사진을 찍어주세요")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
            }
        });
    }
    void requirePermisson(){
        String[] permissons = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ArrayList<String> ListPermissonsNeeded=new ArrayList<>();

        for(String permisson : permissons) {
            if(ContextCompat.checkSelfPermission(this,permisson) == PackageManager.PERMISSION_DENIED)
            {
                //권한이 허가가 안됬을 경우 요청할 권한을 모집하는 부분
                ListPermissonsNeeded.add(permisson);
            }
        }
        if(!ListPermissonsNeeded.isEmpty()){
            //권한 요청하는 부분
            ActivityCompat.requestPermissions(this,ListPermissonsNeeded.toArray(new String[ListPermissonsNeeded.size()]),1);
        }
    }
    void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            File photofile = createImageFile();
            Uri photoUri = FileProvider.getUriForFile(this,"com.example.registeration.fileprovider",photofile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
            startActivityForResult(intent,10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 10)
        {
            ImageView imageView=(ImageView)findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
            validate=true;
        }
    }
}
