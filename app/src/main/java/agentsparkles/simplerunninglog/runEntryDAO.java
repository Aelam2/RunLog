package agentsparkles.simplerunninglog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface runEntryDAO {

    @Query("SELECT * FROM runDatabase ORDER BY date DESC")
    List<runEntry> getAllRuns();

    @Query("SELECT * FROM runDatabase WHERE date BETWEEN :dateOne AND :dateTwo ORDER BY date DESC")
    List<runEntry> findBetweenDates(long dateOne, long dateTwo);


    @Query("SELECT * FROM runDatabase WHERE date = :date")
    List<runEntry> findDate(Long date);

    @Query("DELETE FROM runDatabase WHERE date = :date")
    void delete(Long date);

    @Delete()
    void clearAllTables(List<runEntry> runs);

    @Insert
    void insertAll(runEntry... runs);

    @Update
    void update(runEntry run);

    @Delete()
    void delete(runEntry run);
}
