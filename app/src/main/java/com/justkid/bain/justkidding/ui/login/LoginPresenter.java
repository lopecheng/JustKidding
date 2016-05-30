package com.justkid.bain.justkidding.ui.login;

import android.util.Log;

import com.justkid.bain.justkidding.data.DataManager;
import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private static final String TAG = "BasePresent";
    private DataManager dataManager;
    private Subscription subscription;

    @Inject
    public LoginPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(subscription != null) subscription.unsubscribe();
    }

    public void getAccessTokenWithCode(String code){
        subscription = dataManager.getAccessToken(code)
                .flatMap(new Func1<String, Observable<User>>() {
                    @Override
                    public Observable<User> call(String s) {
                        return dataManager.getUser(s);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getMessage());
                        getMvpView().showFinish();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(User user) {
                        getMvpView().setResult(user);
                    }

                    @Override
                    public void onStart() {
                        getMvpView().showLoading();
                    }
                });

    }
}

