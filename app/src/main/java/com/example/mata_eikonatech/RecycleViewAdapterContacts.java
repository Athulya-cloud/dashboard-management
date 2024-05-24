package com.example.mata_eikonatech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapterContacts extends RecyclerView.Adapter <RecycleViewAdapterContacts.ViewHolder> {

    @NonNull
    @Override
    public RecycleViewAdapterContacts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactsrecycle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterContacts.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,number,profileletter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
