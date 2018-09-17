package ru.sav.cloudclient.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientBuilder {
    private static final String FLICKR_API_BASE_URL = "https://api.flickr.com/";
    private Gson gson = new GsonBuilder().create();

    public <S> S createService(Class<S> serviceClass) {
        return new Retrofit.Builder()
                .baseUrl(FLICKR_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(serviceClass);
    }
}
