package fyi.jackson.drew.rezept.util;

import fyi.jackson.drew.rezept.R;

public class RecipeUtils {

    private static final int[] placeholderImages = {
            R.mipmap.img_bbq_2x,
            R.mipmap.img_breakfast_2x,
            R.mipmap.img_lunch_2x,
            R.mipmap.img_dinner_2x
    };

    private static int placeHolderImageIndex = 0;

    public static int getPlaceHolderImage() {
        placeHolderImageIndex++;
        placeHolderImageIndex %= placeholderImages.length;
        return placeholderImages[placeHolderImageIndex];
    }

}
