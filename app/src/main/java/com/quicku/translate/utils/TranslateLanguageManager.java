package com.quicku.translate.utils;

import android.app.Activity;
import android.content.SharedPreferences;


public class TranslateLanguageManager {
    private Activity activity;

    public String deviceLang;
    public String sourceLang;
    public String targetLang;
    public boolean isSourceLangAutoDetect = true;

    private SharedPreferences appPrefs;
    private SharedPreferences.Editor appPrefsEditor;

    public TranslateLanguageManager(Activity a) {

        this.activity = a;

        appPrefs = activity.getSharedPreferences("SETTINGS", activity.MODE_PRIVATE);
        appPrefsEditor = appPrefs.edit();

    }

    public void setDeviceLang(String language) {
        appPrefsEditor.putString("device_lang", language);
        appPrefsEditor.apply();
    }

    public String getDeviceLang() {
        deviceLang = appPrefs.getString("device_lang", "en");

        return deviceLang;
    }

    public void setSourceLang(String language) {
        appPrefsEditor.putString("translate_source_lang", language);
        appPrefsEditor.apply();
    }

    public String getSourceLang() {
        sourceLang = appPrefs.getString("translate_source_lang", "en");

        return sourceLang;
    }

    public String getTargetLang() {
        targetLang = appPrefs.getString("translate_target_lang", deviceLang);

        return targetLang;
    }

    public void setTargetLang(String language) {
        appPrefsEditor.putString("translate_target_lang", language);
        appPrefsEditor.apply();
    }

    public void setSourceLangAutoDetect(boolean status) {
        isSourceLangAutoDetect = status;
        appPrefsEditor.putBoolean("isSourceLangAutoDetect", isSourceLangAutoDetect);
        appPrefsEditor.apply();

    }

    public boolean getSourceLangAutoDetect() {
        isSourceLangAutoDetect = appPrefs.getBoolean("isSourceLangAutoDetect", true);

        return isSourceLangAutoDetect;
    }
}
