package com.quicku.translate.di;

import com.quicku.translate.networking.TranslateApiModule;
import com.quicku.translate.ui.translateresults.TranslateResultsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, TranslateApiModule.class})
public interface AppComponent {
    void inject(TranslateResultsActivity target);
}
