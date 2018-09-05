package fyi.jackson.activejournal.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "content")
public class Content {

    public static final int TYPE_TEXT = 547;
    public static final int TYPE_IMAGE = 287;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "contentId")
    private int contentId;

    @ColumnInfo(name = "activityId")
    private int activityId;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "value")
    private String value;

    @ColumnInfo(name = "position")
    private int position;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
