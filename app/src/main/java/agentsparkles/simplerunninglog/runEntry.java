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

    public runEntry(int date, String title, String runType,String surfaceType,double totalDistance,int totalTime) {
        this.date = date;
        this.title = title;
        this.runType = runType;
        this.surfaceType = surfaceType;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;

    }

    @PrimaryKey(autoGenerate = false)
    private int date;

    @ColumnInfo(name = "run_title")
    private String title;

    @ColumnInfo(name = "run_type")
    private String runType;

    @ColumnInfo(name = "surface_type")
    private String surfaceType;

    @ColumnInfo(name = "total_distance")
    private double totalDistance;

    @ColumnInfo(name = "total_time")
    private int totalTime;


    // constructors

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDate() {
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
}


