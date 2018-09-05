package fyi.jackson.activejournal.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "positions")
public class Position {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "positionId")
    private int positionId;

    @ColumnInfo(name = "activityId")
    private int activityId;

    @ColumnInfo(name = "legId")
    private int legId;

    @ColumnInfo(name = "lat")
    private float lat;

    @ColumnInfo(name = "lng")
    private float lng;

    @ColumnInfo(name = "ts")
    private long ts;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getLegId() {
        return legId;
    }

    public void setLegId(int legId) {
        this.legId = legId;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setLatLng(float lat, float lng) {
        setLat(lat);
        setLng(lng);
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
