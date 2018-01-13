package com.quicku.translate.di;

import com.quicku.translate.networking.TranslateApiModule;
import com.quicku.translate.root.SharedPrefsModule;
import com.quicku.translate.ui.home.HomeActivity;
import com.quicku.translate.ui.info.InfoActivity;
import com.quicku.translate.ui.settings.SettingsActivity;
import com.quicku.translate.ui.themeselection.ThemeSelectionActivity;
import com.quicku.translate.ui.translatelanguages.TranslateLanguageActivity;
import com.quicku.translate.ui.translateresults.TranslateResultsActivity;
import com.quicku.translate.utils.FontManagerModule;
import com.quicku.translate.utils.TranslateCardThemeModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SharedPrefsModule.class
        , TranslateApiModule.class, TranslateCardThemeModule.class,
        FontManagerModule.class})
public interface AppComponent {
    void inject(HomeActivity target);
    void inject(TranslateResultsActivity target);
    void inject(SettingsActivity target);
    void inject(ThemeSelectionActivity target);
    void inject(InfoActivity target);
    void inject(TranslateLanguageActivity target);
}
