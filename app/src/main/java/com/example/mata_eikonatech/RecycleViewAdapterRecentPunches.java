package com.example.mata_eikonatech;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RecycleViewAdapterRecentPunches extends RecyclerView.Adapter<RecycleViewAdapterRecentPunches.ViewHolder> {

    private List<ModelRecentPunchesRecycle> recentPunchesList;
    private Context context;

    public RecycleViewAdapterRecentPunches(Context context,List<ModelRecentPunchesRecycle> recentPunchesList) {
        this.recentPunchesList = recentPunchesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_punch, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRecentPunchesRecycle recentPunch = recentPunchesList.get(position);
        if (recentPunch != null) {
            String base64String = recentPunch.getImageBase64();
            if (base64String != null) {
                try {
                    byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.imageView.setImageBitmap(decodedByte);
                } catch (IllegalArgumentException e) {
                    Log.e("RecycleViewAdapter", "Base64 decoding failed for position " + position, e);
                }
            } else {
                Log.w("RecycleViewAdapter", "base64String is null for position " + position);
                holder.imageView.setImageResource(R.drawable.image_placeholder); // Ensure you have a placeholder image in your resources
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedDate = sdf.format(new Date(recentPunch.getTimestamp()));
            holder.timestamp.setText(formattedDate);
            holder.latitude.setText(String.valueOf(recentPunch.getLatitude()));
            holder.longitude.setText(String.valueOf(recentPunch.getLongitude()));
        } else {
            Log.w("RecycleViewAdapter", "Punch is null for position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return recentPunchesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timestamp, latitude, longitude;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
