package com.quicku.translate.root;

import android.app.Application;
import android.database.SQLException;

import com.quicku.translate.databases.LastTranslatedWordsDatabase;
import com.quicku.translate.di.AppComponent;
import com.quicku.translate.di.AppModule;
import com.quicku.translate.di.DaggerAppComponent;
import com.quicku.translate.networking.TranslateApiModule;
import com.quicku.translate.utils.FontManagerModule;
import com.quicku.translate.utils.TranslateCardThemeModule;

import java.io.IOException;

public class QuickuApplication extends Application {

    private static QuickuApplication mInstance;
    private AppComponent mAppComponent;

    private LastTranslatedWordsDatabase lastTranslatedWordsDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Create Dagger graph
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .translateApiModule(new TranslateApiModule())
                .sharedPrefsModule(new SharedPrefsModule())
                .translateCardThemeModule(new TranslateCardThemeModule())
                .fontManagerModule(new FontManagerModule())
                .build();

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

    public static synchronized QuickuApplication getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
