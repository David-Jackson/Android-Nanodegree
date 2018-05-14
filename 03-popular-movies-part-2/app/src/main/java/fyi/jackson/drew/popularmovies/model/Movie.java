package fyi.jackson.drew.popularmovies.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import fyi.jackson.drew.popularmovies.data.MovieContract.MovieEntry;

public class Movie implements Parcelable {

    int id;
    String title;
    String overview;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    Double popularity;
    boolean video;
    @SerializedName("vote_average")
    Double voteAverage;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("release_date")
    String releaseDate;
    boolean favorite = false;

    //    String tagline;
    // optional attributes
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
        setBackdropPath(in.readString());
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
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
        dest.writeString(releaseDate);
    }

    public static Movie fromCursor(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_POPULARITY)));
        movie.setVideo(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_VIDEO)) == 1);
        movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_FAVORITE)) == 1);
        return movie;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, getId());
        cv.put(MovieEntry.COLUMN_TITLE, getTitle());
        cv.put(MovieEntry.COLUMN_OVERVIEW, getOverview());
        cv.put(MovieEntry.COLUMN_POSTER, getPosterPath());
        cv.put(MovieEntry.COLUMN_BACKDROP, getBackdropPath());
        cv.put(MovieEntry.COLUMN_POPULARITY, getPopularity());
        cv.put(MovieEntry.COLUMN_VIDEO, isVideo());
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, getVoteAverage());
        cv.put(MovieEntry.COLUMN_VOTE_COUNT, getVoteCount());
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, getReleaseDate());
        cv.put(MovieEntry.COLUMN_FAVORITE, isFavorite());
        return cv;
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
