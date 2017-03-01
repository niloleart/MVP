package com.example.noleart.mvp.views.interfaces.interfaces;

/**
 * Created by noleart on 3/02/17.
 */

public interface BasePresenter<T,V> {
    void attach(T context, V view);
}
