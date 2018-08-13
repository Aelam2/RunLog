package agentsparkles.simplerunninglog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ramotion.foldingcell.FoldingCell;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class singleDayViewer extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Button deleteButton;
    TextView runDate;
    FoldingCell foldingCell;
    List<runEntry> runs;
    Long startMillis;
    Long endMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_day_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.singleDayRecycler);

        final runDatabase db = Room.databaseBuilder(getApplicationContext(), runDatabase.class, "Recent_Runs")
                .allowMainThreadQueries()
                .build();

        final CollapsibleCalendar collapsibleCalendar = findViewById(R.id.singleDayCalendarView);
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                String startOfDay = day.getYear() + "/" +
                        String.format("%02d", (day.getMonth() + 1)) + "/" +
                        String.format("%02d", day.getDay());
                String endOfDay = day.getYear() + "/" +
                        String.format("%02d", (day.getMonth() + 1)) + "/" +
                        String.format("%02d", day.getDay() + 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


                try {
                    Date startDate = sdf.parse(startOfDay);
                    Date endDate = sdf.parse(endOfDay);
                    final long startMillis = startDate.getTime();
                    final long endMillis = endDate.getTime();

                    runs = db.runEntryDAO().findBetweenDates(startMillis, endMillis);
                    Log.i("SingleRuns", "onCreate: " + runs);
                    recyclerView.setLayoutManager(new LinearLayoutManager(singleDayViewer.this));
                    adapter = new runAdapter(db, runs);
                    recyclerView.setAdapter(adapter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Log.i(getClass().getName(), "End and Start Millis: "
                        + startMillis + " " + endMillis);
            }

            @Override
            public void onItemClick(View view) {


            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(singleDayViewer.this, newSimpleRun.class));
            }
        });
    }

}