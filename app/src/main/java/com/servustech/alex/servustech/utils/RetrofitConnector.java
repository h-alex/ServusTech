package com.servustech.alex.servustech.utils;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnector {
    private static final String URL = "https://api.foursquare.com/v2/venues/";
    private static final String CLIENT_ID = "LZGSXOHYCY2T5OEROLIAW4XPK4XY1TALXQUDTBN15ZSS3SPZ";
    private static final String CLIENT_SECRET = "KOXCULTUGRCHJ4Z3BVD3WCVYU12WECZRMXSDED05MFACHUAR";
    private static final String VERSION_UP_TO = "20171206";

    private static RetrofitConnector connector;

    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private RetrofitConnector() {
        Retrofit.Builder builder = new Retrofit.Builder(); // get a builder
        builder.baseUrl(URL); // set the corresponding URL
        builder.addConverterFactory(GsonConverterFactory.create()); // add a decoder for the JSON response
        builder.client(getClient());

        retrofit = builder.build();
    }

    public static RetrofitConnector getInstance() {
        if (connector == null) {
            connector = new RetrofitConnector();
        }
        return connector;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url();
                        HttpUrl newHttpUrl = httpUrl.newBuilder()
                                .addQueryParameter("client_id", CLIENT_ID)
                                .addQueryParameter("client_secret", CLIENT_SECRET)
                                .addQueryParameter("v", VERSION_UP_TO)
                                .build();

                        Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();
    }
}
