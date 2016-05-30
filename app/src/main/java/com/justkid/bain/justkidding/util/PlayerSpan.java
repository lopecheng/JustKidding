package com.justkid.bain.justkidding.util;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.justkid.bain.justkidding.ui.personal.PersonalPageActivity;

import in.uncod.android.bypass.style.TouchableUrlSpan;


public class PlayerSpan extends TouchableUrlSpan {

    private final String playerName;
    private final long playerId;
    private final String playerUsername;

    public PlayerSpan(String url,
                      String playerName,
                      long playerId,
                      @Nullable String playerUsername,
                      ColorStateList textColor,
                      int pressedBackgroundColor) {
        super(url, textColor, pressedBackgroundColor);
        this.playerName = playerName;
        this.playerId = playerId;
        this.playerUsername = playerUsername;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), PersonalPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","Personal");
        bundle.putString("figure_id",String.valueOf(playerId));
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }

//    public static final Creator<PlayerSpan> CREATOR = new Creator<PlayerSpan>() {
//    @Override
//    public PlayerSpan createFromParcel(Parcel in) {
//        return new PlayerSpan(in);
//    }
//
//    @Override
//    public PlayerSpan[] newArray(int size) {
//        return new PlayerSpan[size];
//    }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(playerName);
//        dest.writeLong(playerId);
//        dest.writeString(playerUsername);
//    }
//
//    public PlayerSpan(Parcel in) {
//        playerName = in.readString();
//        playerId = in.readLong();
//        playerUsername = in.readString();
//    }
}
