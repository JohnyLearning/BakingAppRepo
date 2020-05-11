package com.ihadzhi.bakingtime.model;

import androidx.annotation.NonNull;

public enum IngredientMeasure {

    CUP("cups"),
    TBLSP("tblsp"),
    TSP("tsp"),
    G("grams"),
    K("kgs"),
    OZ("ounces"),
    UNIT("units");

    private final String description;

    IngredientMeasure(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
