package com.quicku.translate.utils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FontManagerModule {

    @Singleton
    @Provides
    FontManager provideFontManager() {
        return new FontManager();
    }

}
