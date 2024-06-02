package com.example.mata_eikonatech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Notifications extends AppCompatActivity {
    private String authToken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        authToken = getIntent().getStringExtra("AUTH_TOKEN");
        if (authToken == null || authToken.isEmpty()) {
            Log.e("Notifications", "Authentication token is missing");
        } else {
            Log.d("Notifications", "Authentication token received: " + authToken);
        }

        ImageView backarrow = findViewById(R.id.backarrow);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notifications.this,Dashboard.class );

                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
                finish();
            }
        });
    }
}
