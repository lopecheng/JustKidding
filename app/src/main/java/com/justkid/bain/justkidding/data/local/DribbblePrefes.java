package com.justkid.bain.justkidding.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.justkid.bain.justkidding.BuildConfig;
import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.injection.ApplicationContext;

import javax.inject.Inject;


public class DribbblePrefes {
    private static final String DRIBBBLE_PREFE = "dribbble_preference";
    private static final String DRIBBBLE_ACCESSTOKEN = "dribbble_accesstoken";
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_USERNAME = "KEY_USER_USERNAME";
    private static final String KEY_USER_AVATAR = "KEY_USER_AVATAR";
    public static final String oauthUrl = "https://dribbble.com/oauth/authorize?client_id="+
            BuildConfig.DRIBBBLE_CLIENT_ID+"&redirect_uri="+BuildConfig.REDIRECT_URI+"&scope=public+write+comment+upload";
    private String accessToken;

    private Context context;
    private SharedPreferences prefs;

    @Inject
    public DribbblePrefes(@ApplicationContext Context context) {
        this.context = context;
        prefs = context.getApplicationContext().getSharedPreferences(DRIBBBLE_PREFE, Context
                .MODE_PRIVATE);
        accessToken =prefs.getString(DRIBBBLE_ACCESSTOKEN,"");
    }

    public String getAccessToken() {
        return !TextUtils.isEmpty(accessToken) ? accessToken
                : BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN;
    }

    public void saveAccessToken(String accessToken){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DRIBBBLE_ACCESSTOKEN,accessToken).commit();
    }

    public void saveLoginedUserInfo(User user){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_USER_ID,user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_USERNAME, user.getUsername());
        editor.putString(KEY_USER_AVATAR, user.getAvatar_url());
        editor.commit();
    }

    public String getUserId(){
        return String.valueOf(prefs.getLong(KEY_USER_ID,0l));
    }

    public String getUserAvator(){
        return prefs.getString(KEY_USER_AVATAR,"");
    }

    public String getName(){
        return prefs.getString(KEY_USER_NAME,"");
    }

    public void deleteInfoWhenLogout(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_USER_ID,0l);
        editor.putString(KEY_USER_NAME, null);
        editor.putString(KEY_USER_USERNAME, null);
        editor.putString(KEY_USER_AVATAR, null);
        editor.putString(DRIBBBLE_ACCESSTOKEN,null);
        editor.commit();
    }
}
