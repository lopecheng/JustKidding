package com.justkid.bain.justkidding.data.interceptor;

import android.util.Log;

import com.justkid.bain.justkidding.data.local.DribbblePrefes;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpClientUtil {
    private DribbblePrefes dribbblePrefes;

    @Inject
    public OkHttpClientUtil(DribbblePrefes dribbblePrefes){
        this.dribbblePrefes = dribbblePrefes;
    }
    private static final String TAG = "OkHttpClientUtil";
    public OkHttpClient getClient(){
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
        SSLSocketFactory sslSocketFactory = null;

        try {
            // 这里直接创建一个不做证书串验证的TrustManager
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return new OkHttpClient.Builder()
                // AuthorizationInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
                .addInterceptor(logging)
                .addInterceptor(new AuthorizationInterceptor(dribbblePrefes))
                // 连接超时时间设置
                .connectTimeout(60, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory)
                .build();
    }
}
