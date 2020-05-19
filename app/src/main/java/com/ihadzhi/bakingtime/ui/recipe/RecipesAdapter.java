package com.ihadzhi.bakingtime.ui.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.RecipeListItemBinding;
import com.ihadzhi.bakingtime.model.Recipe;

import java.util.ArrayList;

class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder> {

    @FunctionalInterface
    public interface RecipeClickListener {
        void execute(ArrayList<Recipe> recipe, int selectedRecipeIndex);
    }

    private final Context context;
    private ArrayList<Recipe> recipes;
    private RecipeClickListener recipeClickListener;

    public RecipesAdapter(@NonNull Context context) {
        this.context = context;
    }

    public void addRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void setRecipeClickListener(RecipeClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecipeListItemBinding itemBinding = RecipeListItemBinding.inflate(layoutInflater, parent, false);
        return new RecipeHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        if (recipes != null && recipes.size() > 0 && recipes.get(position) != null) {
            holder.bindItem(recipes.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecipeListItemBinding dataBinding;

        RecipeHolder(RecipeListItemBinding binding) {
            super(binding.getRoot());
            dataBinding = binding;
        }

        void bindItem(@Nullable Recipe recipe) {
            if (recipe != null) {
                dataBinding.setRecipe(recipe);
                switch (recipe.getId()) {
                    case 1:
                        dataBinding.recipeContainer.setBackground(context.getDrawable(R.drawable.r1));
                        break;
                    case 2:
                        dataBinding.recipeContainer.setBackground(context.getDrawable(R.drawable.r2));
                        break;
                    case 3:
                        dataBinding.recipeContainer.setBackground(context.getDrawable(R.drawable.r3));
                        break;
                    case 4:
                        dataBinding.recipeContainer.setBackground(context.getDrawable(R.drawable.r4));
                        break;
                }
                dataBinding.recipeContainer.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (recipeClickListener != null && recipes != null && recipes.size() > position) {
                recipeClickListener.execute(recipes, position);
            }
        }

    }
}
