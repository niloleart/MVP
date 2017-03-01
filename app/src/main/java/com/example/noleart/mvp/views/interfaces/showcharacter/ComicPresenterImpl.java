package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Context;
import android.view.View;

import com.example.noleart.mvp.api.SendRequest;
import com.example.noleart.mvp.api.ServiceMarvel;
import com.example.noleart.mvp.api.entities.BaseResponse;
import com.example.noleart.mvp.api.entities.Comic;
import com.example.noleart.mvp.api.serverApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noleart on 9/02/17.
 */

public class ComicPresenterImpl implements ComicsContract.Presenter {
    private ComicsContract.View mView;
    private Context mContex;


    @Override
    public void getComics(int characterId) {
        serverApi server = ServiceMarvel.getService(mContex);
        final SendRequest request = SendRequest.create();
        Call<BaseResponse<Comic>> call =
                server.getComicsByCharacter(characterId,
                        String.valueOf(request.getTimeStamp()),
                        request.getPublicKey(),
                        request.getHashSignature());
        call.enqueue(new Callback<BaseResponse<Comic>>() {
            @Override
            public void onResponse(Call<BaseResponse<Comic>> call, Response<BaseResponse<Comic>> response) {
                mView.showProgressBar(false);
                if (response.isSuccessful()){
                    if (response.body()!=null && response.body().data.results.size()>0){
                        mView.showList(true);
                        mView.fillData(response.body().data.results);
                    }
                    else mView.showMessage("Sense Resultats");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Comic>> call, Throwable t) {
                mView.showProgressBar(false);
                mView.showMessage("Error al recuperar les dades");
            }
        });

    }

    @Override
    public void attach(Context context, ComicsContract.View view) {
        this.mContex = context;
        this.mView = view;
        mView.setPresenter(this);
    }

}
