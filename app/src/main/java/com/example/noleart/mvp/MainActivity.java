package com.example.noleart.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.noleart.mvp.api.database.Favourite;
import com.example.noleart.mvp.api.database.Favourite_Table;
import com.example.noleart.mvp.api.database.MyDatabase;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.control.control.adapters.CharactersRecyclerViewAdapter;
import com.example.noleart.mvp.control.control.callbacks.CharacterListCallback;
import com.example.noleart.mvp.views.interfaces.BaseActivity;
import com.example.noleart.mvp.views.interfaces.interfaces.MainContract;
import com.example.noleart.mvp.views.interfaces.showcharacter.ShowCharacter;
import com.example.noleart.mvp.views.interfaces.showcharacter.ShowFavCharacter;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.From;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainContract.View, CharacterListCallback {
    private final String TAG = "Main Activity";
    private static final String CHARACTER = "character";
    private static final String FAV_CHARACTER = "fav_character";

    private MainPresenterImpl presenter;
    private List<Characters> characters;
    private CharactersRecyclerViewAdapter adapter;

    @BindView(R.id.heroName)
    EditText mHeroName;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "On Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlowManager.init(new FlowConfig.Builder(this).build());
        ButterKnife.bind(this);

        super.initToolbar();
        setTitle("Inici");

        presenter = new MainPresenterImpl();
        presenter.attach(this, this);


        mHeroName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //trim() elimina espais innecessaris"
                String enterText = mHeroName.getText().toString().trim();
                //iniciem busca a partir de 3 lletres
                if (enterText.length() > 2) {
                    initSearch(enterText);

                }

            }
        });
        characters = new ArrayList<>();
        adapter = new CharactersRecyclerViewAdapter(characters, getBaseContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(adapter);

    }

    private void initSearch(String enterText) {
        presenter.getHeroes(enterText);
    }

    @Override
    public void fillData(List<Characters> characters) {
        this.characters = characters;
        adapter.fillData(characters);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showList(boolean show) {
        mRecyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressBar(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    public boolean characterOnDataBase(Characters item) {
        boolean pertany = false;
        List<Favourite> ids = SQLite.select(Favourite_Table.id)
                .from(Favourite.class).queryList();
        for (Favourite i : ids) {
            if ((i.getId()) == (item.getId())) {
                pertany = true;
            }
        }
        return pertany;
    }

    @Override
    public void onClickCharacter(Characters item) {
        if (characterOnDataBase(item)) {
            Log.e("TAG", "està a la DB");
            Favourite character = SQLite
                    .select()
                    .from(Favourite.class)
                    .where(Favourite_Table.id.eq(item.getId()))
                    .querySingle();
            Gson gson = new Gson();
            String json = gson.toJson(character);
            Intent i = new Intent(getBaseContext(), ShowFavCharacter.class);
            i.putExtra(FAV_CHARACTER, json);
            startActivity(i);

        } else {
            Log.e("TAG", "NO està a la DB");
            Gson gson = new Gson();
            String json = gson.toJson(item);
            Intent i = new Intent(getBaseContext(), ShowCharacter.class);
            i.putExtra(CHARACTER, json);
            startActivity(i);
        }

    }

}
