package com.quicku.translate.root;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.SQLException;

import com.quicku.translate.databases.LastTranslatedWordsDatabase;
import com.quicku.translate.di.AppComponent;
import com.quicku.translate.di.AppModule;
import com.quicku.translate.di.DaggerAppComponent;
import com.quicku.translate.networking.TranslateApiModule;

import java.io.IOException;
import java.util.Locale;

public class QuickuApplication extends Application {

    private static QuickuApplication mInstance;
    private AppComponent mAppComponent;

    private SharedPreferences appPrefs;
    private SharedPreferences.Editor appPrefsEditor;
    private String deviceLang;

    private LastTranslatedWordsDatabase lastTranslatedWordsDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Create Dagger graph
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .translateApiModule(new TranslateApiModule())
                .build();

        setInitialSharedPrefsSettings();

        lastTranslatedWordsDatabase = new LastTranslatedWordsDatabase(this);
        if (lastTranslatedWordsDatabase.checkDatabase()) {
            // Open database
            try {
                lastTranslatedWordsDatabase.openDatabase();
            } catch (SQLException sqle) {
                throw sqle;
            }

        } else {
            // Create database
            try {
                lastTranslatedWordsDatabase.createDatabase();
            } catch (IOException e) {
                throw new Error("Unable to create database");
            }
        }
    }

    private void setInitialSharedPrefsSettings() {
        appPrefs = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        appPrefsEditor = appPrefs.edit();
        deviceLang = Locale.getDefault().getLanguage();
        appPrefsEditor.putString("device_lang", deviceLang);
        appPrefsEditor.apply();

        if (!appPrefs.contains("translate_card_theme")) {
            appPrefsEditor.putInt("translate_card_theme", 0); // First theme
            appPrefsEditor.putBoolean("isTargetLangIsDeviceLang", true);
            appPrefsEditor.putBoolean("isSourceLangAutoDetect", true);
            appPrefsEditor.putString("translate_source_lang", "en");
            appPrefsEditor.putString("translate_target_lang", deviceLang);
            appPrefsEditor.apply();
        }

        if (!appPrefs.contains("isTargetLangIsDeviceLang")) {
            appPrefsEditor.putString("translate_target_lang", deviceLang);
            appPrefsEditor.apply();
        }

        // set on every start
        appPrefsEditor.putBoolean("isHistoryCleared", false);
        appPrefsEditor.apply();
    }

    public static synchronized QuickuApplication getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
