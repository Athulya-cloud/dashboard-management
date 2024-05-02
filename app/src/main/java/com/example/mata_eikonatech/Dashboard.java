package com.example.mata_eikonatech;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Base64;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import androidx.core.content.ContextCompat;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.GoogleMap;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;

import android.Manifest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.example.mata_eikonatech.R;
import java.io.ByteArrayOutputStream;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.Uri;

public class Dashboard extends AppCompatActivity {
    DrawerLayout drawerlayout;
    ImageView menu;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;

    private GoogleMap myMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        drawerlayout = findViewById(R.id.drawerlayout);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerlayout);
            }
        });
        ImageButton imageButtonMarkAttendance = findViewById(R.id.imageButtonMarkAttendance);
        imageButtonMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    openCamera();
                } else {
                    requestPermission();
                }
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerlayout) {
        drawerlayout.openDrawer(GravityCompat.START);
        Log.d("success","Drawer Opened");
    }

    public static void closeDrawer(DrawerLayout drawerlayout) {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerlayout);
    }

    //ImageButton btnNavigateToMenu = findViewById(R.id.btnNavigateToMenu);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        btnNavigateToMenu.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Dashboard.this, Menu.class);
//                startActivity(intent);
//            }

    //});

//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        myMap = googleMap;
//        if (myMap != null) {
//            LatLng sydney = new LatLng(-34, 151);
//            myMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
//            myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        } else {
//            Toast.makeText(this, "Error:Map initialization failed", Toast.LENGTH_SHORT).show();
//        }
//    }

    private boolean checkPermission(){
        return ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if(extras != null && extras.containsKey("data")){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if(imageBitmap != null){
                    String base64Image = encodeImageToBase64(imageBitmap);
                }
            }
        }
    }

    private String encodeImageToBase64(Bitmap imageBitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] byteArrayImage = baos.toByteArray();
        Log.d("success","image converted");
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

    }
}
