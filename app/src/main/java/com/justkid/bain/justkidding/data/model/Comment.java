package com.justkid.bain.justkidding.data.model;

import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.justkid.bain.justkidding.util.DribbbleUtils;

import java.util.Date;


public class Comment {
    public final long id;
    public final String body;
    public long likes_count;
    public final String likes_url;
    public final Date created_at;
    public final Date updated_at;
    public final User user;

    public Spanned parsedBody;

    public Comment(long id,
                   String body,
                   long likes_count,
                   String likes_url,
                   Date created_at,
                   Date updated_at,
                   User user){
        this.id = id;
        this.body = body;
        this.likes_count = likes_count;
        this.likes_url = likes_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public Spanned getParsedBody(TextView textView) {
        if (parsedBody == null && !TextUtils.isEmpty(body)) {
            parsedBody = DribbbleUtils.parseDribbbleHtml(body, textView.getLinkTextColors(),
                    textView.getHighlightColor());
        }
        return parsedBody;
    }
}
