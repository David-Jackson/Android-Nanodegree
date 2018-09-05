package fyi.jackson.activejournal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.data.entities.Position;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM activities")
    List<Activity> getAllActivities();

    @Query("SELECT * FROM positions WHERE activityId = :activityId")
    List<Position> getPositionsForActivity(int activityId);

    @Query("SELECT * FROM content WHERE activityId = :activityId ORDER BY position ASC")
    List<Content> getContentForActivity(int activityId);

    @Query("SELECT COUNT(*) from positions")
    Integer getPositionCount();

    @Query("SELECT COUNT(*) from positions")
    LiveData<Integer> getLivePositionCount();

    @Insert
    void insertActivity(Activity... activities);

    @Insert
    void insertPosition(Position... positions);

    @Insert
    void insertContent(Content... contents);
}
