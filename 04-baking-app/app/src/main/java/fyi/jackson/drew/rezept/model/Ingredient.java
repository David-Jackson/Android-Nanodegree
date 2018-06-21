package fyi.jackson.drew.rezept.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Ingredient implements Parcelable {

    double quantity;
    String measure;
    String ingredient;

    Map<String, String> formattedMeasures = new HashMap<String, String>(){{
        put("CUP", "cup");
        put("TBLSP", "tbsp");
        put("TSP", "tsp");
        put("K", "kg");
        put("G", "g");
        put("OZ", "oz");
        put("UNIT", "");
    }};

    public Ingredient() {}

    public Ingredient(Parcel in) {
        setQuantity(in.readDouble());
        setMeasure(in.readString());
        setIngredient(in.readString());
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
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
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    private String getFormattedQuantity() {
        return String.format("%." + (quantity % 1 == 0 ? "0" : "1") + "f", quantity);
    }

    private String getFormattedMeasure() {
        String m = formattedMeasures.get(measure);
        if (m == null) m = measure;
        return m;
    }

    private String getFormattedIngredient() {
        return ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getFormattedQuantity())
                .append(" ")
                .append(getFormattedMeasure())
                .append(" ")
                .append(getFormattedIngredient())
                .toString();
    }
}
