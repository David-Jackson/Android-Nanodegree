package fyi.jackson.activejournal.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fyi.jackson.activejournal.data.dao.ActivityDao;
import fyi.jackson.activejournal.data.dao.StatsDao;
import fyi.jackson.activejournal.data.entities.Activity;
import fyi.jackson.activejournal.data.entities.Content;
import fyi.jackson.activejournal.data.entities.Position;
import fyi.jackson.activejournal.data.entities.Stats;

@Database(
        entities = {Activity.class, Content.class, Position.class, Stats.class},
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ActivityDao activityDao();
    public abstract StatsDao statsDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // TODO: 9/19/2018 Implement Migrations
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "activejournal_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
