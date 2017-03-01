package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Context;

import com.example.noleart.mvp.api.SendRequest;
import com.example.noleart.mvp.api.ServiceMarvel;
import com.example.noleart.mvp.api.entities.BaseResponse;
import com.example.noleart.mvp.api.entities.Event;
import com.example.noleart.mvp.api.serverApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by noleart on 10/02/17.
 */
public class EventPresenterImpl implements EventContract.Presenter {
    private Context mContext;
    private EventContract.View mView;

    @Override
    public void getEvents(int characterId) {
        serverApi server = ServiceMarvel.getService(mContext);
        final SendRequest request = SendRequest.create();
        Call<BaseResponse<Event>> call =
                server.getEventsByCharacter(characterId,
                        String.valueOf(request.getTimeStamp()),
                        request.getPublicKey(),
                        request.getHashSignature()
                );
        call.enqueue(new Callback<BaseResponse<Event>>() {
            @Override
            public void onResponse(Call<BaseResponse<Event>> call, Response<BaseResponse<Event>> response) {
                mView.showProgressBar(false);
                if (response.isSuccessful()){
                    if (response.body() != null && response.body().data.results.size() > 0){
                        mView.showList(true);
                        mView.fillData(response.body().data.results);
                    }
                }
                else mView.showMessage("Sense Resultats");
            }

            @Override
            public void onFailure(Call<BaseResponse<Event>> call, Throwable t) {
                mView.showProgressBar(false);
                mView.showMessage("Error al recuperar les dades");
            }
        });
    }

    @Override
    public void attach(Context context, EventContract.View view) {
        this.mContext = context;
        this.mView = view;
        view.setPresenter(this);
    }
}
