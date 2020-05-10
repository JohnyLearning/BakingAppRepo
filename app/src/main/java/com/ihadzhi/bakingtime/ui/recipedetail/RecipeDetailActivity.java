package com.ihadzhi.bakingtime.ui.recipedetail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.ihadzhi.bakingtime.model.Ingredient;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.ui.BaseActivity;

import java.util.List;

public class RecipeDetailActivity extends BaseActivity {

    public static final String RECIPE_PARAM = "recipe";

    private ActivityRecipeDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        if (getIntent() != null) {
            decorateIngredients(getIntent().getParcelableExtra(RECIPE_PARAM));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

