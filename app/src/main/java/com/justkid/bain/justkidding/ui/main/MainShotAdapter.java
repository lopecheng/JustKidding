package com.justkid.bain.justkidding.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.injection.ActivityContext;
import com.justkid.bain.justkidding.util.GlideUtil;
import com.justkid.bain.justkidding.widget.CircleImageView;
import com.justkid.bain.justkidding.widget.SquaredImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainShotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    public List<Shot> shots;

    private int pre_page = 1;

    private OnShotItemClickListener onShotItemClickListener;

    @Inject
    public MainShotAdapter(@ActivityContext Context context){
        this.context = context;
        shots = new ArrayList<>();
    }

    public void setShots(List<Shot> shots){
        this.shots = shots;
    }

    public Shot getListItem(int position){
        return shots.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.shot_listitem,parent,false);
        final MainShotViewHolder mainShotViewHolder = new MainShotViewHolder(view);
        mainShotViewHolder.like_the_shot.setOnClickListener(this);
        mainShotViewHolder.show_the_comment.setOnClickListener(this);
        mainShotViewHolder.show_the_more.setOnClickListener(this);
        return mainShotViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MainShotViewHolder mainShotViewHolder = (MainShotViewHolder) holder;
        GlideUtil.loadImageWithTargetView(context,shots.get(position).user.avatar_url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                mainShotViewHolder.avator.setImageBitmap(resource);
            }
        });
        mainShotViewHolder.name.setText(shots.get(position).user.name);
        mainShotViewHolder.date.setText(DateUtils.getRelativeTimeSpanString(shots.get(position).created_at.getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS));
        GlideUtil.loadImageWithTargetView(context,shots.get(position).images.bestQuality(), new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap  resource, GlideAnimation glideAnimation) {
                mainShotViewHolder.shot_image.setImageBitmap(resource);
            }
        });
        mainShotViewHolder.shot_title.setText(shots.get(position).title);
        mainShotViewHolder.like_the_shot.setTag(position);
        mainShotViewHolder.show_the_comment.setTag(position);
        mainShotViewHolder.show_the_more.setTag(position);
        mainShotViewHolder.the_praise_count.setText(String.valueOf(shots.get(position).likes_count+" likes"));
    }

    @Override
    public int getItemCount() {
        return shots.size()==0?0:shots.size();
    }

    public void addMoreData(List<Shot> datas, int current_page) {
        if (current_page == pre_page) {
            shots = datas;
        } else {
            shots.addAll(datas);
        }
    }


    public void setOnShotItemClickListener(OnShotItemClickListener onShotItemClickListener){
        this.onShotItemClickListener = onShotItemClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.like_the_shot:
                if(onShotItemClickListener != null){
                    onShotItemClickListener.onHeartClick(v, (Integer) v.getTag());
                }
                break;
            case R.id.show_the_comment:
                if(onShotItemClickListener != null){
                    onShotItemClickListener.onCommentsClick(v, (Integer) v.getTag());
                }
                break;
            case R.id.show_the_more:
                if(onShotItemClickListener != null){
                    onShotItemClickListener.onMoreClick(v, (Integer) v.getTag());
                }
                break;
        }
    }

    public static class MainShotViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.profile_avator)
        CircleImageView avator;
        @Bind(R.id.profile_name)
        TextView name;
        @Bind(R.id.shot_created_date)
        TextView date;
        @Bind(R.id.squared_image)
        SquaredImageView shot_image;
        @Bind(R.id.shot_title)
        TextView shot_title;
        @Bind(R.id.like_the_shot)
        ImageButton like_the_shot;
        @Bind(R.id.show_the_comment)
        ImageButton show_the_comment;
        @Bind(R.id.show_the_more)
        ImageButton show_the_more;
        @Bind(R.id.the_praise_count)
        TextView the_praise_count;

        public MainShotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnShotItemClickListener {
        public void onHeartClick(View v,int position);

        public void onCommentsClick(View v, int position);

        public void onMoreClick(View v, int position);
    }
}

