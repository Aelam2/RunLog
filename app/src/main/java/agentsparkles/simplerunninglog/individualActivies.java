package agentsparkles.simplerunninglog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class individualActivies extends AppCompatActivity  {

        Button deleteButton;
        TextView runDate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.run_row);

            final runDatabase db = Room.databaseBuilder(getApplicationContext(), runDatabase.class, "Recent_Runs")
                    .allowMainThreadQueries()
                    .build();

            final List<runEntry> runs = db.runEntryDAO().getAllRuns();

            runDate = findViewById(R.id.runDate);
            deleteButton = findViewById(R.id.deleteButton);
            String currentRunDate = runDate.getText().toString();

            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date longDate = null;
            try {
                longDate = df.parse(currentRunDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final long millis = longDate.getTime();

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.runEntryDAO().delete(millis);
                }
            });

    }
}
