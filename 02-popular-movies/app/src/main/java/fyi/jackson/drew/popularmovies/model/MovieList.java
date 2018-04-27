package fyi.jackson.drew.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {

    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    List<Movie> results;

    public MovieList() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
