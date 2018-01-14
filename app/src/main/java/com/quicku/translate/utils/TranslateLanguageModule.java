package com.quicku.translate.utils;

import android.content.SharedPreferences;

import com.quicku.translate.root.SharedPrefsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {SharedPrefsModule.class})
public class TranslateLanguageModule {

    @Singleton
    @Provides
    TranslateLanguageManager provideTranslateLanguageManager(SharedPreferences sharedPreferences) {
        return new TranslateLanguageManager(sharedPreferences);
    }

}
