package com.example.mata_eikonatech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecentPunches extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecycleViewAdapterRecentPunches adapter;
    private List<ModelRecentPunchesRecycle> recentPunchesList;
    private String authToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recentpunches);
        Log.d("RecentPunches", "Activity created");
        authToken = getIntent().getStringExtra("AUTH_TOKEN");
        boolean refreshRequired = getIntent().getBooleanExtra("REFRESH_REQUIRED", false);
        if (authToken == null || authToken.isEmpty()) {
            Log.e("RecentPunches", "Authentication token is missing");
        } else {
            Log.d("RecentPunches", "Authentication token received: " + authToken);
        }
        recyclerView = findViewById(R.id.recyclerViewRecentPunches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentPunchesList = loadRecentPunches();
        adapter = new RecycleViewAdapterRecentPunches(this, recentPunchesList);
        recyclerView.setAdapter(adapter);
        ImageView backarrow = findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentPunches.this, Dashboard.class);
                intent.putExtra("AUTH_TOKEN", authToken);
                intent.putExtra("REFRESH_REQUIRED", true);
                startActivity(intent);
                finish();
            }
        });
    }

    private List<ModelRecentPunchesRecycle> loadRecentPunches() {
        SharedPreferences sharedPreferences = getSharedPreferences("recent_punches", MODE_PRIVATE);
        String json = sharedPreferences.getString("punch_list", "");
        Type type = new TypeToken<List<ModelRecentPunchesRecycle>>() {
        }.getType();
        List<ModelRecentPunchesRecycle> punches = new Gson().fromJson(json, type);
        if (punches == null) {
            punches = new ArrayList<>();
        }
        Log.d("RecentPunches", "Retrieved punches: " + punches);
        return punches != null ? punches : new ArrayList<>();
    }
}
