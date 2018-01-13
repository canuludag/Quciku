package com.quicku.translate.ui.themeselection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.root.QuickuApplication;
import com.quicku.translate.utils.FontManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeSelectionActivity extends AppCompatActivity implements View.OnClickListener {
    // View injection
    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.tvTheme1DemoText)
    TextView tvTheme1DemoText;
    @BindView(R.id.tvTheme2DemoText)
    TextView tvTheme2DemoText;
    @BindView(R.id.tvTheme3DemoText)
    TextView tvTheme3DemoText;
    @BindView(R.id.tvTheme4DemoText)
    TextView tvTheme4DemoText;
    @BindView(R.id.tvTheme5DemoText)
    TextView tvTheme5DemoText;
    @BindView(R.id.rlTheme1)
    RelativeLayout rlTheme1;
    @BindView(R.id.rlTheme2)
    RelativeLayout rlTheme2;
    @BindView(R.id.rlTheme3)
    RelativeLayout rlTheme3;
    @BindView(R.id.rlTheme4)
    RelativeLayout rlTheme4;
    @BindView(R.id.rlTheme5)
    RelativeLayout rlTheme5;
    @BindView(R.id.ivCheck1)
    ImageView ivCheck1;
    @BindView(R.id.ivCheck2)
    ImageView ivCheck2;
    @BindView(R.id.ivCheck3)
    ImageView ivCheck3;
    @BindView(R.id.ivCheck4)
    ImageView ivCheck4;
    @BindView(R.id.ivCheck5)
    ImageView ivCheck5;
    private TextView tvAppBarHeader;

    @Inject
    SharedPreferences mSharedPrefs;
    @Inject
    SharedPreferences.Editor mPrefsEditor;
    @Inject
    FontManager mFontManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selection);
        ButterKnife.bind(this);

        // Initialize injection for this activity
        ((QuickuApplication) getApplication()).getAppComponent().inject(this);

        createCustomToolbar();
        setFonts();
        setClickListeners();
        setActiveThemeSelection();
        setInitialVisibilities();
    }

    private void setInitialVisibilities() {
        ivCheck1.setVisibility(View.INVISIBLE);
        ivCheck2.setVisibility(View.INVISIBLE);
        ivCheck3.setVisibility(View.INVISIBLE);
        ivCheck4.setVisibility(View.INVISIBLE);
        ivCheck5.setVisibility(View.INVISIBLE);
    }

    private void setActiveThemeSelection() {
        int theme = mSharedPrefs.getInt("translate_card_theme", 0);
        switch (theme) {
            case 0:
                ivCheck1.setVisibility(View.VISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.VISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.VISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.VISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setFonts() {
        tvTheme1DemoText.setTypeface(mFontManager.getRobotoSlabRegular(this));
        tvTheme2DemoText.setTypeface(mFontManager.getRobotoSlabRegular(this));
        tvTheme3DemoText.setTypeface(mFontManager.getRobotoSlabRegular(this));
        tvTheme4DemoText.setTypeface(mFontManager.getRobotoSlabRegular(this));
        tvTheme5DemoText.setTypeface(mFontManager.getRobotoSlabRegular(this));

        tvAppBarHeader.setTypeface(mFontManager.getRobotoBold(this));
    }

    private void setClickListeners() {
        rlTheme1.setOnClickListener(this);
        rlTheme2.setOnClickListener(this);
        rlTheme3.setOnClickListener(this);
        rlTheme4.setOnClickListener(this);
        rlTheme5.setOnClickListener(this);
    }

    private void createCustomToolbar() {
        tvAppBarHeader = (TextView) toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_theme_selection));
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlTheme1:
                mPrefsEditor.putInt("translate_card_theme", 0);
                mPrefsEditor.apply();

                ivCheck1.setVisibility(View.VISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case R.id.rlTheme2:
                mPrefsEditor.putInt("translate_card_theme", 1);
                mPrefsEditor.apply();

                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.VISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case R.id.rlTheme3:
                mPrefsEditor.putInt("translate_card_theme", 2);
                mPrefsEditor.apply();

                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.VISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case R.id.rlTheme4:
                mPrefsEditor.putInt("translate_card_theme", 3);
                mPrefsEditor.apply();

                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.VISIBLE);
                ivCheck5.setVisibility(View.INVISIBLE);
                break;
            case R.id.rlTheme5:
                mPrefsEditor.putInt("translate_card_theme", 4);
                mPrefsEditor.apply();

                ivCheck1.setVisibility(View.INVISIBLE);
                ivCheck2.setVisibility(View.INVISIBLE);
                ivCheck3.setVisibility(View.INVISIBLE);
                ivCheck4.setVisibility(View.INVISIBLE);
                ivCheck5.setVisibility(View.VISIBLE);
                break;
        }
    }
}
