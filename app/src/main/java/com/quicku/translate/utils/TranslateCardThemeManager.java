package com.quicku.translate.utils;


import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicku.translate.R;

public class TranslateCardThemeManager {

    private Activity activity;
    private SharedPreferences appPrefs;

    public TranslateCardThemeManager(Activity a) {

        this.activity = a;
        appPrefs = activity.getSharedPreferences("SETTINGS", activity.MODE_PRIVATE);

    }

    public void setActiveTheme(RelativeLayout background, TextView text) {

        switch (appPrefs.getInt("translate_card_theme", 0)) {

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

    public void setTheme0(RelativeLayout background, TextView text) {

        background.setBackground(activity.getResources().getDrawable(R.drawable.translate_result_card_1));
        text.setTextColor(activity.getResources().getColor(R.color.translate_result_card_1_font_color));

    }

    public void setTheme1(RelativeLayout background, TextView text) {

        background.setBackground(activity.getResources().getDrawable(R.drawable.translate_result_card_2));
        text.setTextColor(activity.getResources().getColor(R.color.translate_result_card_2_font_color));

    }

    public void setTheme2(RelativeLayout background, TextView text) {

        background.setBackground(activity.getResources().getDrawable(R.drawable.translate_result_card_3));
        text.setTextColor(activity.getResources().getColor(R.color.translate_result_card_3_font_color));

    }

    public void setTheme3(RelativeLayout background, TextView text) {

        background.setBackground(activity.getResources().getDrawable(R.drawable.translate_result_card_4));
        text.setTextColor(activity.getResources().getColor(R.color.translate_result_card_4_font_color));

    }

    public void setTheme4(RelativeLayout background, TextView text) {

        background.setBackground(activity.getResources().getDrawable(R.drawable.translate_result_card_5));
        text.setTextColor(activity.getResources().getColor(R.color.translate_result_card_5_font_color));

    }

}
