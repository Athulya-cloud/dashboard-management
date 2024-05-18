package com.example.mata_eikonatech;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        //TextView logout = findViewById(R.id.logout);
        //TextView attendance=findViewById(R.id.attendance);
        //TextView schedule=findViewById(R.id.schedule);
        //setUpMenu(this, logout);
        //setUpSchedule(this,schedule);
        //setUpAttendance(this, attendance);
    }

//    public void setUpMenu(final Context context, TextView logout) {
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(intent);
//                Log.d("success","LOGOUT");
//            }
//        });
//    }
//
//    public void setUpAttendance(final Context context,TextView attendance){
//        attendance.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Attendance.class);
//                context.startActivity(intent);
//                Log.d("success","ATTENDANCE ACTIVITY OPENED");
//            }
//        });
//    }
//
//    public void setUpSchedule(final Context context, TextView schedule){
//        schedule.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Schedule.class);
//                context.startActivity(intent);
//                Log.d("success","SCHEDULE ACTIVITY OPENED");
//            }
//        });
//    }








}
