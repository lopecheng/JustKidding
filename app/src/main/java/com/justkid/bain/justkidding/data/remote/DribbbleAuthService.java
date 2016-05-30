package com.justkid.bain.justkidding.data.remote;

import com.justkid.bain.justkidding.data.model.AccessToken;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DribbbleAuthService {
    public static final String ENDPOINT = "https://dribbble.com/";

    @POST("oauth/token")
    Observable<AccessToken> getAccessToken(@Query("client_id") String client_id,
                                           @Query("client_secret") String client_secret,
                                           @Query("code") String code,
                                           @Body String redirect_uri);
}

