package com.justkid.bain.justkidding.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_layout);

        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long,Long>() {

                    @Override
                    public Long call(Long aLong) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                        return aLong;
                    }
                }).subscribe();
    }
}
