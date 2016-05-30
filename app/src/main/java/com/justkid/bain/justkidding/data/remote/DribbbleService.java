package com.justkid.bain.justkidding.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.justkid.bain.justkidding.data.interceptor.OkHttpClientUtil;
import com.justkid.bain.justkidding.data.local.DribbblePrefes;
import com.justkid.bain.justkidding.data.model.AccessToken;
import com.justkid.bain.justkidding.data.model.Comment;
import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.data.model.User;
import com.justkid.bain.justkidding.injection.ApplicationContext;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface DribbbleService {
    String ENDPOINT = "https://api.dribbble.com/v1/";
    String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss Z";
    public static final int PAGE_NUM = 1;
    public static final int PER_PAGE = 10;

    @GET("shots")
    Observable<List<Shot>> getPopular(@Query("access_token") String accessToken,
                                      @Query("page") Integer page,
                                      @Query("per_page") Integer pageSize);

    @GET("shots/{id}/comments")
    Observable<List<Comment>> getShotComment(@Path("id") long shotId,
                                             @Query("access_token") String accessToken,
                                             @Query("page") Integer page,
                                             @Query("per_page") Integer pageSize);

    @POST("https://dribbble.com/oauth/token")
    Observable<AccessToken> getAccessTokenWithCode(@Query("client_id")String client_id,
                                                   @Query("client_secret")String client_secret,
                                                   @Query("code")String code,
                                                   @Query("redirect_uri")String redirect_uri);

    @GET("user")
    Observable<User> getUserWithAccessToken(@Query("access_token")String access_token);


    class Creator{
        public static DribbbleService newDribbbleService(@ApplicationContext Context context){
            Gson gson = new GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DribbbleService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClientUtil(new DribbblePrefes(context)).getClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(DribbbleService.class);
        }
    }
}

