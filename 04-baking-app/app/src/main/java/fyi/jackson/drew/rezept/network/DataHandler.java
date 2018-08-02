package fyi.jackson.drew.rezept.network;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fyi.jackson.drew.rezept.model.Recipe;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHandler {

    public static final String TAG = DataHandler.class.getSimpleName();
    public static final String API_BASE_URL = "https://api.myjson.com/bins/";

    private Call<List<Recipe>> recipeCall;
    private List<Recipe> recipes;

    private DataCallback callback;

    public DataHandler(DataCallback callback) {

        this.callback = callback;

        initializeCalls();
    }

    private void initializeCalls() {
        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        RecipeApiService apiService = retrofit.create(RecipeApiService.class);

        recipeCall = apiService.getAllRecipes();
    }

    public void requestData() {
        Log.d(TAG, "requestData: Requesting...");
        if (!recipeCall.isExecuted()) {
            Log.d(TAG, "requestData: Enqueueing");
            recipeCall.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    Log.d(TAG, "onResponse: Got Response: " + response.body().size());
                    recipes = response.body();
                    callback.onUpdate(recipes);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.d(TAG, "onFailure: Failed");
                    t.printStackTrace();
                    recipes = new ArrayList<>();
                    callback.onUpdate(recipes);
                }
            });
            return;
        }
        callback.onUpdate(recipes);
    }

    public void forceRequest() {
        initializeCalls();
        requestData();
    }

    public interface DataCallback {
        void onUpdate(List<Recipe> recipes);
    }
}
