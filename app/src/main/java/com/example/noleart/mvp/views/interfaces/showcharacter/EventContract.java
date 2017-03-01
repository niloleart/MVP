package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Context;

import com.example.noleart.mvp.api.entities.Event;
import com.example.noleart.mvp.views.interfaces.interfaces.BasePresenter;
import com.example.noleart.mvp.views.interfaces.interfaces.BaseView;
import com.example.noleart.mvp.views.interfaces.interfaces.MainContract;

import java.util.List;

/**
 * Created by noleart on 10/02/17.
 */

public interface EventContract {
    interface View extends BaseView<Presenter> {
        void fillData(List<Event> list);
    }

    interface Presenter extends BasePresenter<Context, View> {
        void getEvents(int characterId);
    }
}
