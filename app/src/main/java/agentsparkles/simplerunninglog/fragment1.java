package agentsparkles.simplerunninglog;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import xyz.rimon.adateswitcher.DateSwitcher;

public class fragment1 extends android.support.v4.app.Fragment {
    private static final String tag = "Fragment1";

    RecyclerView recyclerView;
    DateSwitcher mDateSwitcher;
    RecyclerView.Adapter adapter;
    TextView totalMileage;
    TextView totalTime;
    TextView totalActivities;
    double rangeDistance;
    int rangeTime;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        mDateSwitcher = view.findViewById(R.id.mDateSwitcher);
        recyclerView = view.findViewById(R.id.recyclerViewerFragment);
        totalMileage = view.findViewById(R.id.totalMileage);
        totalTime = view.findViewById(R.id.weeklyTime);
        totalActivities = view.findViewById(R.id.totalActivities);

        final runDatabase db = Room.databaseBuilder(getActivity(), runDatabase.class, "Recent_Runs")
                .allowMainThreadQueries()
                .build();



        final List<runEntry> runs = db.runEntryDAO().getAllRuns();
        Log.i("runs", "onCreate: " + runs);

        for (int i = 0; i < runs.size(); i++){
            double currentDistance = runs.get(i).getTotalDistance();
            int currentTime = runs.get(i).getTotalTime();
            rangeDistance = rangeDistance + currentDistance;
            rangeTime = rangeTime + currentTime;
            Log.i("timePassing", "onCreateView: " + rangeTime);
        }

        String sRangeTime = convertMilliToTime(rangeTime);

        totalMileage.setText(String.valueOf(rangeDistance));
        totalTime.setText(String.valueOf(sRangeTime));
        totalActivities.setText(String.valueOf(runs.size()));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new runAdapter(db, runs);
        recyclerView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), newSimpleRun.class));
            }
        });

        return view;

    }

    private String convertMilliToTime(int totalSeconds) {
        long s = totalSeconds % 60;
        long m = (totalSeconds / 60) % 60;
        long h = (totalSeconds / (60 * 60));
        Log.i("Total Run Time", "convertMilliToTime: " + totalSeconds);
        return String.format("%d:%02d:%02d", h,m,s);
    }
}
