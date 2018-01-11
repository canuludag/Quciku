package com.quicku.translate.utils;

import android.app.Activity;
import android.graphics.Typeface;

public class FontManager {
    public static Typeface getRobotoSlabBold(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Bold.ttf");
    }

    public static Typeface getRobotoSlabLight(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Light.ttf");
    }

    public static Typeface getRobotoSlabRegular(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Regular.ttf");
    }

    public static Typeface getRobotoRegular(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public static Typeface getRobotoBold(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    public static Typeface getMerriweatherSansRegular(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/MerriweatherSans-Regular.ttf");
    }

    public static Typeface getMerriweatherSansBold(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/MerriweatherSans-Bold.ttf");
    }

    public static Typeface getSourceSansProRegular(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/SourceSansPro-Regular.ttf");
    }

    public static Typeface getSourceSansProBold(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "fonts/SourceSansPro-Bold.ttf");
    }
}
