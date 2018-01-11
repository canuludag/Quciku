package com.quicku.translate.di;

import android.content.Context;

import com.quicku.translate.root.QuickuApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private QuickuApplication mQuickuApplication;

    public AppModule(QuickuApplication quickuApplication) {
        mQuickuApplication = quickuApplication;
    }

    @Singleton
    @Provides
    QuickuApplication provideApp() {
        return mQuickuApplication;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mQuickuApplication.getApplicationContext();
    }
}
