package fyi.jackson.drew.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {
    int id;
    int page;
    List<Review> results;
    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("total_results")
    int totalResults;

    public ReviewList(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
