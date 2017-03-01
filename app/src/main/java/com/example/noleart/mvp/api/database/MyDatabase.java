package com.example.noleart.mvp.api.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by noleart on 15/02/17.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";
    public static final int VERSION = 1;
}
