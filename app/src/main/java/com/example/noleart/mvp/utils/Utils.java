package com.example.noleart.mvp.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.target.Target;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by noleart on 3/02/17.
 */

public class Utils {
    /**
     * Creates an md5 hash string from the string input
     *
     * @param s  String to be hashed
     * @return
     */
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final void loadImage(Context context, Object resource,ImageView view){
        Glide.with(context)
                .load(resource)
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .into(view);
    }
}
