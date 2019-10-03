package com.zhiyong.easy_attendance.data.local;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferenceHelper {
    private static final String PREF_ACCESS_TOKEN = "access_token";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Inject
    public PreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {
        editor.putString(PREF_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public void clearAccessToken() {
        editor.remove(PREF_ACCESS_TOKEN);
    }
}
