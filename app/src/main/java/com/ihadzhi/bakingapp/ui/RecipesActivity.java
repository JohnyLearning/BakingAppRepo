package com.ihadzhi.bakingapp.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.ihadzhi.bakingapp.R;
import com.ihadzhi.bakingapp.databinding.ActivityRecipesBinding;

public class RecipesActivity extends BaseActivity {

    private RecipesViewModel recipesViewModel;
    private ActivityRecipesBinding dataBinding;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipes);
        recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        recipesAdapter = new RecipesAdapter(this);
        dataBinding.rvRecipes.setAdapter(recipesAdapter);
        dataBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        loadRecipes();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadRecipes()  {
        recipesViewModel.getRecipes(this).removeObservers(this);
        recipesViewModel.getRecipes(this).observe(this, recipes -> {
            Log.d("THIS", String.valueOf(recipes));
            recipesAdapter.addRecipes(recipes);
        });
    }
}

