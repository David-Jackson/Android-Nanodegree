package fyi.jackson.drew.popularmovies.model;

import java.util.List;

public class VideoList {
    int id;
    List<Video> results;

    public VideoList(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
