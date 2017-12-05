package com.example.alex.servustech.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.servustech.R;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<String> mDataSet;

    public CustomAdapter(List<String> dataSet) {
        mDataSet = dataSet;
    }


    // Called when a new view when the layout manager requests it
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycle_view_item, parent, false)
        );
    }


    // Replaces the content of a view.
    // For instance, if a view would have an image and 2 textviews, here we'd update the data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextView().setText(mDataSet.get(position));
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView; // We currently only have a textview
        // If we were to have multiple items, here they'd go.

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_recycle_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}