package agentsparkles.simplerunninglog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;

import java.util.List;

@Entity(tableName = "runDatabase")
public class runEntry {

    public runEntry(long date, String title, String runType,String surfaceType, String units, double totalDistance, int totalTime, String avgPace) {
        this.date = date;
        this.title = title;
        this.runType = runType;
        this.surfaceType = surfaceType;
        this.units = units;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
        this.avgPace = avgPace;

    }

    @PrimaryKey(autoGenerate = false)
    private long date;

    @ColumnInfo(name = "run_title")
    private String title;

    @ColumnInfo(name = "run_type")
    private String runType;

    @ColumnInfo(name = "surface_type")
    private String surfaceType;

    @ColumnInfo(name = "distance_units")
    private String units;

    @ColumnInfo(name = "total_distance")
    private double totalDistance;

    @ColumnInfo(name = "total_time")
    private int totalTime;

    @ColumnInfo(name = "avg_pace")
    private String avgPace;


    // constructors

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getRunType() {
        return this.runType;
    }

    public void setSurfaceType(String SurfaceType) {
        this.surfaceType = SurfaceType;
    }

    public String getSurfaceType() {
        return this.surfaceType;
    }


    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalDistance() {
        return this.totalDistance;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(String avgPace) {
        this.avgPace = avgPace;
    }
}


