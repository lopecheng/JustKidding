package com.justkid.bain.justkidding.ui.base;


public interface Presenter<V extends BaseMvpView>{
    public void attachView(V view);
    public void detachView();
}
