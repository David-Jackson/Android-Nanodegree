package fyi.jackson.activejournal.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "activities")
public class Activity {

    public static final int TYPE_WALKING = 349;
    public static final int TYPE_RUNNING = 319;
    public static final int TYPE_HIKING = 439;
    public static final int TYPE_BIKING = 723;
    public static final int TYPE_SAILING = 127;
    public static final int TYPE_BOATING = 859;
    public static final int TYPE_DRIVING = 740;
    public static final int TYPE_OTHER = 22;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "activityId")
    private int activityId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private int type = TYPE_OTHER;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
