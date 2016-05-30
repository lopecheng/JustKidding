package com.justkid.bain.justkidding;

import android.app.Application;
import android.content.Context;

import com.justkid.bain.justkidding.injection.component.ApplicationComponent;
import com.justkid.bain.justkidding.injection.component.DaggerApplicationComponent;
import com.justkid.bain.justkidding.injection.module.ApplicationModule;


public class JustKiddingApp extends Application{
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static JustKiddingApp get(Context context){
        return (JustKiddingApp) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent(){
        if(applicationComponent == null){
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
