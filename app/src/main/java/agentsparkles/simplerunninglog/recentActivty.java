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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class recentActivty extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Button deleteButton;
    TextView runDate;
    FoldingCell foldingCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recentActivities);

        final runDatabase db = Room.databaseBuilder(getApplicationContext(), runDatabase.class, "Recent_Runs")
                .allowMainThreadQueries()
                .build();

        final List<runEntry> runs = db.runEntryDAO().getAllRuns();
        Log.i("runs", "onCreate: " + runs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new runAdapter(db, runs);
        recyclerView.setAdapter(adapter);


        deleteButton = findViewById(R.id.deleteAll);
        runDate = findViewById(R.id.runDate);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(recentActivty.this, singleDayViewer.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(recentActivty.this, newSimpleRun.class));
            }
        });
    }

}
