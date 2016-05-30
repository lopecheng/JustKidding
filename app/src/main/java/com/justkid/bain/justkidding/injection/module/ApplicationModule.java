package com.justkid.bain.justkidding.injection.module;

import android.app.Application;
import android.content.Context;

import com.justkid.bain.justkidding.JustKiddingApp;
import com.justkid.bain.justkidding.data.remote.DribbbleService;
import com.justkid.bain.justkidding.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private final JustKiddingApp justKidApplication;
    public ApplicationModule(JustKiddingApp justKidApplication){
        this.justKidApplication = justKidApplication;
    }

    @Provides
    Application provideApplication(){ return justKidApplication; }

    @Provides
    @ApplicationContext
    Context provideContext(){ return justKidApplication; }

    @Provides
    @Singleton
    DribbbleService provideDribbbleService(){
        return DribbbleService.Creator.newDribbbleService(justKidApplication);
    }

}

