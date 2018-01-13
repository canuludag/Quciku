package com.quicku.translate.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
    public Typeface getRobotoSlabBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Bold.ttf");
    }

    public Typeface getRobotoSlabLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Light.ttf");
    }

    public Typeface getRobotoSlabRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/RobotoSlab-Regular.ttf");
    }

    public Typeface getRobotoRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public Typeface getRobotoBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    public Typeface getMerriweatherSansRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/MerriweatherSans-Regular.ttf");
    }

    public Typeface getMerriweatherSansBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/MerriweatherSans-Bold.ttf");
    }

    public Typeface getSourceSansProRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Regular.ttf");
    }

    public Typeface getSourceSansProBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/SourceSansPro-Bold.ttf");
    }
}
