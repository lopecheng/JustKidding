package com.justkid.bain.justkidding.injection.component;

import android.app.Application;
import android.content.Context;

import com.justkid.bain.justkidding.data.remote.DribbbleService;
import com.justkid.bain.justkidding.injection.ApplicationContext;
import com.justkid.bain.justkidding.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Application application();
    @ApplicationContext
    Context context();

    DribbbleService dribbbleService();
}
