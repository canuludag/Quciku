package com.quicku.translate.utils;

import android.app.Activity;
import android.graphics.Typeface;

public class FontManager {
    private static Typeface robotoSlabBold;
    private static Typeface robotoSlabLight;
    private static Typeface robotoSlabRegular;
    private static Typeface robotoRegular;
    private static Typeface robotoBold;
    private static Typeface merriweatherSansRegular;
    private static Typeface merriweatherSansBold;
    private static Typeface sourceSansProRegular;
    private static Typeface sourceSansProBold;

    public static Typeface getRobotoSlabBold(Activity activity) {
        robotoSlabBold = Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Bold.ttf");

        return robotoSlabBold;
    }

    public static Typeface getRobotoSlabLight(Activity activity) {
        robotoSlabLight = Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Light.ttf");

        return robotoSlabLight;
    }



    public static Typeface getRobotoSlabRegular(Activity activity) {
        robotoSlabRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/RobotoSlab-Regular.ttf");

        return robotoSlabRegular;
    }

    public static Typeface getRobotoRegular(Activity activity) {
        robotoRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Regular.ttf");

        return robotoRegular;
    }

    public static Typeface getRobotoBold(Activity activity) {
        robotoBold = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");

        return robotoBold;
    }

    public static Typeface getMerriweatherSansRegular(Activity activity) {
        merriweatherSansRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/MerriweatherSans-Regular.ttf");

        return merriweatherSansRegular;
    }

    public static Typeface getMerriweatherSansBold(Activity activity) {
        merriweatherSansBold = Typeface.createFromAsset(activity.getAssets(), "fonts/MerriweatherSans-Bold.ttf");

        return merriweatherSansBold;
    }

    public static Typeface getSourceSansProRegular(Activity activity) {
        sourceSansProRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/SourceSansPro-Regular.ttf");

        return sourceSansProRegular;
    }

    public static Typeface getSourceSansProBold(Activity activity) {
        sourceSansProBold = Typeface.createFromAsset(activity.getAssets(), "fonts/SourceSansPro-Bold.ttf");

        return sourceSansProBold;
    }



}
