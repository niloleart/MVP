package com.example.noleart.mvp.control.control.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.entities.Comic;
import com.example.noleart.mvp.api.entities.Event;

import java.util.List;

/**
 * Created by noleart on 10/02/17.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder<Event>> {
    private final Context mContext;
    private List<Event> mValues;

    public EventRecyclerViewAdapter(Context context, List<Event> items) {
        this.mContext = context;
        this.mValues = items;
    }

    @Override
    public CommonViewHolder<Event> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_resource, parent, false);
        return new CommonViewHolder<>(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder<Event> holder, int position) {
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

    public void fillData(List<Event> list){
        mValues = list;
    }
}
