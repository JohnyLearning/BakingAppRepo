package com.ihadzhi.bakingtime.ui.recipedetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ihadzhi.bakingtime.databinding.StepListItemBinding;
import com.ihadzhi.bakingtime.model.Step;

import java.util.List;

class RecipeDetailStepsAdapter extends RecyclerView.Adapter<RecipeDetailStepsAdapter.RecipeDetailStepViewHolder> {

    @FunctionalInterface
    public interface StepClickListener {
        void execute(int selectedStep);
    }

    private final List<Step> steps;
    private final Context context;
    private StepClickListener stepClickListener;

    public RecipeDetailStepsAdapter(Context context, List<Step> steps) {
        this.context = context;
        this.steps = steps;
    }

    public void setStepClickListener(StepClickListener stepClickListener) {
        this.stepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public RecipeDetailStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        StepListItemBinding itemBinding = StepListItemBinding.inflate(layoutInflater, parent, false);
        return new RecipeDetailStepViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailStepViewHolder holder, int position) {
        if (steps != null && steps.size() > 0 && steps.get(position) != null) {
            holder.bindItem(steps.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    class RecipeDetailStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final StepListItemBinding dataBinding;

        RecipeDetailStepViewHolder(StepListItemBinding binding) {
            super(binding.getRoot());
            this.dataBinding = binding;
        }

        void bindItem(Step step) {
            dataBinding.setStep(step);
            dataBinding.stepContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (stepClickListener != null) {
                stepClickListener.execute(getAdapterPosition());
            }
        }
    }

}
