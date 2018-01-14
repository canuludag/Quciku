package com.quicku.translate.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;


public class TranslateLanguageManager {

    private String deviceLang;
    private boolean isSourceLangAutoDetect = true;

    private SharedPreferences mAppPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    @Inject
    public TranslateLanguageManager(SharedPreferences sharedPrefs) {
        mAppPrefs = sharedPrefs;
        mPrefsEditor = mAppPrefs.edit();
    }

    public void setDeviceLang(String language) {
        mPrefsEditor.putString("device_lang", language);
        mPrefsEditor.apply();
    }

    public String getDeviceLang() {
        deviceLang = mAppPrefs.getString("device_lang", "en");

        return deviceLang;
    }

    public void setSourceLang(String language) {
        mPrefsEditor.putString("translate_source_lang", language);
        mPrefsEditor.apply();
    }

    public String getSourceLang() {

        return mAppPrefs.getString("translate_source_lang", "en");
    }

    public String getTargetLang() {

        return mAppPrefs.getString("translate_target_lang", deviceLang);
    }

    public void setTargetLang(String language) {
        mPrefsEditor.putString("translate_target_lang", language);
        mPrefsEditor.apply();
    }

    public void setSourceLangAutoDetect(boolean status) {
        isSourceLangAutoDetect = status;
        mPrefsEditor.putBoolean("isSourceLangAutoDetect", isSourceLangAutoDetect);
        mPrefsEditor.apply();

    }

    public boolean getSourceLangAutoDetect() {
        isSourceLangAutoDetect = mAppPrefs.getBoolean("isSourceLangAutoDetect", true);

        return isSourceLangAutoDetect;
    }
}
