package com.example.noleart.mvp.views.interfaces.interfaces;

/**
 * Created by noleart on 3/02/17.
 */

public interface BaseView<T> {
    void showList(boolean show);
    void showProgressBar(boolean show);
    void showMessage(String message);
    void setPresenter(T presenter);
}
