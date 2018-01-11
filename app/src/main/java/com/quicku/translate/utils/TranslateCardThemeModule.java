package com.quicku.translate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.quicku.translate.di.AppModule;
import com.quicku.translate.root.SharedPrefsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPrefsModule.class, AppModule.class})
public class TranslateCardThemeModule {
    @Provides
    @Singleton
    TranslateCardThemeManager provideCardThemeManager(Context context, SharedPreferences sharedPreferences) {
        return new TranslateCardThemeManager(context, sharedPreferences);
    }
}
