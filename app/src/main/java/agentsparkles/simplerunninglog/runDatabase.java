package agentsparkles.simplerunninglog;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {runEntry.class}, version = 1)
public abstract class runDatabase extends RoomDatabase {
    public abstract runEntryDAO runEntryDAO();
}