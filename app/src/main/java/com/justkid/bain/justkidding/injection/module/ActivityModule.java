package com.justkid.bain.justkidding.injection.module;

import android.app.Activity;
import android.content.Context;

import com.justkid.bain.justkidding.injection.ActivityContext;
import com.justkid.bain.justkidding.injection.PerActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private Activity activity;
    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity(){ return activity; }

    @Provides
    @ActivityContext
    @PerActivity
    Context provideContext(){ return activity; }

}
