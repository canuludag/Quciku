package com.quicku.translate.utils;

import android.content.Context;

import com.quicku.translate.di.AppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class FontManagerModule {

    @Singleton
    @Provides
    FontManager provideFontManager(Context context) {
        return new FontManager(context);
    }

}
