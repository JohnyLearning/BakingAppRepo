package com.ihadzhi.bakingtime.recipe;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.model.Recipe;
import com.ihadzhi.bakingtime.ui.recipedetail.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.ihadzhi.bakingtime.ui.recipedetail.RecipeDetailActivity.RECIPES_PARAM;
import static com.ihadzhi.bakingtime.ui.recipedetail.RecipeDetailActivity.SELECTED_RECIPE_INDEX_PARAM;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void recipeDetailsTest() {

        Intents.init();

        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>());
        recipe.setId(1);
        recipe.setName("Test recipe");
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(RECIPES_PARAM, recipes);
        intent.putExtra(SELECTED_RECIPE_INDEX_PARAM, 0);

        activityTestRule.launchActivity(intent);

        onView(allOf(withId(R.id.ingredients_title))).check(matches(withText(R.string.ingredients_title)));
        onView(allOf(withId(R.id.steps_title))).check(matches(withText(R.string.steps_title)));

        Intents.release();
    }

}
