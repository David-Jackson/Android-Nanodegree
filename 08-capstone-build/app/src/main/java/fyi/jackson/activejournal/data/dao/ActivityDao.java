package fyi.jackson.activejournal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.data.entities.Position;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activities")
    List<Activity> getAllActivities();

    @Query("SELECT * FROM activities")
    LiveData<List<Activity>> getLiveAllActivities();

    @Query("SELECT * FROM positions WHERE activityId = :activityId ORDER BY ts ASC")
    List<Position> getPositionsForActivity(long activityId);

    @Query("SELECT * FROM content WHERE activityId = :activityId ORDER BY position ASC")
    List<Content> getContentForActivity(long activityId);

    @Query("SELECT * FROM content WHERE activityId = :activityId ORDER BY position ASC")
    LiveData<List<Content>> getLiveContentForActivity(long activityId);

    @Query("SELECT COUNT(*) from positions")
    Integer getPositionCount();

    @Query("SELECT COUNT(*) from positions")
    LiveData<Integer> getLivePositionCount();

    @Insert
    void insertActivity(Activity... activities);

    @Insert
    void insertPosition(Position... positions);

    @Insert
    void insertPositionList(List<Position> positions);

    @Insert
    void insertContent(Content... contents);

    @Query("UPDATE activities SET thumbnail = :thumbnailFileName WHERE activityId = :activityId")
    void updateThumbail(long activityId, String thumbnailFileName);

}
