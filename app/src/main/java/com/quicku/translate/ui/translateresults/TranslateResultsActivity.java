package com.quicku.translate.ui.translateresults;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quicku.translate.R;
import com.quicku.translate.databases.LastTranslatedWordsDatabase;
import com.quicku.translate.models.LanguageDetectResponse;
import com.quicku.translate.models.LanguageTranslationResponse;
import com.quicku.translate.networking.TranslateApiService;
import com.quicku.translate.root.QuickuApplication;
import com.quicku.translate.ui.home.HomeActivity;
import com.quicku.translate.ui.translatelanguages.TranslateLanguageActivity;
import com.quicku.translate.utils.Constants;
import com.quicku.translate.utils.FontManager;
import com.quicku.translate.utils.InternetConnectivity;
import com.quicku.translate.utils.TranslateCardThemeManager;
import com.quicku.translate.utils.TranslateLanguageManager;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranslateResultsActivity extends AppCompatActivity implements View.OnClickListener {
    // View injection
    @BindView(R.id.tvTranslateResult)
    TextView tvTranslateResult;
    @BindView(R.id.tvErrorText)
    TextView tvErrorText;
    @BindView(R.id.tvPoweredByYandex)
    TextView tvPoweredByYandex;
    @BindView(R.id.btnErrorOpenSettings)
    Button btnErrorOpenSettings;
    @BindView(R.id.llMainContainer)
    LinearLayout llMainContainer;
    @BindView(R.id.rlResultCard)
    RelativeLayout rlResultCard;
    @BindView(R.id.rlErrorArea)
    RelativeLayout rlErrorArea;

    private TranslateLanguageManager translateLanguageManager;

    private LastTranslatedWordsDatabase lastTranslatedWordsDatabase;

    // Api service injection
    @Inject
    TranslateApiService mApiService;
    // Card theme manager injection
    @Inject
    TranslateCardThemeManager mCardThemeManager;
    @Inject
    FontManager mFontManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_translate_results);
        ButterKnife.bind(this);

        // Initialize injection for this activity
        ((QuickuApplication) getApplication()).getAppComponent().inject(this);

        lastTranslatedWordsDatabase = new LastTranslatedWordsDatabase(this);
        lastTranslatedWordsDatabase.openDatabase();

        setFonts();
        setClickListeners();
        setCardThemeSettings();
        handleSharedIntentFromAnotherAppIfExist();
    }

    private void setCardThemeSettings() {
        translateLanguageManager = new TranslateLanguageManager(this);

        rlResultCard.setVisibility(View.INVISIBLE);
        rlErrorArea.setVisibility(View.INVISIBLE);
        rlErrorArea.setEnabled(false);

        mCardThemeManager.setActiveTheme(rlResultCard, tvTranslateResult);
    }

    private void setFonts() {
        tvTranslateResult.setTypeface(mFontManager.getRobotoSlabRegular(this));
        tvErrorText.setTypeface(mFontManager.getRobotoRegular(this));
        tvPoweredByYandex.setTypeface(mFontManager.getRobotoRegular(this));
    }

    private void setClickListeners() {
        rlResultCard.setOnClickListener(this);
        btnErrorOpenSettings.setOnClickListener(this);
        llMainContainer.setOnClickListener(this);
        tvPoweredByYandex.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlResultCard:
                finish();
                break;
            case R.id.btnErrorOpenSettings:
                Intent iTransLangSettings = new Intent(getApplicationContext(), TranslateLanguageActivity.class);
                startActivity(iTransLangSettings);
                break;
            case R.id.llMainContainer:
                finish();
                break;
            case R.id.tvPoweredByYandex:
                String url = "http://translate.yandex.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }

    private void handleSharedIntentFromAnotherAppIfExist() {
        if (getIntent() != null) {
            // Get intent, action and MIME type
            Intent intent = getIntent();
            String action = intent.getAction();
            String type = intent.getType();

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    String receivedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (InternetConnectivity.isConnected(getApplicationContext())) {
                        if (receivedText.trim().length() > 0) {
                            String cleanText = cleanSharedTranslateText(receivedText);
                            if (cleanText.length() <= 40) {
                                if (translateLanguageManager.getSourceLangAutoDetect()) {
                                    detectHighlightedTextLang(cleanText);
                                } else {
                                    translateHighlightedText(cleanText);
                                }
                            } else {
                                Toast.makeText(TranslateResultsActivity.this,
                                        getResources().getString(R.string.toast_receivedtext_is_too_long), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(TranslateResultsActivity.this,
                                    getResources().getString(R.string.toast_receivedtext_is_empty), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TranslateResultsActivity.this,
                                getResources().getString(R.string.toast_no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Intent iSecond = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(iSecond);
                finish();
            }
        } else {
            Intent iSecond = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(iSecond);
            finish();
        }
    }

    private void detectHighlightedTextLang(final String text) {
        mApiService.detectLanguage(Constants.getYandexTranslateApiKey(), text)
                .enqueue(new Callback<LanguageDetectResponse>() {
            @Override
            public void onResponse(Call<LanguageDetectResponse> call, Response<LanguageDetectResponse> response) {
                LanguageDetectResponse languageDetectResponse = response.body();
                if (languageDetectResponse != null && response.isSuccessful() && response.code() == 200) {
                    translateLanguageManager.setSourceLang(languageDetectResponse.getLang());
                    translateHighlightedText(text);
                }

            }

            @Override
            public void onFailure(Call<LanguageDetectResponse> call, Throwable t) {
                Toast.makeText(TranslateResultsActivity.this,
                        R.string.toast_cannot_detect_lang, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void translateHighlightedText(final String text) {
        final String targetLang = translateLanguageManager.getTargetLang();
        final String sourceLang = translateLanguageManager.getSourceLang();
        String lang = sourceLang + "-" + targetLang;

        threadTimeout.start();

        mApiService.translateLanguage(Constants.getYandexTranslateApiKey(), text, lang)
                .enqueue(new Callback<LanguageTranslationResponse>() {
            @Override
            public void onResponse(Call<LanguageTranslationResponse> call, Response<LanguageTranslationResponse> response) {
                LanguageTranslationResponse languageTranslationResponse = response.body();
                if (languageTranslationResponse != null && response.isSuccessful() && response.code() == 200) {
                    String translatedText = languageTranslationResponse.getText().get(0);
                    if (!text.equals(translatedText)) {
                        rlResultCard.setVisibility(View.VISIBLE);
                        String resultText = text + " = " + translatedText;
                        tvTranslateResult.setText(resultText);

                        HashMap<String, String> queryValues = new HashMap<>();
                        queryValues.put("text", resultText);

                        lastTranslatedWordsDatabase.insertWord("words", queryValues);
                        lastTranslatedWordsDatabase.close();
                        threadCardDisplay.start();
                    } else {
                        if (!sourceLang.equals(targetLang)) {
                            Toast.makeText(TranslateResultsActivity.this,
                                    getResources().getString(R.string.toast_cant_translate_the_word), Toast.LENGTH_SHORT).show();
                        } else {
                            rlErrorArea.setEnabled(true);
                            rlErrorArea.setVisibility(View.VISIBLE);
                            threadCardDisplay.start();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LanguageTranslationResponse> call, Throwable t) {
                Toast.makeText(TranslateResultsActivity.this,
                        getResources().getString(R.string.toast_an_error_occured), Toast.LENGTH_SHORT).show();
            }
        });
    }

    Thread threadCardDisplay = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Thread threadTimeout = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(8000);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private String cleanSharedTranslateText(String sharedText) {
        String cleanText = sharedText;
        if (cleanText.contains("#")) {
            cleanText = cleanText.replaceAll("#([A-Za-z0-9_-]+)", "");
        }

        if (cleanText.contains("@")) {
            cleanText = cleanText.replaceAll("@([A-Za-z0-9_-]+)", "");
        }

        if (cleanText.contains("http")) {
            cleanText = cleanText.replaceAll("\\?", "").replaceAll("\\&", "");

            String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(cleanText);
            int i = 0;
            while (m.find()) {
                cleanText = cleanText.replaceAll(m.group(i), "").trim();
                i++;
            }
        }

        char[] charArray = cleanText.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                sb.append(c);
            }
        }
        cleanText = sb.toString().toLowerCase();

        return cleanText;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // too important!!!
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
