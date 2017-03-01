package com.example.noleart.mvp.control.control.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.entities.Comic;

import java.util.List;

/**
 * Created by noleart on 9/02/17.
 */

public class ComicsRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder<Comic>> {
    private final Context mContext;
    private List<Comic> mValues;

    public ComicsRecyclerViewAdapter(Context context, List<Comic> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @Override
    public CommonViewHolder<Comic> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_resource, parent, false);
        return new CommonViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder<Comic> holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getTitle());
        holder.description.setText(mValues.get(position).getDescription());
        String urlImage = mValues.get(position).getThumbnail().getPath() + "." + mValues.get(position).getThumbnail().getExtension();
        Glide.with(mContext)
                .load(urlImage)
                .into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void fillData(List<Comic> list){
        mValues = list;
    }
}
