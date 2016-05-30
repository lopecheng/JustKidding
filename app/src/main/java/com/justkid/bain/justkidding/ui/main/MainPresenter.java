package com.justkid.bain.justkidding.ui.main;

import android.util.Log;

import com.justkid.bain.justkidding.data.DataManager;
import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.data.remote.DribbbleService;
import com.justkid.bain.justkidding.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainPresenter extends BasePresenter<MainMvpView> {
    private DataManager dataManager;
    private Subscription subscription;
    private int per_page = 10;
    private int page = 2;

    @Inject
    public MainPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(subscription != null) subscription.unsubscribe();
    }

    public void loadShots(final int page, int per_page){
        checkViewAttached();
        subscription = dataManager.getPopularDribbble(page, per_page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().loadFinish();
                        getMvpView().loadTheNewestFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                        getMvpView().loadFinish();
                        getMvpView().loadTheNewestFinish();
                        Log.e("MainPresenter", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        if(shots.isEmpty()) {
                            getMvpView().showEmptyShots();
                        }else {
                            getMvpView().showShots(shots,page);
                        }
                    }

                    @Override
                    public void onStart() {
                        getMvpView().showLoading();
                    }
                });
    }

    public void loadTheNewestShots(final int page, int per_page){
        checkViewAttached();
        subscription = dataManager.getPopularDribbble(page, per_page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().loadFinish();
                        getMvpView().loadTheNewestFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                        getMvpView().loadTheNewestFinish();
                        getMvpView().loadFinish();
                        Log.e("MainPresenter", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        if(shots.isEmpty()) {
                            getMvpView().showEmptyShots();
                        }else {
                            if(shots != null && (shots.get(0).id == (getMvpView().getFirstShotId()))){
                                getMvpView().showIsNewest();
                            }else{
                                getMvpView().showShots(shots,page);
                            }
                        }
                    }

                    @Override
                    public void onStart() {
                        getMvpView().showLoading();
                    }
                });
    }

    public void loadNewestShots() {
        loadShots(DribbbleService.PAGE_NUM, DribbbleService.PER_PAGE);
    }

    public void loadMoreShots(){
        loadShots(page,per_page);
        page+=1;
    }
}

