package com.justkid.bain.justkidding.ui.main;

import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.ui.base.BaseMvpView;

import java.util.List;


public interface MainMvpView extends BaseMvpView {
    public void showLoading();

    public void loadFinish();

    public void loadTheNewestFinish();

    public void showError();

    public void showShots(List<Shot> shots,int page);

    public void showEmptyShots();

    public void showIsNewest();

    public long getFirstShotId();
}
