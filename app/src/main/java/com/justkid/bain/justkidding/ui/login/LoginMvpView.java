package com.justkid.bain.justkidding.ui.login;

import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.ui.base.BaseMvpView;


public interface LoginMvpView extends BaseMvpView {
    public void setResult(User user);
    public void showFinish();
    public void showError();
    public void showLoading();
}
