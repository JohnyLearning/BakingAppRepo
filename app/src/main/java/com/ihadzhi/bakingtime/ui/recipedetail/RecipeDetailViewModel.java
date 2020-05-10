package com.ihadzhi.bakingtime.ui.recipedetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ihadzhi.bakingtime.model.Recipe;

class RecipeDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Recipe> recipe;

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
    }


}
