package com.quicku.translate.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicku.translate.R;

import javax.inject.Inject;

public class TranslateCardThemeManager {

    private Context mContext;
    private SharedPreferences mSharedPrefs;

    @Inject
    public TranslateCardThemeManager(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPrefs = sharedPreferences;
    }

    public void setActiveTheme(RelativeLayout background, TextView text) {
        switch (mSharedPrefs.getInt("translate_card_theme", 0)) {
            case 0:
                setTheme0(background, text);
                break;
            case 1:
                setTheme1(background, text);
                break;
            case 2:
                setTheme2(background, text);
                break;
            case 3:
                setTheme3(background, text);
                break;
            case 4:
                setTheme4(background, text);
                break;
        }
    }

    private void setTheme0(RelativeLayout background, TextView text) {
        background.setBackground(mContext.getResources().getDrawable(R.drawable.translate_result_card_1));
        text.setTextColor(mContext.getResources().getColor(R.color.translate_result_card_1_font_color));
    }

    private void setTheme1(RelativeLayout background, TextView text) {
        background.setBackground(mContext.getResources().getDrawable(R.drawable.translate_result_card_2));
        text.setTextColor(mContext.getResources().getColor(R.color.translate_result_card_2_font_color));
    }

    private void setTheme2(RelativeLayout background, TextView text) {
        background.setBackground(mContext.getResources().getDrawable(R.drawable.translate_result_card_3));
        text.setTextColor(mContext.getResources().getColor(R.color.translate_result_card_3_font_color));
    }

    private void setTheme3(RelativeLayout background, TextView text) {
        background.setBackground(mContext.getResources().getDrawable(R.drawable.translate_result_card_4));
        text.setTextColor(mContext.getResources().getColor(R.color.translate_result_card_4_font_color));
    }

    private void setTheme4(RelativeLayout background, TextView text) {
        background.setBackground(mContext.getResources().getDrawable(R.drawable.translate_result_card_5));
        text.setTextColor(mContext.getResources().getColor(R.color.translate_result_card_5_font_color));
    }

}
