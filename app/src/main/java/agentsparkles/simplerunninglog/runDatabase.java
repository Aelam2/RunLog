package agentsparkles.simplerunninglog;


@Entity(tableName = "runDatabase")
public class runEntry {

    @PrimaryKey(date = "date")
    private int date;

    @ColumnInfo(title = "run_title")
    private String title;

    @ColumnInfo(runType = "run_type")
    private String runType;
    
    @ColumnInfo(surfaceType = "surface_type")
    private String surfaceType;

    @ColumnInfo(totalDistance = "total_distance")
    private int totalDistance;
    
    @ColumnInfo(totalTime = "total_time")
    private int totalTime;
    
// constructors
public Student() {}

public void setTitle(String title) {
this.title = title;
}

public int getTitle() {
return this.title;
}

public void setDate(String date) {
this.date = date;
}

public int getDate() {
return this.date;
}

public void setRunType(String runType) {
this.runType = runType;
}

public int getRunType() {
return this.runType;
}

public void setSurfaceType(String SurfaceType) {
this.SurfaceType = SurfaceType;
}

public int getSurfaceType() {
return this.SurfaceType;
}

public void setTotalDistance(String totalDistance) {
this.totalDistance = totalDistance;
}

public int getTotalDistance() {
return this.totalDistance;
}

public void setTotalTime(String totalTime) {
this.totalTime = totalTime;
}

public int getTotalTime() {
return this.totalTime;
}

