package com.example.noleart.mvp.views.interfaces.showcharacter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.noleart.mvp.R;
import com.example.noleart.mvp.api.database.Favourite;
import com.example.noleart.mvp.api.database.Favourite_Table;
import com.example.noleart.mvp.api.entities.Characters;
import com.example.noleart.mvp.api.entities.Url;
import com.example.noleart.mvp.utils.Utils;
import com.example.noleart.mvp.utils.Utils.*;
import com.example.noleart.mvp.views.interfaces.BaseActivity;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by noleart on 9/02/17.
 */
public class ShowCharacter extends BaseActivity {
    private Characters mCharacter;
    private Favourite mFavCharacter;
    private static final String CHARACTER = "character";
    private static final String FAV_CHARACTER = "fav_character";
    private SectionsPagerAdapter mSectionsPageAdapter;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_character);

        ButterKnife.bind(this);

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());

        Gson gson = new Gson();
        String json_norm = getIntent().getExtras().getString(CHARACTER);

        mCharacter = gson.fromJson(json_norm, Characters.class);

        super.initFavToolbar();
        setTitle(mCharacter.getName());

        String urlImage = mCharacter.getThumbnail().getPath() + "." + mCharacter.getThumbnail().getExtension();
        Glide.with(this)
                .load(urlImage)
                .into(mDetailAvatar);

        mDetailName.setText(mCharacter.getName());
        mDetailDescription.setText(mCharacter.getDescription());

        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mCharacter.getUrls(), "detail");
            }
        });

        mWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mCharacter.getUrls(), "wiki");
            }
        });

        mComics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mCharacter.getUrls(), "comiclink");
            }
        });

        mSectionsPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPageAdapter);

        fillTabs();
        mCharacter.setFavourite(false);
        Utils.loadImage(this, R.drawable.unfav, mFavButton);

        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFavState();
            }
        });


    }

    private void fillTabs() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
        ;
        String title = "";
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            switch (i) {
                case 0:
                    title = "[" + mCharacter.getComics().getAvailable() + "] " + getString(R.string.comics);
                    break;
                case 1:
                    title = "[" + mCharacter.getEvents().getAvailable() + "] " + getString(R.string.events);
                    break;
            }
            mTabLayout.getTabAt(i).setText(title);
        }

    }

    private void changeFavState() {
        Favourite favourite = new Favourite();
        if (!mCharacter.isFavourite()) {
            Utils.loadImage(this, R.drawable.fav, mFavButton);

            mCharacter.setFavourite(true);

            favourite.setId(mCharacter.getId());
            favourite.setName(mCharacter.getName());
            favourite.setFavourite(mCharacter.isFavourite());
            favourite.setDescription(mCharacter.getDescription());
            favourite.setThumbnail(mCharacter.getThumbnail().getPath() + "." + mCharacter.getThumbnail().getExtension());

            List<Url> urls = mCharacter.getUrls();
            for (Url url : urls) {
                if (url.getType().equals("wiki")) {
                    favourite.setWikiUrl(url.getUrl());
                } else if (url.getType().equals("detail")) {
                    favourite.setDetailUrl(url.getUrl());
                } else if (url.getType().equals("comiclink")) {
                    favourite.setComiclinkUrl(url.getUrl());
                }
            }
            favourite.save();
        } else {
            Utils.loadImage(this, R.drawable.unfav, mFavButton);
            mCharacter.setFavourite(false);
            SQLite.delete(Favourite.class).where(Favourite_Table.id.is(mCharacter.getId())).async().execute();
        }
    }

    private void openUrl(List<Url> urls, String type) {
        for (Url url : urls) {
            if (url.getType().equals(type)) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url.getUrl()));
                startActivity(i);
            }
        }

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
                    ComicFragment comicFragment = ComicFragment.newInstance(mCharacter.getId());
                    ComicPresenterImpl comicPresenter = new ComicPresenterImpl();
                    comicPresenter.attach(ShowCharacter.this, comicFragment);
                    fragment = comicFragment;
                    break;

                case 1:
                    EventFragment eventFragment = EventFragment.newInstance(mCharacter.getId());
                    EventPresenterImpl eventPresenter = new EventPresenterImpl();
                    eventPresenter.attach(ShowCharacter.this, eventFragment);
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
