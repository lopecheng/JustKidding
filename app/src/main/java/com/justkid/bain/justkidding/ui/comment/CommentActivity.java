package com.justkid.bain.justkidding.ui.comment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.ui.base.BaseActivity;
import com.justkid.bain.justkidding.util.DetectNetworkStatus;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentActivity extends BaseActivity implements CommentMvpView{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rvComments)
    RecyclerView rvComments;
    @Bind(R.id.send_comment_et)
    EditText sendCommentEt;
    @Bind(R.id.send_comment_bt)
    Button sendCommentBt;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    CommentPresenter commentPresenter;
    @Inject CommentAdapter commentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisableItem;
    private long shotId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.comment_activity);
        ButterKnife.bind(this);
        initial();
        shotId = getIntent().getLongExtra("shotId",0);
        commentPresenter.getComment(shotId);
    }

    private void initial() {
        commentPresenter.attachView(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("Comment");
        linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(commentAdapter);
        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisableItem == commentAdapter.comments.size()-1){
                    if(DetectNetworkStatus.isNetworkConnected(CommentActivity.this)){
                        commentPresenter.getComment(shotId);
                    }else {
                        Toast.makeText(CommentActivity.this,"The network didn't connect",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisableItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadFinish() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this,"show comments missing problem.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComments(List<Comment> comments,int page) {
        if (comments.isEmpty()) {
            Toast.makeText(this,"There is no more comments !",Toast.LENGTH_SHORT).show();
        } else{
            commentAdapter.addMoreData(comments, page);
            commentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmptyComment(int page) {
        commentAdapter.addMoreData(Collections.<Comment>emptyList(),page);
        Toast.makeText(this,"There is no more comment.",Toast.LENGTH_LONG).show();
    }
}