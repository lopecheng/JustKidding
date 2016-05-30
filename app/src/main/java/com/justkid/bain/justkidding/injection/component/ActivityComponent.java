package com.justkid.bain.justkidding.injection.component;

import com.justkid.bain.justkidding.injection.PerActivity;
import com.justkid.bain.justkidding.injection.module.ActivityModule;
import com.justkid.bain.justkidding.ui.comment.CommentActivity;
import com.justkid.bain.justkidding.ui.login.LoginActivity;
import com.justkid.bain.justkidding.ui.main.MainActivity;
import com.justkid.bain.justkidding.ui.personal.PersonalPageActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(CommentActivity commentActivity);
    void inject(LoginActivity loginActivity);
    void inject(PersonalPageActivity personalPageActivity);
}
