package com.quicku.translate.utils;

import com.quicku.translate.BuildConfig;

public class Constants {
    // Database Constants
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "translate_history.sqlite";
    public static final String COLUMN_WORDS_DATE = "date";
    public static final String COLUMN_WORDS_TEXT = "text";
    public static final String DATABASE_ASSETS_PATH = "databases/" + DATABASE_NAME;

    // Shared Preferences Constants
    public static final String SETTINGS_PREF = "SETTINGS";

    public static String getYandexTranslateApiKey() {
        return BuildConfig.YANDEX_TRANSLATE_API_KEY;
    }

}
