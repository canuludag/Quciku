package com.quicku.translate.ui.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.root.QuickuApplication;
import com.quicku.translate.utils.FontManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    // View injection
    @BindView(R.id.tvHowToUseQuickuCaption)
    TextView tvHowToUseQuickuCaption;
    @BindView(R.id.tvHowToUseQuickuText)
    TextView tvHowToUseQuickuText;
    @BindView(R.id.tvWhichLanguagesSupportedCaption)
    TextView tvWhichLanguagesSupportedCaption;
    @BindView(R.id.tvWhichLanguagesSupportedText)
    TextView tvWhichLanguagesSupportedText;
    @BindView(R.id.app_bar)
    Toolbar toolbar;

    @Inject
    FontManager mFontManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        ((QuickuApplication) getApplication()).getAppComponent().inject(this);

        createCustomToolbar();
        setFonts();
    }

    private void setFonts() {
        tvHowToUseQuickuCaption.setTypeface(mFontManager.getRobotoBold(this));
        tvWhichLanguagesSupportedCaption.setTypeface(mFontManager.getRobotoBold(this));
        tvHowToUseQuickuText.setTypeface(mFontManager.getRobotoRegular(this));
        tvWhichLanguagesSupportedText.setTypeface(mFontManager.getRobotoRegular(this));
    }

    private void createCustomToolbar() {
        TextView tvAppBarHeader = toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setTypeface(mFontManager.getRobotoBold(this));
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_info));
        setSupportActionBar(toolbar);
    }

}
