package com.quicku.translate.utils;

import android.content.Context;
import android.graphics.Typeface;

import javax.inject.Inject;

public class FontManager {

    private Context mContext;

    @Inject
    public FontManager(Context context) {
        mContext = context;
    }

    public Typeface getRobotoSlabBold() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoSlab-Bold.ttf");
    }

    public Typeface getRobotoSlabLight() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoSlab-Light.ttf");
    }

    public Typeface getRobotoSlabRegular() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoSlab-Regular.ttf");
    }

    public Typeface getRobotoRegular() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    public Typeface getRobotoBold() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Bold.ttf");
    }

    public Typeface getMerriweatherSansRegular() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/MerriweatherSans-Regular.ttf");
    }

    public Typeface getMerriweatherSansBold() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/MerriweatherSans-Bold.ttf");
    }

    public Typeface getSourceSansProRegular() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/SourceSansPro-Regular.ttf");
    }

    public Typeface getSourceSansProBold() {
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/SourceSansPro-Bold.ttf");
    }
}
