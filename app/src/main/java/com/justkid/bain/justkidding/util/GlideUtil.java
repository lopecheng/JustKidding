package com.justkid.bain.justkidding.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.justkid.bain.justkidding.R;


public class GlideUtil {
    public static void loadImageWithTargetView(Context context,String url, SimpleTarget simpleTarget){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.shot_image_placeholder)
                .into(simpleTarget);
    }
}
