package com.quicku.translate.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.databases.LastTranslatedWordsDatabase;
import com.quicku.translate.root.QuickuApplication;
import com.quicku.translate.ui.themeselection.ThemeSelectionActivity;
import com.quicku.translate.ui.translatelanguages.TranslateLanguageActivity;
import com.quicku.translate.utils.FontManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    // View injection
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.tvSettingsCardThemes)
    TextView tvSettingsCardThemes;
    @BindView(R.id.tvSettingsTranslateLanguage)
    TextView tvSettingsTranslateLanguage;
    @BindView(R.id.tvSettingsClearHistory)
    TextView tvSettingsClearHistory;
    @BindView(R.id.rlCardThemes)
    RelativeLayout rlCardThemes;
    @BindView(R.id.rlTranslateLanguage)
    RelativeLayout rlTranslateLanguage;
    @BindView(R.id.rlClearHistory)
    RelativeLayout rlClearHistory;
    private TextView tvAppBarHeader;

    private LastTranslatedWordsDatabase lastTranslatedWordsDatabase;

    @Inject
    SharedPreferences mSharedPrefs;
    @Inject
    SharedPreferences.Editor mPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        // Initialize injection for this activity
        ((QuickuApplication) getApplication()).getAppComponent().inject(this);

        createCustomToolbar();
        setFonts();
        setClickListeners();

        lastTranslatedWordsDatabase = new LastTranslatedWordsDatabase(this);
        lastTranslatedWordsDatabase.openDatabase();
    }

    private void setFonts() {
        tvAppBarHeader.setTypeface(FontManager.getRobotoBold(this));
        tvSettingsCardThemes.setTypeface(FontManager.getRobotoRegular(this));
        tvSettingsTranslateLanguage.setTypeface(FontManager.getRobotoRegular(this));
        tvSettingsClearHistory.setTypeface(FontManager.getRobotoRegular(this));
    }

    private void setClickListeners() {
        rlCardThemes.setOnClickListener(this);
        rlTranslateLanguage.setOnClickListener(this);
        rlClearHistory.setOnClickListener(this);
    }


    private void createCustomToolbar() {
        tvAppBarHeader = toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_settings));
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlCardThemes:
                Intent iCardThemes = new Intent(getApplicationContext(), ThemeSelectionActivity.class);
                startActivity(iCardThemes);
                break;
            case R.id.rlTranslateLanguage:
                Intent iTranslateLanguage = new Intent(getApplicationContext(), TranslateLanguageActivity.class);
                startActivity(iTranslateLanguage);
                break;
            case R.id.rlClearHistory:
                new AlertDialog.Builder(this)
                        .setMessage(getResources().getString(R.string.dialog_delete_all_data_message))
                        .setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                lastTranslatedWordsDatabase.deleteAllData("words");
                                lastTranslatedWordsDatabase.close();

                                mPrefsEditor.putBoolean("isHistoryCleared", true);
                                mPrefsEditor.apply();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }
}
