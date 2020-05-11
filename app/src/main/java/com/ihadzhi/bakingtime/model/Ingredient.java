package com.ihadzhi.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private IngredientMeasure measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    public Ingredient() {
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public IngredientMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(IngredientMeasure measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.quantity);
        dest.writeInt(this.measure == null ? -1 : this.measure.ordinal());
        dest.writeString(this.ingredient);
    }

    Ingredient(Parcel in) {
        this.quantity = (Double) in.readValue(Double.class.getClassLoader());
        int tmpMeasure = in.readInt();
        this.measure = tmpMeasure == -1 ? null : IngredientMeasure.values()[tmpMeasure];
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
