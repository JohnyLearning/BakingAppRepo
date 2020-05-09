package com.ihadzhi.bakingapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ihadzhi.bakingapp.model.OnError;
import com.ihadzhi.bakingapp.model.Recipe;
import com.ihadzhi.bakingapp.network.BakingService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipes;

    private final BakingService bakingService;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        bakingService = new BakingService();
    }

    public LiveData<List<Recipe>> getRecipes(OnError onError) {
        if (recipes == null) {
            recipes = new MutableLiveData<>();
            fetchRecipes(onError);
        }
        return recipes;
    }

    private void fetchRecipes(OnError errorCallback) {
        bakingService.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<Recipe> liveRecipes = this.recipes.getValue();
                    if (liveRecipes == null || liveRecipes.isEmpty()) {
                        this.recipes.setValue(response);
                    } else {
                        this.recipes.setValue(liveRecipes);
                    }
                }, error -> {
                    if (errorCallback != null) {
                        errorCallback.error(error);
                    }
                });
    }
}
