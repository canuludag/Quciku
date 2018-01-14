package com.quicku.translate.ui.translatelanguages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.quicku.translate.R;
import com.quicku.translate.root.QuickuApplication;
import com.quicku.translate.utils.FontManager;
import com.quicku.translate.utils.TranslateLanguageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslateLanguageActivity extends AppCompatActivity {
    // View injection
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.tvTranslateLangInfo)
    TextView tvTranslateLangInfo;
    @BindView(R.id.tvSetSourceLangCaption)
    TextView tvSetSourceLangCaption;
    @BindView(R.id.tvSetTargetLangCaption)
    TextView tvSetTargetLangCaption;
    @BindView(R.id.tvAutoDetectTargetLangInfo)
    TextView tvAutoDetectTargetLangInfo;
    @BindView(R.id.tvPoweredByYandex)
    TextView tvPoweredByYandex;
    @BindView(R.id.spinnerSourceLang)
    Spinner spinnerSourceLang;
    @BindView(R.id.spinnerTargetLang)
    Spinner spinnerTargetLang;
    @BindView(R.id.cbAutoDetectTargetLang)
    CheckBox cbAutoDetectTargetLang;
    @BindView(R.id.llSourceLangSettingContainer)
    LinearLayout llSourceLangSettingContainer;

    private ArrayList<String> langList;
    private ArrayList<String> langKeyList;
    private ArrayList<CharSequence> adapterList;

    private String sourceLang, targetLang;
    private TextView tvAppBarHeader;

    @Inject
    FontManager mFontManager;
    @Inject
    TranslateLanguageManager mLanguageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_language);
        ButterKnife.bind(this);

        ((QuickuApplication) getApplication()).getAppComponent().inject(this);

        targetLang = mLanguageManager.getTargetLang();
        sourceLang = mLanguageManager.getSourceLang();

        langList = new ArrayList<>();
        langKeyList = new ArrayList<>();
        adapterList = new ArrayList<>();

        createCustomToolbar();
        setFonts();
        setCheckBoxCheckedListeners();
        setClickListeners();
        prepareJsonData();
        setSourceLangAlpha();
    }

    private void setFonts() {
        tvTranslateLangInfo.setTypeface(mFontManager.getRobotoRegular());
        tvSetSourceLangCaption.setTypeface(mFontManager.getRobotoRegular());
        tvSetTargetLangCaption.setTypeface(mFontManager.getRobotoRegular());
        tvAutoDetectTargetLangInfo.setTypeface(mFontManager.getRobotoRegular());
        tvPoweredByYandex.setTypeface(mFontManager.getRobotoRegular());
        tvAppBarHeader.setTypeface(mFontManager.getRobotoBold());
    }

    private void setSourceLangAlpha() {
        if (mLanguageManager.getSourceLangAutoDetect()) {
            llSourceLangSettingContainer.setAlpha(0.1f);
        } else {
            llSourceLangSettingContainer.setAlpha(1f);
        }
    }

    private void setClickListeners() {
        tvPoweredByYandex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://translate.yandex.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void setCheckBoxCheckedListeners() {
        cbAutoDetectTargetLang.setChecked(mLanguageManager.getSourceLangAutoDetect());
        cbAutoDetectTargetLang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mLanguageManager.setSourceLangAutoDetect(true);
                    llSourceLangSettingContainer.setAlpha(0.1f);
                } else {
                    mLanguageManager.setSourceLangAutoDetect(false);
                    llSourceLangSettingContainer.setAlpha(1f);
                    Toast.makeText(TranslateLanguageActivity.this,
                            getResources().getString(R.string.toast_please_select_source_target_lang), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setSpinnerAdapters() {
        // Adapter for source language
        ArrayAdapter<CharSequence> sourceLangAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_textview, adapterList);
        sourceLangAdapter.setDropDownViewResource(R.layout.spinner_checked_textview);
        spinnerSourceLang.setAdapter(sourceLangAdapter);

        spinnerSourceLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLanguageManager.setSourceLang(langKeyList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Adapter for target language
        ArrayAdapter<CharSequence> targetLangAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_textview, adapterList);
        targetLangAdapter.setDropDownViewResource(R.layout.spinner_checked_textview);
        spinnerTargetLang.setAdapter(targetLangAdapter);

        spinnerTargetLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLanguageManager.setTargetLang(langKeyList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int sourceSpinnerSelectedId = langKeyList.indexOf(sourceLang);
        int targetSpinnerSelectedId = langKeyList.indexOf(targetLang);

        spinnerSourceLang.setSelection(sourceSpinnerSelectedId);
        spinnerTargetLang.setSelection(targetSpinnerSelectedId);
    }

    private void createCustomToolbar() {
        tvAppBarHeader = toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_translate_language));
        setSupportActionBar(toolbar);
    }

    private void prepareJsonData() {
        try {
            String jsonString = loadJSONFromAsset();
            JSONObject jsonLangs = new JSONObject(jsonString);
            HashMap<String, String> langMap = new HashMap<>();
            JSONArray namesArray = jsonLangs.names(); // [en, tr, zh, ar]

            for (int i = 0; i < namesArray.length(); i++) {
                String key = namesArray.getString(i);
                langList.add(jsonLangs.getString(key));
                langMap.put(jsonLangs.getString(key), key);
            }

            Collections.sort(langList, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            for (int k = 0; k < langList.size(); k++) {
                String lang = langList.get(k);
                langKeyList.add(langMap.get(lang));
            }
            adapterList.addAll(langList);

            setSpinnerAdapters();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String jsonString;
        try {
            InputStream is;
            if (Locale.getDefault().getLanguage().equals("en")) {
                is = getAssets().open("jsonfiles/langs_en.json");
            } else if (Locale.getDefault().getLanguage().equals("tr")) {
                is = getAssets().open("jsonfiles/langs_tr.json");
            } else {
                is = getAssets().open("jsonfiles/langs_en.json");
            }

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

            return null;
        }

        return jsonString;
    }
}
