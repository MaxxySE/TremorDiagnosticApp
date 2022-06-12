package com.example.tremordiagnosticapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tremordiagnosticapp.entity.CustomResultListItem;

import java.util.ArrayList;

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.CustomViewHolder> {
    private ArrayList<CustomResultListItem> customResultListItems;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView id, username, date, result;
        public Button button;

        public CustomViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            init(itemView, listener);
        }

        public void init(View itemView, final OnItemClickListener listener){
            id = itemView.findViewById(R.id.id);
            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            result = itemView.findViewById(R.id.result);
            button = itemView.findViewById(R.id.removeElementBtn);

            getPosition(button, listener);
        }

        public void getPosition(View v, final OnItemClickListener listener){
            v.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public CustomViewAdapter(ArrayList<CustomResultListItem> customResultListItems){
        this.customResultListItems = customResultListItems;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, parent, false);
        return new CustomViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        CustomResultListItem currentItem = customResultListItems.get(position);
        setDataToFields(holder, currentItem);
    }

    public void setDataToFields(@NonNull CustomViewHolder holder, CustomResultListItem currentItem){
        holder.id.setText(currentItem.getId());
        holder.username.setText(currentItem.getUsername());
        holder.date.setText(currentItem.getDate());
        holder.result.setText(currentItem.getResult());
    }

    @Override
    public int getItemCount() {
        return customResultListItems.size();
    }
}
