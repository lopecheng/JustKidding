package com.justkid.bain.justkidding.data.interceptor;

import com.justkid.bain.justkidding.data.local.DribbblePrefes;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor{
    private String accessToken;
    private DribbblePrefes dribbblePrefes;

    @Inject
    public AuthorizationInterceptor(DribbblePrefes prefes){
        dribbblePrefes = prefes;
        accessToken = dribbblePrefes.getAccessToken();
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.newBuilder()
                .header("Authorization","Bearer "+accessToken)
                .build();
        return chain.proceed(request);
    }
}
