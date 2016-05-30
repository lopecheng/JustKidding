package com.justkid.bain.justkidding.ui.comment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.injection.ActivityContext;
import com.justkid.bain.justkidding.util.GlideUtil;
import com.justkid.bain.justkidding.util.HtmlUtils;
import com.justkid.bain.justkidding.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public List<Comment> comments;

    private int pre_page=1;

    @Inject
    public CommentAdapter(@ActivityContext Context context){
        this.context = context;
        comments = new ArrayList<>();
    }

    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.comment_listitem,parent,false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        //int bodyLength = comments.get(position).body.length();
        GlideUtil.loadImageWithTargetView(context,comments.get(position).user.avatar_url, new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                commentViewHolder.comment_avator.setImageBitmap(resource);
            }
        });
        //commentViewHolder.comment_description.setText(comments.get(position).body.substring(3,bodyLength-4));
        HtmlUtils.setTextWithNiceLinks(commentViewHolder.comment_description,comments.get(position).getParsedBody(commentViewHolder.comment_description));
        commentViewHolder.comment_date.setText(DateUtils.getRelativeTimeSpanString(comments.get(position).created_at.getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS));
    }

    @Override
    public int getItemCount() {
        return comments.size()==0?0:comments.size();
    }

    public void addMoreData(List<Comment> datas, int current_page) {
        if (current_page == pre_page) {
            comments = datas;
        } else {
            comments.addAll(datas);
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.comment_avator)
        CircleImageView comment_avator;
        @Bind(R.id.comment_description)
        TextView comment_description;
        @Bind(R.id.comment_date)
        TextView comment_date;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

