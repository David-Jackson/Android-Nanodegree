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
    private static boolean SYNCHRONOUS = false;

    public static AppDatabase getDatabase(final Context context) {
        return loadDatabase(context, false);
    }

    public static AppDatabase getSynchronousDatabase(final Context context) {
        return loadDatabase(context, true);
    }

    public static AppDatabase loadDatabase(final Context context, boolean synchronous) {
        if (INSTANCE == null || SYNCHRONOUS != synchronous) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null || SYNCHRONOUS != synchronous) {
                    SYNCHRONOUS = synchronous;
                    if (INSTANCE != null) INSTANCE.close();
                    // TODO: 9/19/2018 Implement Migrations

                    Builder<AppDatabase> builder =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "activejournal_database");
                    builder.fallbackToDestructiveMigration();
                    if (SYNCHRONOUS) {
                        builder.allowMainThreadQueries();
                    }

                    INSTANCE = builder.build();
                }
            }
        }
        return INSTANCE;
    }
}
