package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

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
                Intent intent=new Intent(GoogleMapActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    //35.83617, 128.7529 영남대학교 정문
    //35.83066, 128.7544 아이티관
    //35.82996, 128.7543 전기관
    //35.82942, 128.7544 섬유관
    //35.82898, 128.7543 화공관
    //35.83316, 128.7580 중앙도서관
    //35.82892, 128.7569 이과도서관
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng frontdoor=new LatLng(35.83617, 128.7529);
        googleMap.addMarker(new MarkerOptions().position(frontdoor).title("영남대학교").snippet("정문"));

        LatLng IT=new LatLng(35.83066, 128.7544);
        googleMap.addMarker(new MarkerOptions().position(IT).title("IT관"));

        LatLng electronic=new LatLng(35.82996, 128.7543);
        googleMap.addMarker(new MarkerOptions().position(electronic).title("전기관"));

        LatLng cloth=new LatLng(35.82942, 128.7544);
        googleMap.addMarker(new MarkerOptions().position(cloth).title("섬유관"));

        LatLng chemical=new LatLng(35.82898, 128.7543);
        googleMap.addMarker(new MarkerOptions().position(chemical).title("화공관"));

        LatLng centallibrary=new LatLng(35.83316, 128.7580);
        googleMap.addMarker(new MarkerOptions().position(centallibrary).title("중앙도서관"));

        LatLng sciencelibrary=new LatLng(35.82892, 128.7569);
        googleMap.addMarker(new MarkerOptions().position(sciencelibrary).title("이과도서관"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(frontdoor,15));

    }
}
