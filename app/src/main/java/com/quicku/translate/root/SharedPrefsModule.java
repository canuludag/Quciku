package com.quicku.translate.root;

import android.content.Context;
import android.content.SharedPreferences;

import com.quicku.translate.di.AppModule;
import com.quicku.translate.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class SharedPrefsModule {
    private SharedPreferences mSharedPreferences;

    @Singleton
    @Provides
    SharedPreferences provideSharedPrefs(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constants.SETTINGS_PREF, Context.MODE_PRIVATE);

        return mSharedPreferences;
    }

    @Singleton
    @Provides
    SharedPreferences.Editor providePrefsEditor() {
        SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();

        return prefsEditor;
    }
}
