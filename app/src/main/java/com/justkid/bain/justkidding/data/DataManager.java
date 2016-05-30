package com.justkid.bain.justkidding.data;

import com.justkid.bain.justkidding.BuildConfig;
import com.justkid.bain.justkidding.data.local.DribbblePrefes;
import com.justkid.bain.justkidding.data.model.AccessToken;
import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.data.remote.DribbbleService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class DataManager {
    private DribbbleService dribbbleService;
    private DribbblePrefes dribbblePrefes;

    @Inject
    public DataManager(DribbbleService dribbbleService,DribbblePrefes dribbblePrefes) {
        this.dribbbleService = dribbbleService;
        this.dribbblePrefes = dribbblePrefes;
    }

    public Observable<List<Shot>> getPopularDribbble(int page, int per_page){
        return dribbbleService.getPopular(BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN,page,per_page);
    }

    public Observable<List<Comment>> getShotComment(long id, int page, int per_page){
        return dribbbleService.getShotComment(id,BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN, page, per_page);
    }


    public Observable<String> getAccessToken(String code){
        return dribbbleService.getAccessTokenWithCode(BuildConfig.DRIBBBLE_CLIENT_ID,
                BuildConfig.DRIBBBLE_CLIENT_SECRET,
                code,
                BuildConfig.REDIRECT_URI)
                .flatMap(new Func1<AccessToken, Observable<String>>() {
                    @Override
                    public Observable<String> call(AccessToken accessToken) {
                        dribbblePrefes.saveAccessToken(accessToken.getAccess_token());
                        return Observable.just(accessToken.getAccess_token());
                    }
                });
    }

    public Observable<User> getUser(String accesstoken){
        return dribbbleService.getUserWithAccessToken(accesstoken)
                .map(new Func1<User, User>() {
                    @Override
                    public User call(User user) {
                        dribbblePrefes.saveLoginedUserInfo(user);
                        return user;
                    }
                });
    }
}

