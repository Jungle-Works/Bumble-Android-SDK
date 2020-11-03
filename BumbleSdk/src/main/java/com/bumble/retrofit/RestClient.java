package com.bumble.retrofit;


import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumble.BumbleConfig;
import com.bumble.constants.BumbleAppConstants;
import com.bumble.database.CommonData;
import com.bumblesdk.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rest Client
 */
public class RestClient {

    public static Retrofit retrofit = null;
    public static Retrofit retrofitIP = null;


    /**
     * @return
     */
    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            String baseUrl = CommonData.getServerUrl();
            if (TextUtils.isEmpty(baseUrl.trim()))
                baseUrl = BumbleAppConstants.LIVE_SERVER;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    public static ApiInterface getApiIpInterface() {
        if (retrofitIP == null) {
            String baseUrl = "https://api.ipify.org";

            retrofitIP = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //.baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofitIP.create(ApiInterface.class);
    }

    /**
     * @return
     */
    public static Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            String baseUrl = CommonData.getServerUrl();
            if (TextUtils.isEmpty(baseUrl.trim()))
                baseUrl = BumbleAppConstants.LIVE_SERVER;
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient().build())
                    .build();
        }
        return retrofit;
    }

    /**
     * @return
     */
    private static OkHttpClient.Builder httpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if(BumbleConfig.DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        else
//            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS);

        // add_small your other interceptors
        // add_small logging as last interceptor
        httpClient.addInterceptor(logging);
        return httpClient;
    }


}
