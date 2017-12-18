package com.servustech.alex.servustech.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.servustech.alex.servustech.R;
import com.servustech.alex.servustech.model.category.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Category> mDataSet;

    public CustomAdapter(List<Category> dataSet) {
        mDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycle_view_item, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(mDataSet.get(position).getId());
        holder.name.setText(mDataSet.get(position).getName());

        if (mDataSet.get(position).getIcon() != null) {
            Picasso.with(holder.name.getContext())
                    .load(mDataSet.get(position).getIcon().getImageURL())
                    .error(R.drawable.ic_broken_image_black_48dp)
                    .into(holder.icon);
        } else {
            holder.icon.setImageResource(R.drawable.ic_broken_image_black_48dp);
        }

    }





    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void replaceAll(List<Category> results) {
        mDataSet.clear();
        mDataSet.addAll(results);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_id)
        TextView id;
        @BindView(R.id.tv_category_name)
        TextView name;
        @BindView(R.id.iv_category_icon)
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}