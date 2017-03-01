package com.example.noleart.mvp.control.control.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noleart.mvp.R;

/**
 * Created by noleart on 8/02/17.
 */

public class CommonViewHolder<T> extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView name;
    public final TextView description;
    public final ImageView avatar;

    public T mItem;

    public CommonViewHolder(View view) {
        super(view);
        mView = view;
        name = (TextView) view.findViewById(R.id.hero_name);
        description = (TextView) view.findViewById(R.id.hero_description);
        avatar = (ImageView) view.findViewById(R.id.hero_avatar);

    }

    public String toString(){
        return super.toString() + " '" + name.getText() + "'";
    }
}
