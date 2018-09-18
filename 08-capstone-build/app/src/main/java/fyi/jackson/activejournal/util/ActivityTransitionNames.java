package fyi.jackson.activejournal.util;

public class ActivityTransitionNames {

    public String map;
    public String title;
    public String type;
    public String container;

    public ActivityTransitionNames(long activityId) {
        map = activityId + "map";
        title = activityId + "title";
        type = activityId + "type";
        container = activityId + "container";
    }
}
