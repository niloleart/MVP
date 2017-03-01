package com.example.noleart.mvp;

import android.content.Context;

import com.example.noleart.mvp.api.SendRequest;
import com.example.noleart.mvp.api.ServiceMarvel;
import com.example.noleart.mvp.api.entities.BaseResponse;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.views.interfaces.interfaces.MainContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noleart on 2/02/17.
 */

public class MainPresenterImpl implements MainContract.Presenter {
    private MainContract.View mView;
    private Context mContext;
    private ServiceMarvel mService = null;

    public MainPresenterImpl() {

    }

    @Override
    public void getHeroes(String search) {
        mView.showProgressBar(true);
        mView.showList(false);

        final SendRequest request = SendRequest.create();
        Call<BaseResponse<Characters>> call = mService.getService(mContext).getCharactersByStart(search, "1", request.getPublicKey(), request.getHashSignature());
        call.enqueue(new Callback<BaseResponse<Characters>>() {
            @Override
            public void onResponse(Call<BaseResponse<Characters>> call, Response<BaseResponse<Characters>> response) {
                mView.showProgressBar(false);
                if (response.isSuccessful()) {
                    if (response.body().data != null && response.body().data.results.size() > 0) {
                        mView.showList(true);
                        mView.fillData(response.body().data.results);
                    } else {
                        mView.showMessage("Sense resultats");
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Characters>> call, Throwable t) {
                mView.showProgressBar(false);
                mView.showMessage("Error al recuperar les dades");

            }
        });

    }

    @Override
    public void attach(Context context, MainContract.View view) {
        this.mContext = context;
        this.mView = view;

    }
}
