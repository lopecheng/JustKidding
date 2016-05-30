package com.justkid.bain.justkidding.ui.login;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.data.local.DribbblePrefes;
import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements LoginMvpView{
    private static final String TAG = "LoginActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progressBar;
    @Bind(R.id.login_webview)
    WebView webView;

    @Inject
    DribbblePrefes dribbblePrefes;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.loginlayout);
        ButterKnife.bind(this);
        initial();
    }

    public void initial(){
        loginPresenter.attachView(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setResult(Activity.RESULT_CANCELED);
                Toast.makeText(LoginActivity.this, "Login fail!", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar.setTitle("Authorization");
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(DribbblePrefes.oauthUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
                String code;
                super.onPageStarted(view, url, favicon);
                if(url.contains("?code")){
                    Uri uri = Uri.parse(url);
                    code = uri.getQueryParameter("code");
                    loginPresenter.getAccessTokenWithCode(code);
                }
            }
        });
    }

    @Override
    public void setResult(User user) {
        dribbblePrefes.saveLoginedUserInfo(user);
        LoginActivity.this.setResult(Activity.RESULT_OK);
    }

    @Override
    public void showFinish() {
        progressBar.setVisibility(View.GONE);
        LoginActivity.this.finish();
        Toast.makeText(LoginActivity.this,"Login success!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(LoginActivity.this,"Authorication :missing error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

}

