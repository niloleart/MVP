package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.entities.Comic;
import com.example.noleart.mvp.control.control.adapters.ComicsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noleart on 9/02/17.
 */

public class ComicFragment extends Fragment implements ComicsContract.View {
    private static final String ARG_CHARACTER = "charId";
    private Context mContext;
    private int characterId;
    private RecyclerView mRecyclerView;
    private ComicsRecyclerViewAdapter adapter;
    private ComicPresenterImpl mPresenter;


    public ComicFragment() {
    }

    public static ComicFragment newInstance(int characterId) {
        ComicFragment fragment = new ComicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHARACTER, characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getInt(ARG_CHARACTER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resource_list, container, false);

        mContext = view.getContext();
        if (view instanceof RecyclerView){
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            List<Comic> entities = new ArrayList<>();
            adapter = new ComicsRecyclerViewAdapter(mContext, entities);
            mRecyclerView.setAdapter(adapter);
            this.mPresenter.getComics(characterId);
        }

        return view;
    }

    @Override
    public void fillData(List<Comic> list) {
        adapter.fillData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showList(boolean show) {

    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ComicsContract.Presenter presenter) {
        this.mPresenter = (ComicPresenterImpl) presenter;
    }
}
