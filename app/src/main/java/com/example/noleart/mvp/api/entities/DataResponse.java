package com.example.noleart.mvp.api.entities;

import java.util.List;

/**
 * Created by noleart on 3/02/17.
 */
public class DataResponse<T> {
    public int offset;
    public int limit;
    public int total;
    public int count;
    public List<T> results;
}
