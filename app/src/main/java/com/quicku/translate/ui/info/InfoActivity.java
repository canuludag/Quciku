package com.quicku.translate.ui.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.quicku.translate.R;
import com.quicku.translate.utils.FontManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        createCustomToolbar();
        setFonts();
    }

    private void setFonts() {
        tvHowToUseQuickuCaption.setTypeface(FontManager.getRobotoBold(this));
        tvWhichLanguagesSupportedCaption.setTypeface(FontManager.getRobotoBold(this));
        tvHowToUseQuickuText.setTypeface(FontManager.getRobotoRegular(this));
        tvWhichLanguagesSupportedText.setTypeface(FontManager.getRobotoRegular(this));
    }

    private void createCustomToolbar() {
        TextView tvAppBarHeader = toolbar.findViewById(R.id.tvAppBarHeader);
        tvAppBarHeader.setTypeface(FontManager.getRobotoBold(this));
        tvAppBarHeader.setText(getResources().getString(R.string.toolbar_header_info));
        setSupportActionBar(toolbar);
    }

}
