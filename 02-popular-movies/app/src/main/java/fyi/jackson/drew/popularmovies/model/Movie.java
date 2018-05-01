package fyi.jackson.drew.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class Movie implements Parcelable {

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


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {

    }

    public Movie(Parcel in) {
        setId(in.readInt());
        setTitle(in.readString());
        setOverview(in.readString());
        setPosterPath(in.readString());
        setPopularity(in.readDouble());
        setVoteAverage(in.readDouble());
        setVoteCount(in.readInt());
        setReleaseDate(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
        dest.writeString(releaseDate);
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
