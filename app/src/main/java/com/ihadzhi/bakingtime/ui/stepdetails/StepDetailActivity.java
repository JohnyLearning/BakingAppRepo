package com.ihadzhi.bakingtime.ui.stepdetails;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.ActivityStepDetailBinding;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.model.Step;
import com.ihadzhi.bakingtime.ui.BaseActivity;

public class StepDetailActivity extends BaseActivity {

    public static final String RECIPE_PARAM = "recipe";
    public static final String SELECTED_STEP_PARAM = "selectedStep";

    private ActivityStepDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);
        if (getIntent() != null) {
            Recipe recipe = getIntent().getParcelableExtra(RECIPE_PARAM);
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
                    setupVideo(selectedStep);
                    if (dataBinding.nextAction != null) {
                        setupNavigationButtons();
                    }
                }
            }
        }
    }

    private void setupVideo(Step step) {
        if (getSupportFragmentManager().findFragmentByTag(StepVideoFragment.class.getCanonicalName()) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.video_container, StepVideoFragment.newInstance(step), StepVideoFragment.class.getCanonicalName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    private void switchContent(Step step) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.video_container, StepVideoFragment.newInstance(step), StepVideoFragment.class.getCanonicalName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        dataBinding.setStep(step);
    }

    private void setupNavigationButtons() {
        final Recipe recipe = getRecipe();
        final int selectedStepIndex = getSelectedStepIndex();
        if (selectedStepIndex > 0) {
            enableButton(dataBinding.previousAction);
            dataBinding.previousAction.setOnClickListener(view -> {
                int previousStepIndex = getSelectedStepIndex() - 1;
                switchContent(recipe.getSteps().get(previousStepIndex));
                updateSelectedStep(previousStepIndex);
                setupNavigationButtons();
            });
        } else {
            disableButton(dataBinding.previousAction);
        }
        if (selectedStepIndex < recipe.getSteps().size() - 1) {
            enableButton(dataBinding.nextAction);
            dataBinding.nextAction.setOnClickListener(view -> {
                int nextStepIndex = getSelectedStepIndex() + 1;
                switchContent(recipe.getSteps().get(nextStepIndex));
                updateSelectedStep(nextStepIndex);
                setupNavigationButtons();
            });
        } else {
            disableButton(dataBinding.nextAction);
        }
    }

    private void enableButton(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        button.setEnabled(true);
    }

    private void disableButton(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.gray));
        button.setEnabled(false);
    }

    private void updateSelectedStep(int stepIndex) {
        if (getIntent() != null) {
            getIntent().putExtra(SELECTED_STEP_PARAM, stepIndex);
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

    private int getSelectedStepIndex() {
        int stepIndex = 0;
        if (getIntent() != null) {
            stepIndex = getIntent().getIntExtra(SELECTED_STEP_PARAM, stepIndex);
        }
        return stepIndex;
    }

    private Recipe getRecipe() {
        Recipe recipe = null;
        if (getIntent() != null) {
            recipe = getIntent().getParcelableExtra(RECIPE_PARAM);
        }
        return recipe;
    }

}

