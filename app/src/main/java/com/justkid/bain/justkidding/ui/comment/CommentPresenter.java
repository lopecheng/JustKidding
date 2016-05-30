package com.justkid.bain.justkidding.ui.comment;

import com.justkid.bain.justkidding.data.DataManager;
import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CommentPresenter extends BasePresenter<CommentMvpView> {
    private DataManager dataManager;
    private Subscription subscription;
    private int page = 1;
    private int per_page = 10;

    @Inject
    public CommentPresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(CommentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    public void getCommentFromService(long shotId, final int page, int per_page){
        subscription = dataManager.getShotComment(shotId,page,per_page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().loadFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        if(comments.isEmpty()){
                            getMvpView().showEmptyComment(page);
                        }else{
                            getMvpView().showComments(comments,page);
                        }
                    }

                    @Override
                    public void onStart() {
                        getMvpView().showLoading();
                    }
                });
    }

    public void getComment(long shotId){
        getCommentFromService(shotId,page,per_page);
        page +=1;
    }
}

