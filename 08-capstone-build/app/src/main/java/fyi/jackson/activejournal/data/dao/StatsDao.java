package fyi.jackson.activejournal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import fyi.jackson.activejournal.data.entities.Stats;

@Dao
public interface StatsDao {

    @Query("SELECT * FROM stats")
    List<Stats> getStats();

    @Query("SELECT * FROM stats")
    LiveData<List<Stats>> getLiveStats();

}
