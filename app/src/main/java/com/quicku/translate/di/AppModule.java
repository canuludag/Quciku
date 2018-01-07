package com.quicku.translate.di;

import android.content.Context;

import com.quicku.translate.root.QuickuApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    public QuickuApplication mQuickuApplication;

    public AppModule(QuickuApplication quickuApplication) {
        mQuickuApplication = quickuApplication;
    }

    @Singleton
    @Provides
    public QuickuApplication provideApp() {
        return mQuickuApplication;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mQuickuApplication.getApplicationContext();
    }
}
