package com.example.noleart.mvp.control.control.adapters;

/**
 * Created by noleart on 8/02/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.control.control.callbacks.CharacterListCallback;

import java.util.List;

public class CharactersRecyclerViewAdapter extends RecyclerView.Adapter<CommonViewHolder<Characters>> {
    private List<Characters> mValues;
    private final Context mContext;
    private final CharacterListCallback mListener;

    public CharactersRecyclerViewAdapter(List<Characters> mValues, Context mContext, CharacterListCallback listener) {
        this.mValues = mValues;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public CommonViewHolder<Characters> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_resource, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder<Characters> holder, final int position) {
        holder.name.setText(mValues.get(position).getName());
        holder.description.setText(mValues.get(position).getDescription());
        String urlImage = mValues.get(position).getThumbnail().getPath() + "." + mValues.get(position).getThumbnail().getExtension();

        Glide.with(mContext)
                .load(urlImage)
                .centerCrop()
                .into(holder.avatar);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickCharacter(mValues.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void fillData(List<Characters> characters) {
        mValues = characters;
    }
}
