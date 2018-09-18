package fyi.jackson.activejournal.sensor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import fyi.jackson.activejournal.data.entities.Position;
import fyi.jackson.activejournal.data.entities.Stats;

public class LocationStatistics {

    private Stats statistics;
    private Position lastPosition;

    public LocationStatistics(long activityId) {
        statistics = new Stats();
        statistics.setActivityId(activityId);
        statistics.setPointCount(0);
        statistics.setDuration(0);
        statistics.setDistance(0);
        statistics.setAverageSpeed(0);
    }

    public Stats getStatistics() {
        return statistics;
    }

    public void setActivityId(long activityId) {
        statistics.setActivityId(activityId);
    }

    public Stats accumulate(Position position) {
        analyze(position);
        lastPosition = position;
        return statistics;
    }

    private void analyze(Position position) {
        statistics.setPointCount(statistics.getPointCount() + 1);
        if (lastPosition != null) {
            statistics.setDuration(statistics.getDuration() +
                    durationBetween(lastPosition, position));
            statistics.setDistance(statistics.getDistance() +
                    distanceBetween(lastPosition, position));
            statistics.setAverageSpeed(statistics.getDistance() / (statistics.getDuration() / 1000));
        }
    }

    private long durationBetween(Position start, Position end) {
        return end.getTs() - start.getTs();
    }

    private double distanceBetween(Position start, Position end) {
        LatLng startLatLng = new LatLng(start.getLat(), start.getLng());
        LatLng endLatLng = new LatLng(end.getLat(), end.getLng());
        return SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);
    }

}
