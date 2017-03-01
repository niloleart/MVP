package com.example.noleart.mvp.api;

import com.example.noleart.mvp.utils.Constants;
import com.example.noleart.mvp.utils.Utils;

/**
 * Created by noleart on 3/02/17.
 */

public class SendRequest {
    private static final String apiKey = Constants.API_KEY;
    private static final String privateKey = Constants.PRIVATE_KEY;
    private static final String publicKey = apiKey;
    private long timeStamp;
    private String hashSignature;

    public static String getApiKey() {
        return apiKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public String getHashSignature() {
        return hashSignature;
    }
    private SendRequest(){
        this.timeStamp=1;
        this.hashSignature= Utils.md5(String.valueOf(this.timeStamp)+privateKey+publicKey);
    }

    public static SendRequest create() {
        return new SendRequest();
    }
}
