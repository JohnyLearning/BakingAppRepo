package com.ihadzhi.bakingtime.ui.recipe;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityRecipesBinding;
import com.ihadzhi.bakingtime.ui.BaseActivity;
import com.ihadzhi.bakingtime.ui.recipedetail.RecipeDetailActivity;

public class RecipesActivity extends BaseActivity {

    private RecipesViewModel recipesViewModel;
    private RecipesAdapter recipesAdapter;
    private ActivityRecipesBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        recipesAdapter = new RecipesAdapter(this);
        recipesAdapter.setRecipeClickListener(recipe -> {
            Intent recipeDetailIntent = new Intent(RecipesActivity.this, RecipeDetailActivity.class);
            recipeDetailIntent.putExtra(RecipeDetailActivity.RECIPE_PARAM, recipe);
            startActivity(recipeDetailIntent);
        });
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);
        dataBinding.rvRecipes.setAdapter(recipesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                dataBinding.rvRecipes.setLayoutManager(new GridLayoutManager(this, 5));
            } else {
                dataBinding.rvRecipes.setLayoutManager(new GridLayoutManager(this, 3));
            }
        } else {
            dataBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        }
        loadRecipes();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void loadRecipes()  {
        recipesViewModel.getRecipes(this).removeObservers(this);
        recipesViewModel.getRecipes(this).observe(this, recipes -> {
            recipesAdapter.addRecipes(recipes);
        });
    }
}

