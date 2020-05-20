package com.ihadzhi.bakingtime.ui.recipedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityRecipeDetailBinding;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.model.Step;
import com.ihadzhi.bakingtime.ui.BaseActivity;
import com.ihadzhi.bakingtime.ui.recipedetail.RecipesDetailsListFragment.OnStepClick;
import com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity;
import com.ihadzhi.bakingtime.ui.stepdetails.StepVideoFragment;

import java.util.ArrayList;
import java.util.List;

import static com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity.RECIPE_PARAM;
import static com.ihadzhi.bakingtime.ui.stepdetails.StepDetailActivity.SELECTED_STEP_PARAM;

public class RecipeDetailActivity extends BaseActivity implements OnStepClick {

    public static final String RECIPES_PARAM = "recipes";
    public static final String SELECTED_RECIPE_INDEX_PARAM = "selectedRecipeIndex";

    private ActivityRecipeDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Recipe> recipes = getIntent().getParcelableArrayListExtra(RECIPES_PARAM);
        int recipeIndex = getIntent().getIntExtra(SELECTED_RECIPE_INDEX_PARAM, -1);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        if (getIntent() != null) {
            if (recipes != null && recipes.size() > recipeIndex && recipeIndex > -1) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
    }

    private void showRecipeDetails(int index) {
        if (getIntent() != null) {
            List<Recipe> recipes = getRecipes();
            if (recipes != null && recipes.size() > index && index > -1) {
                replaceDetailsList(recipes.get(index));
                if (isTablet()) {
                    setupVideo(null);
                }
            }
        }
    }

    private void setupVideo(Step step) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.video_container, StepVideoFragment.newInstance(step))
                .commit();
    }

    private void setupInstruction(Step step) {
        dataBinding.stepDescription.setText(step.getDescription());
    }

    private void replaceDetailsList(Recipe recipe) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_details_list_container, RecipesDetailsListFragment.newInstance(recipe, selectedStepIndex -> {
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
        if (isTablet()) {
            Recipe recipe = getRecipe();
            if (recipe != null) {
                List<Step> steps = recipe.getSteps();
                if (steps != null && steps.size() > selectedStepIndex) {
                    Step step = steps.get(selectedStepIndex);
                    setupVideo(step);
                    setupInstruction(step);
                }
            }
        } else {
            Intent stepDetailIntent = new Intent(RecipeDetailActivity.this, StepDetailActivity.class);
            stepDetailIntent.putExtra(RECIPE_PARAM, getRecipe());
            stepDetailIntent.putExtra(SELECTED_STEP_PARAM, selectedStepIndex);
            startActivity(stepDetailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_details_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner_recipe_detail);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(this, R.layout.recipe_details_menu_item, getRecipes());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        int selectedRecipeIndex = getSelectedRecipeIndex();
        spinner.setSelection(selectedRecipeIndex);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switchRecipe(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // no-op
            }
        });

        return true;
    }

    private void switchRecipe(int index) {
        showRecipeDetails(index);
        if (getIntent() != null) {
            getIntent().putExtra(SELECTED_RECIPE_INDEX_PARAM, index);
        }
    }

    private Recipe getRecipe() {
        List<Recipe> recipes = getRecipes();
        return recipes != null ? getRecipes().get(getSelectedRecipeIndex()) : null;
    }

    private int getSelectedRecipeIndex() {
        return getIntent() != null ? getIntent().getIntExtra(SELECTED_RECIPE_INDEX_PARAM, 0) : 0;
    }

    private ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = null;
        if (getIntent() != null) {
            recipes = getIntent().getParcelableArrayListExtra(RECIPES_PARAM);
        }
        return recipes;
    }
}

