package com.justkid.bain.justkidding.ui.comment;

import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.ui.base.BaseMvpView;

import java.util.List;


public interface CommentMvpView extends BaseMvpView {
    public void showLoading();

    public void loadFinish();

    public void showError();

    public void showComments(List<Comment> comments,int page);

    public void showEmptyComment(int page);
}