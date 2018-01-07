package com.quicku.translate.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.databases.LastTranslatedWordsDatabase;
import com.quicku.translate.ui.translatelanguages.TranslateLanguageActivity;
import com.quicku.translate.ui.info.InfoActivity;
import com.quicku.translate.ui.settings.SettingsActivity;
import com.quicku.translate.utils.FontManager;
import com.quicku.translate.utils.TranslateLanguageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    // View injection
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.tvLastTranslatedWordsCapiton)
    TextView tvLastTranslatedWordsCapiton;
    @BindView(R.id.tvCurrentSourceAndTargetLangs)
    TextView tvCurrentSourceAndTargetLangs;
    @BindView(R.id.rlCurrentSourceAndTargetLangs)
    RelativeLayout rlCurrentSourceAndTargetLangs;
    @BindView(R.id.rcvLastTranslatedWords)
    RecyclerView rcvLastTranslatedWords;
    private ImageView ivToolbarSettings;
    private ImageView ivToolbarInfo;

    private RcvLastTranslatedWordsAdapter rcvLastTranslatedWordsAdapter;
    private ArrayList<String> lastTranslatedWordsList;

    private SharedPreferences appPrefs;
    private SharedPreferences.Editor appPrefsEditor;

    private LastTranslatedWordsDatabase lastTranslatedWordsDatabase;
    private ArrayList<HashMap<String, String>> lastTranslatedWordsMapList;

    private TranslateLanguageManager translateLanguageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        translateLanguageManager = new TranslateLanguageManager(this);

        appPrefs = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        appPrefsEditor = appPrefs.edit();

        createCustomToolbar();
        getAllTranslatedWordsFromDb();
        setupRecyclerView();
        setFonts();
        setClickListeners();
    }

    private void getAllTranslatedWordsFromDb() {
        lastTranslatedWordsDatabase = new LastTranslatedWordsDatabase(this);
        lastTranslatedWordsMapList = new ArrayList<>();
        lastTranslatedWordsList = new ArrayList<>();

        lastTranslatedWordsMapList = lastTranslatedWordsDatabase.getAllWords("words");

        if (lastTranslatedWordsMapList.size() > 0) {
            for (HashMap<String, String> textMap : lastTranslatedWordsMapList) {
                String text = textMap.get("text");
                lastTranslatedWordsList.add(text);
            }
            Collections.reverse(lastTranslatedWordsList);
            Log.d("DB", "Size: "+ lastTranslatedWordsList.size());
        }
    }

    private void createCustomToolbar() {
        TextView tvAppBarHeader = toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setTypeface(FontManager.getRobotoSlabBold(this));
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_home));

        ivToolbarSettings = toolbar.findViewById(R.id.ivToolbarSettings);
        ivToolbarInfo = toolbar.findViewById(R.id.ivToolbarInfo);

        setSupportActionBar(toolbar);
    }

    private void setFonts() {
        tvLastTranslatedWordsCapiton.setTypeface(FontManager.getRobotoBold(this));
        tvCurrentSourceAndTargetLangs.setTypeface(FontManager.getRobotoRegular(this));

        tvCurrentSourceAndTargetLangs.setText(getResources().getString(R.string.tv_current_source_target_lang_info1) + " "
                + translateLanguageManager.getSourceLang() + "-" + translateLanguageManager.getTargetLang()
                + getResources().getString(R.string.tv_current_source_target_lang_info2));
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        rcvLastTranslatedWords.setLayoutManager(linearLayoutManager);
        rcvLastTranslatedWordsAdapter = new RcvLastTranslatedWordsAdapter(this, lastTranslatedWordsList);
        rcvLastTranslatedWords.setAdapter(rcvLastTranslatedWordsAdapter);
    }

    private void setClickListeners() {
        rlCurrentSourceAndTargetLangs.setOnClickListener(this);
        ivToolbarSettings.setOnClickListener(this);
        ivToolbarInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivToolbarSettings:
                Intent iSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(iSettings);
                break;
            case R.id.ivToolbarInfo:
                Intent iInfo = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(iInfo);
                break;
            case R.id.rlCurrentSourceAndTargetLangs:
                Intent iTranslateLangs = new Intent(getApplicationContext(), TranslateLanguageActivity.class);
                startActivity(iTranslateLangs);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetHistory();
    }

    private void resetHistory() {
        lastTranslatedWordsMapList = lastTranslatedWordsDatabase.getAllWords("words");
        Log.d("onResume", "Size: " + lastTranslatedWordsMapList.size());

        if (lastTranslatedWordsMapList.size() > 0) {
            if (lastTranslatedWordsList.size() > 0) {
                lastTranslatedWordsList.clear();
            }

            for (HashMap<String, String> textMap : lastTranslatedWordsMapList) {
                String text = textMap.get("text");
                lastTranslatedWordsList.add(text);
            }

            Collections.reverse(lastTranslatedWordsList);
        } else {
            if (lastTranslatedWordsList.size() > 0) {
                lastTranslatedWordsList.clear();
            }
        }

        rcvLastTranslatedWordsAdapter.notifyDataSetChanged();

        tvCurrentSourceAndTargetLangs.setText(getResources().getString(R.string.tv_current_source_target_lang_info1) + " "
                + translateLanguageManager.getSourceLang() + "-" + translateLanguageManager.getTargetLang()
                + getResources().getString(R.string.tv_current_source_target_lang_info2));

    }
}
