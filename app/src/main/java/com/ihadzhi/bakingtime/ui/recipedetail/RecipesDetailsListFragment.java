package com.ihadzhi.bakingtime.ui.recipedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.FragmentRecipeDetailsBinding;
import com.ihadzhi.bakingtime.model.Ingredient;
import com.ihadzhi.bakingtime.model.Recipe;

import java.util.List;

public class RecipesDetailsListFragment extends Fragment {

    @FunctionalInterface
    public interface OnStepClick {
        void onStepClickExecute(int selectedStepIndex);
    }

    private static final String RECIPE_PARAM = "recipe";

    private FragmentRecipeDetailsBinding dataBinding;

    private OnStepClick onStepClick;

    public RecipesDetailsListFragment() {
    }

    public static RecipesDetailsListFragment newInstance(Recipe recipe, OnStepClick onStepClick) {
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_PARAM, recipe);
        RecipesDetailsListFragment fragment = new RecipesDetailsListFragment();
        fragment.setArguments(args);
        fragment.onStepClick = onStepClick;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe = getArguments().getParcelable(RECIPE_PARAM);
        decorateIngredients(recipe);
        decorateSteps(recipe);
    }

    private void decorateIngredients(Recipe recipe) {
        if (recipe != null) {
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
        RecipeDetailStepsAdapter adapter = new RecipeDetailStepsAdapter(getContext(), recipe.getSteps());
        adapter.setStepClickListener(stepSelectedIndex -> {
            if (onStepClick != null) {
                onStepClick.onStepClickExecute(stepSelectedIndex);
            }
        });
        dataBinding.rvSteps.setAdapter(adapter);
        dataBinding.rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
