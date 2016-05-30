package com.justkid.bain.justkidding.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.justkid.bain.justkidding.JustKiddingApp;
import com.justkid.bain.justkidding.injection.component.ActivityComponent;
import com.justkid.bain.justkidding.injection.component.DaggerActivityComponent;
import com.justkid.bain.justkidding.injection.module.ActivityModule;


public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(JustKiddingApp.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }
}

