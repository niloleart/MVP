package com.example.noleart.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.noleart.mvp.api.database.Favourite;
import com.example.noleart.mvp.api.database.Favourite_Table;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.control.control.adapters.CharactersRecyclerViewAdapter;
import com.example.noleart.mvp.control.control.callbacks.CharacterListCallback;
import com.example.noleart.mvp.views.interfaces.BaseActivity;
import com.example.noleart.mvp.views.interfaces.interfaces.MainContract;
import com.example.noleart.mvp.views.interfaces.showcharacter.ShowCharacter;
import com.example.noleart.mvp.views.interfaces.showcharacter.ShowFavCharacter;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.noleart.mvp.R.id.toolbar;


public class MainActivity extends BaseActivity implements MainContract.View, CharacterListCallback, NavigationView.OnNavigationItemSelectedListener {
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

        Toolbar toolbar = super.initToolbar();
        setTitle("Inici");
        initDrawer(toolbar);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            Toast.makeText(this,"Camera pressed",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
