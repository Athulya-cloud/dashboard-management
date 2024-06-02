package com.example.mata_eikonatech;

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

    public RecycleViewAdapterRecentPunches(List<ModelRecentPunchesRecycle> recentPunchesList) {
        this.recentPunchesList = recentPunchesList;
    }

//    public void updateData(List<ModelRecentPunchesRecycle> newPunchesList) {
//        this.recentPunchesList = newPunchesList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_punch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelRecentPunchesRecycle recentPunch = recentPunchesList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(new Date(recentPunch.getTimestamp()));
        holder.timestamp.setText(formattedDate);
//        holder.latitude.setText(recentPunch.getLatitude());
//        holder.longitude.setText(recentPunch.getLongitude());

//        byte[] decodedString = Base64.decode(recentPunch.getImageBase64(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        holder.imageView.setImageBitmap(decodedByte);
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
//            latitude = itemView.findViewById(R.id.latitude);
//            longitude = itemView.findViewById(R.id.longitude);
//            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
