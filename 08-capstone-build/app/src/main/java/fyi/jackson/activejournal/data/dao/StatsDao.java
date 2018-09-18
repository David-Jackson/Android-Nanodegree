package fyi.jackson.activejournal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Stats;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StatsDao {

    @Query("SELECT * FROM stats")
    List<Stats> getStats();

    @Query("SELECT * FROM stats")
    LiveData<List<Stats>> getLiveStats();

    @Insert(onConflict = REPLACE)
    void insertStats(Stats stats);

    @Update(onConflict = REPLACE)
    void updateStats(Stats stats);

    @Query("DELETE FROM stats")
    void clearStats();

}
