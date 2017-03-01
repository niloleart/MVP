package com.example.noleart.mvp.views.interfaces.interfaces;

import android.content.Context;

import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.views.interfaces.interfaces.BasePresenter;
import com.example.noleart.mvp.views.interfaces.interfaces.BaseView;

import java.util.List;

/**
 * Created by noleart on 3/02/17.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void fillData(List<Characters> characters);
    }

    interface Presenter extends BasePresenter<Context,View> {
        void getHeroes(String search);
    }
}
