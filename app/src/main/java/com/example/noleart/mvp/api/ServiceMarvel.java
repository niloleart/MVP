package com.example.noleart.mvp.api;

import android.content.Context;

/**
 * Created by noleart on 3/02/17.
 */

public class ServiceMarvel {

    private static serverApi service = null;

    public static serverApi getService(Context context){
        service = serviceGenerator.createService(serverApi.class, context);
        return service;
    }
}
