package fyi.jackson.drew.rezept.network;

import java.util.List;

import fyi.jackson.drew.rezept.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApiService {

    @GET("vf42a")
    Call<List<Recipe>> getAllRecipes();

}
