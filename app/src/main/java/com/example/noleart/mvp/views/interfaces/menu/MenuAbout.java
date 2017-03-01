package com.example.noleart.mvp.views.interfaces.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.noleart.mvp.R;
import com.example.noleart.mvp.views.interfaces.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by noleart on 1/03/17.
 */

public class MenuAbout extends BaseActivity {
    private static final String TAG = "AboutUs";
    private static final String URLOTQueFem = "http://opentrends.net/ca/whatwedo;jsessionid=52E782B375C4A5EDF8F35EEAFB3CB7BD";
    private static final String URLOTBlog = "http://insights.opentrends.net/en/";
    private static final String URLOTTwitter = "https://twitter.com/opentrends";
    private static final String URLNilInstagram = "https://www.instagram.com/shakisz_/";
    private static final String URLNilLinkedin = "https://www.linkedin.com/in/nil-oleart-play%C3%A0-461b56127";
    private static final String URLNilTwitter = "https://twitter.com/niloleart";
    @BindView(R.id.opentrends_twitter)
    Button mOTTwitter;

    @BindView(R.id.opentrends_blog)
    Button mOTBlog;

    @BindView(R.id.opentrends_quefem)
    Button mOTQueFem;

    @BindView(R.id.nil_insta)
    LinearLayout mInstagram;

    @BindView(R.id.nil_linkedin)
    LinearLayout mLinkedin;

    @BindView(R.id.nil_twitter)
    LinearLayout mTwitter;
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"Entra");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_about);
        ButterKnife.bind(this);

        initMenuToolbar("Sobre nosaltres...");

        mOTQueFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLOTQueFem);
            }
        });

        mOTBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLOTBlog);
            }
        });

        mOTTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLOTTwitter);
            }
        });

        mInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLNilInstagram);
            }
        });

        mTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLNilTwitter);
            }
        });

        mLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(URLNilLinkedin);
            }
        });
    }
    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
