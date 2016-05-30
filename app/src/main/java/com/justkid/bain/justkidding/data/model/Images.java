package com.justkid.bain.justkidding.data.model;

import android.text.TextUtils;


public class Images {
    private static final int[] MAIN_IMAGE_SIZE = new int[] {800,800};

    public final String hidpi;
    public final String normal;
    public final String teaser;

    public Images(String hidpi, String normal, String teaser) {
        this.hidpi = hidpi;
        this.normal = normal;
        this.teaser = teaser;
    }

    public String bestQuality() {
        return !TextUtils.isEmpty(hidpi) ? hidpi : normal;
    }

    public int[] imageSize(){return MAIN_IMAGE_SIZE;}
}

