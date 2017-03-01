package com.example.noleart.mvp.api;

import com.example.noleart.mvp.api.entities.BaseResponse;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.api.entities.Comic;
import com.example.noleart.mvp.api.entities.Event;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by noleart on 3/02/17.
 */

public interface serverApi {
    @GET("/v1/public/characters")
    Call<BaseResponse<Characters>> getCharactersByStart(@Query("nameStartsWith") String nameStartsWith
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature);

    @GET("/v1/public/characters/{characterId}/comics")
    Call<BaseResponse<Comic>> getComicsByCharacter(@Path("characterId") int characterId
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature);

    @GET("/v1/public/characters/{characterId}/events")
    Call<BaseResponse<Event>> getEventsByCharacter(@Path("characterId") int characterId
            , @Query("ts") String timestamp
            , @Query("apikey") String apikey
            , @Query("hash") String hashSignature);
}
