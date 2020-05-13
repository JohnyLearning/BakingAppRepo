package com.ihadzhi.bakingtime.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.ui.BaseActivity;
import com.ihadzhi.bakingtime.ui.recipedetail.RecipesDetailsListFragment.OnStepClick;
import com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity;

import static com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity.SELECTED_STEP_PARAM;

public class RecipeDetailActivity extends BaseActivity implements OnStepClick {

    public static final String RECIPE_PARAM = "recipe";

    private ActivityRecipeDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = getIntent().getParcelableExtra(RECIPE_PARAM);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        if (getIntent() != null) {
            if (recipe != null) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(recipe.getName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                setupDetailsList(recipe);
            }
        }

    }

    private void setupDetailsList(Recipe recipe) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_details_list_container, RecipesDetailsListFragment.newInstance(recipe, selectedStepIndex -> {
                    onStepClickExecute(selectedStepIndex);
                }))
                .setTransition(FragmentTransaction.TRANSIT_NONE)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepClickExecute(int selectedStepIndex) {
        Intent stepDetailIntent = new Intent(RecipeDetailActivity.this, StepDetailActivity.class);
        stepDetailIntent.putExtra(RECIPE_PARAM, getRecipe());
        stepDetailIntent.putExtra(SELECTED_STEP_PARAM, selectedStepIndex);
        startActivity(stepDetailIntent);
    }

    private Recipe getRecipe() {
        return getIntent() != null ? getIntent().getParcelableExtra(RECIPE_PARAM) : null;
    }
}

