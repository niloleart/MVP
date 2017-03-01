package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.database.Favourite;
import com.example.noleart.mvp.api.database.Favourite_Table;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.api.entities.Url;
import com.example.noleart.mvp.utils.Utils;
import com.example.noleart.mvp.views.interfaces.BaseActivity;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by noleart on 16/02/17.
 */

public class ShowFavCharacter extends BaseActivity {
    private static final String TAG = "ShowFavCharacter";
    private Favourite mFavCharacter;
    private static final String CHARACTER = "character";
    private static final String FAV_CHARACTER = "fav_character";
    private ShowFavCharacter.SectionsPagerAdapter mSectionsPageAdapter;

    @BindView(R.id.detail_avatar)
    ImageView mDetailAvatar;

    @BindView(R.id.detail_name)
    TextView mDetailName;

    @BindView(R.id.detail_description)
    TextView mDetailDescription;

    @BindView(R.id.detail_detail)
    Button mDetail;

    @BindView(R.id.detail_comics)
    Button mComics;

    @BindView(R.id.detail_wiki)
    Button mWiki;

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.fav_button)
    ImageButton mFavButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "FAVOURITE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_character);

        ButterKnife.bind(this);

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());


        Gson gson = new Gson();

        String json_fav = getIntent().getExtras().getString(FAV_CHARACTER);


        mFavCharacter = gson.fromJson(json_fav, Favourite.class);

        super.initFavToolbar();
        setTitle(mFavCharacter.getName());

        String urlImage = mFavCharacter.getThumbnail();
        Glide.with(this).load(urlImage).into(mDetailAvatar);

        mDetailName.setText(mFavCharacter.getName());
        mDetailDescription.setText(mFavCharacter.getDescription());

        mFavCharacter.setFavourite(true);

        Utils.loadImage(this,R.drawable.fav,mFavButton);

        mSectionsPageAdapter = new ShowFavCharacter.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPageAdapter);
        fillTabs();


        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavState();
            }
        });
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mFavCharacter.getDetailUrl());
            }
        });

        mWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mFavCharacter.getWikiUrl());
            }
        });

        mComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mFavCharacter.getComiclinkUrl());
            }
        });


    }

    private void fillTabs() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
        String title = "";
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            switch (i) {
                case 0:
                    title = "[" + /*mCharacter.getComics().getAvailable() + */"] " + getString(R.string.comics);
                    break;
                case 1:
                    title = "[" + /*mCharacter.getEvents().getAvailable() + */"] " + getString(R.string.events);
                    break;
            }
            mTabLayout.getTabAt(i).setText(title);
        }

    }


    private void changeFavState() {
        Favourite favourite = new Favourite();
        if (mFavCharacter.isFavourite()) {
            mFavCharacter.setFavourite(false);
            Utils.loadImage(this,R.drawable.unfav,mFavButton);

            SQLite.delete(Favourite.class).where(Favourite_Table.id.is(mFavCharacter.getId())).async().execute();

            //Toast.makeText(this, "Esborrat de favorits", Toast.LENGTH_SHORT).show();
        } else {

            Utils.loadImage(this, R.drawable.fav, mFavButton);
            mFavCharacter.setFavourite(true);
            favourite.setId(mFavCharacter.getId());
            favourite.setFavourite(true);
            favourite.setName(mFavCharacter.getName());
            favourite.setDescription(mFavCharacter.getDescription());
            favourite.setThumbnail(mFavCharacter.getThumbnail());
            favourite.setWikiUrl(mFavCharacter.getWikiUrl());
            favourite.setDetailUrl(mFavCharacter.getDetailUrl());
            favourite.setComiclinkUrl(mFavCharacter.getComiclinkUrl());

            favourite.save();
            //Toast.makeText(this, "Guardat a favorits", Toast.LENGTH_SHORT).show();
        }

    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    ComicFragment comicFragment = ComicFragment.newInstance(mFavCharacter.getId());
                    ComicPresenterImpl comicPresenter = new ComicPresenterImpl();
                    comicPresenter.attach(ShowFavCharacter.this, comicFragment);
                    fragment = comicFragment;
                    break;

                case 1:
                    EventFragment eventFragment = EventFragment.newInstance(mFavCharacter.getId());
                    EventPresenterImpl eventPresenter = new EventPresenterImpl();
                    eventPresenter.attach(ShowFavCharacter.this, eventFragment);
                    fragment = eventFragment;
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}