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

        // Uncomment to use try-catch block
        // try {
        //     recentPunchesList = loadRecentPunches();
        //     if (recentPunchesList == null) {
        //         recentPunchesList = new ArrayList<>();
        //         Log.d("RecentPunches", "No recent punches found, initializing empty list");
        //     }
        //     adapter = new RecycleViewAdapterRecentPunches(recentPunchesList);
        //     recyclerView.setAdapter(adapter);
        // } catch (Exception e) {
        //     Log.e("RecentPunches", "Error loading recent punches: " + e.getMessage(), e);
        // }

        recentPunchesList = loadRecentPunches();
        adapter = new RecycleViewAdapterRecentPunches(recentPunchesList);
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
        Type type = new TypeToken<List<ModelRecentPunchesRecycle>>() {}.getType();
        List<ModelRecentPunchesRecycle> punches = new Gson().fromJson(json, type);
        return punches != null ? punches : new ArrayList<>();

        // Uncomment to use alternative loading method
        // if (json.isEmpty()) {
        //     Log.d("RecentPunches", "No data found in SharedPreferences for recent punches");
        //     return new ArrayList<>();
        // }
        // Gson gson = new Gson();
        // Type type = new TypeToken<List<ModelRecentPunchesRecycle>>() {}.getType();
        // return gson.fromJson(json, type);
    }

    // Uncomment to use onResume method
    // @Override
    // protected void onResume() {
    //     super.onResume();
    //     recentPunchesList = loadRecentPunches();
    //     adapter.updateData(recentPunchesList);
    // }
}
