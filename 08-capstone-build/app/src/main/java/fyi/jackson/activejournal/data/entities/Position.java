package fyi.jackson.activejournal.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "positions")
public class Position {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "activityId")
    private long activityId;

    @ColumnInfo(name = "legId")
    private long legId;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lng;

    @ColumnInfo(name = "ts")
    private long ts;

    @ColumnInfo(name = "acc")
    private float acc;

    @ColumnInfo(name = "alt")
    private Double alt;

    @ColumnInfo(name = "vacc")
    private Float vacc;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getLegId() {
        return legId;
    }

    public void setLegId(long legId) {
        this.legId = legId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
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

    public float getAcc() {
        return acc;
    }

    public void setAcc(float acc) {
        this.acc = acc;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }

    public Float getVacc() {
        return vacc;
    }

    public void setVacc(Float vacc) {
        this.vacc = vacc;
    }
}
