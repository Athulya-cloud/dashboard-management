package com.example.mata_eikonatech;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Attendance extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);

        ImageView backarrow = findViewById(R.id.backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Attendance.this,Dashboard.class );
                startActivity(intent);
            }
        });
    }


}
