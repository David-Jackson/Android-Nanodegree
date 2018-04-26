package fyi.jackson.drew.popularmovies.model;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<Movie> getMovies() {
        return new ArrayList<Movie>(){{
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
            add(new Movie());
        }};
    }
}
