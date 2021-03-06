package com.ihadzhi.bakingtime.network;

import com.ihadzhi.bakingtime.model.Recipe;

import java.util.List;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingService {

    private final BakingApi bakingApi;

    public BakingService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        this.bakingApi = retrofit.create(BakingApi.class);
    }

    public Single<List<Recipe>> getRecipes() {
        return bakingApi.getRecipes();
    }

}
