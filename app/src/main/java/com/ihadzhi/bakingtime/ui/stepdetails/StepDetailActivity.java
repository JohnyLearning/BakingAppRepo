package com.ihadzhi.bakingtime.ui.stepdetails;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.model.Step;
import com.ihadzhi.bakingtime.ui.BaseActivity;
import com.ihadzhi.bakingtime.ui.recipedetail.RecipeDetailActivity;

public class StepDetailActivity extends BaseActivity {

    public static final String SELECTED_STEP_PARAM = "selectedStep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.ihadzhi.bakingtime.databinding.ActivityStepDetailBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);
        if (getIntent() != null) {
            Recipe recipe = getIntent().getParcelableExtra(RecipeDetailActivity.RECIPE_PARAM);
            if (recipe != null) {
                int selectedStepIndex = getIntent().getIntExtra(SELECTED_STEP_PARAM, 0);
                if (recipe.getSteps() != null && recipe.getSteps().size() > 0) {
                    Step selectedStep = recipe.getSteps().get(selectedStepIndex);
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(
                                new StringBuilder(recipe.getName())
                                        .append(':')
                                        .append(' ')
                                        .append(selectedStep.getShortDescription()));
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    dataBinding.setStep(selectedStep);
                }
            }
        }
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

