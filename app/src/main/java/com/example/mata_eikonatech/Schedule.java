package com.example.mata_eikonatech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Schedule extends AppCompatActivity {
    private String authToken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        authToken = getIntent().getStringExtra("AUTH_TOKEN");
        if (authToken == null || authToken.isEmpty()) {
            Log.e("schedule", "Authentication token is missing");
        } else {
            Log.d("schedule", "Authentication token received: " + authToken);
        }

        ImageView backarrow = findViewById(R.id.backarrow);
        CalendarView calendar = findViewById(R.id.calendar);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Schedule.this,Dashboard.class );

                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
                finish();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

               String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
            }
        });
    }
}
