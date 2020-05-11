package com.ihadzhi.bakingtime.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.ihadzhi.bakingtime.model.Ingredient;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.ui.BaseActivity;
import com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity;

import java.util.List;

import static com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity.SELECTED_STEP_PARAM;

public class RecipeDetailActivity extends BaseActivity {

    public static final String RECIPE_PARAM = "recipe";

    private ActivityRecipeDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        if (getIntent() != null) {
            Recipe recipe = getIntent().getParcelableExtra(RECIPE_PARAM);
            if (recipe != null) {
                decorateIngredients(recipe);
                decorateSteps(recipe);
            }
        }
    }

    private void decorateIngredients(Recipe recipe) {
        if (recipe != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(recipe.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients != null && ingredients.size() > 0) {
                for (Ingredient ingredient : ingredients) {
                    dataBinding.ingredientsList.append(
                            new StringBuilder(ingredient.getIngredient())
                                    .append(" (")
                                    .append(ingredient.getQuantity())
                                    .append(' ')
                                    .append(ingredient.getMeasure())
                                    .append(')')
                                    .append('\n'));
                }
            }
        }
    }

    private void decorateSteps(Recipe recipe) {
        RecipeDetailStepsAdapter adapter = new RecipeDetailStepsAdapter(this, recipe.getSteps());
        adapter.setStepClickListener(stepSelectedIndex -> {
            Intent stepDetailIntent = new Intent(RecipeDetailActivity.this, StepDetailActivity.class);
            stepDetailIntent.putExtra(RECIPE_PARAM, recipe);
            stepDetailIntent.putExtra(SELECTED_STEP_PARAM, stepSelectedIndex);
            startActivity(stepDetailIntent);
        });
        dataBinding.rvSteps.setAdapter(adapter);
        dataBinding.rvSteps.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

