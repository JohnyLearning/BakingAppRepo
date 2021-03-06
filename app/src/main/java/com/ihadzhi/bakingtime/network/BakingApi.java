package com.ihadzhi.bakingtime.network;

import com.ihadzhi.bakingtime.model.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

interface BakingApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<Recipe>> getRecipes();
}
