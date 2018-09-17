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
    private int legId;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lng")
    private double lng;

    @ColumnInfo(name = "ts")
    private long ts;

    @ColumnInfo(name = "acc")
    private long acc;

    @ColumnInfo(name = "alt")
    private Integer alt;

    @ColumnInfo(name = "vacc")
    private Integer vacc;


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

    public int getLegId() {
        return legId;
    }

    public void setLegId(int legId) {
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

    public long getAcc() {
        return acc;
    }

    public void setAcc(long acc) {
        this.acc = acc;
    }

    public Integer getAlt() {
        return alt;
    }

    public void setAlt(Integer alt) {
        this.alt = alt;
    }

    public Integer getVacc() {
        return vacc;
    }

    public void setVacc(Integer vacc) {
        this.vacc = vacc;
    }
}
