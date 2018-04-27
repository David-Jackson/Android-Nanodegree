package fyi.jackson.drew.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class Movie {

    int id;
    String title;
    String overview;
    @SerializedName("poster_path")
    String posterPath;
    Double popularity;
    boolean video;
    @SerializedName("vote_average")
    Double voteAverage;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("release_date")
    String releaseDate;

    // optional attributes
//    String tagline;
//    String backdropPath;
//    int budget;
//    int revenue;
//    List<Genre> genres;
//    String originalTitle;
//    String imdbId;
//    Integer collection;
//    boolean adult;
//    String homepage;
//    String originalLanguage;
//    String status;

    public Movie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
