package agentsparkles.simplerunninglog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface runEntryDAO {

    @Query("SELECT * FROM runDatabase")
    List<runEntry> getAllRuns();

    @Query("SELECT * FROM runDatabase WHERE date LIKE :date LIMIT 1")
    runEntry findByName(String date);

    @Insert
    void insertAll(runEntry... runs);

    @Update
    void update(runEntry entry);

    @Delete
    void delete(runEntry entry);
}
