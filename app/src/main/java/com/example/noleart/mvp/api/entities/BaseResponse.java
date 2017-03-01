package com.example.noleart.mvp.api.entities;

/**
 * Created by noleart on 3/02/17.
 */

public class BaseResponse<T> {
    public int code;
    public String status;
    public String etag;
    public DataResponse<T> data;
}
