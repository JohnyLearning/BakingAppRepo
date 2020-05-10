package com.ihadzhi.bakingapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ihadzhi.bakingapp.R;
import com.ihadzhi.bakingapp.databinding.RecipeListItemBinding;
import com.ihadzhi.bakingapp.model.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder> {

    private Context context;
    private List<Recipe> recipes;

    public RecipesAdapter(@NonNull Context context) {
        this.context = context;
    }

    public void addRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
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

        private RecipeListItemBinding dataBinding;

        public RecipeHolder(RecipeListItemBinding binding) {
                super(binding.getRoot());
            dataBinding = binding;
        }

        public void bindItem(@Nullable Recipe recipe) {
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
            }
        }

        @Override
        public void onClick(View view) {

        }

    }
}
