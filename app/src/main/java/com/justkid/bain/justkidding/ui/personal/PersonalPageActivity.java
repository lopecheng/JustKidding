package com.justkid.bain.justkidding.ui.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalPageActivity extends BaseActivity{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.personal_page_webview)
    WebView webView;

    private String title;
    private String mFigureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.personal_page);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        mFigureId = bundle.getString("figure_id");
        initial();
    }

    public void initial(){
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        webView.loadUrl("https://dribbble.com/"+mFigureId);
    }
}
