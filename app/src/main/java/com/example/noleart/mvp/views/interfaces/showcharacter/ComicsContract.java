package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Context;
import android.view.View;

import com.example.noleart.mvp.api.entities.Comic;
import com.example.noleart.mvp.views.interfaces.interfaces.BasePresenter;
import com.example.noleart.mvp.views.interfaces.interfaces.BaseView;
import com.example.noleart.mvp.views.interfaces.interfaces.MainContract;

import java.util.List;

/**
 * Created by noleart on 9/02/17.
 */

public interface ComicsContract {
    interface View extends BaseView<Presenter>{
        void fillData(List<Comic> list);
    }

    interface Presenter extends BasePresenter<Context, View>{
        void getComics(int characterId);
    }
}
