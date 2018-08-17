package agentsparkles.simplerunninglog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.ramotion.foldingcell.FoldingCell;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

class runAdapter extends RecyclerView.Adapter<runAdapter.ViewHolder> {
    runDatabase db;
    List<runEntry> runs;

    public runAdapter(runDatabase db, List<runEntry> runs) {

        this.runs = runs;
        this.db = db;
    }

    @NonNull
    @Override
    public runAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull runAdapter.ViewHolder holder, final int position) {

        String runDate = convertDate(runs.get(position).getDate(), "EEE, MMM dd, yyyy");
        String runTime = " at " + convertDate(runs.get(position).getDate(), "hh:mm aa");
        String finalTime = runDate + runTime;
        String totalTime = convertRunTime(runs.get(position).getTotalTime());
        String totalDistance = String.valueOf(runs.get(position).getTotalDistance()) + " " + runs.get(position).getUnits();
        String avgPace = runs.get(position).getAvgPace() + " per /" + runs.get(position).getUnits();

        holder.dateTitleView.setText(finalTime);
        holder.titleTitleView.setText(runs.get(position).getTitle());
        holder.runType.setText(runs.get(position).getRunType());
        holder.surfaceType.setText(runs.get(position).getSurfaceType());
        holder.totalDistance.setText(totalDistance);
        holder.totalTime.setText(totalTime);
        holder.avgPace.setText(avgPace);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long date = runs.get(position).getDate();
                db.runEntryDAO().delete(date);
                runs.remove(position);
                notifyItemRemoved(position);
            }
        });


//        holder.updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mcon.startAc
//            }
//        });




//        holder.updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Long date = runs.get(position).getDate();
//                startActivity(new Intent(newSimpleRun.this, recentActivty.class));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return runs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView runType;
        public TextView surfaceType;
        public TextView totalDistance;
        public TextView totalTime;
        public TextView avgPace;
        public Button deleteButton;
        public Button updateButton;
        public FoldingCell foldingCell;
        public TextView dateTitleView;
        public TextView titleTitleView;


        public ViewHolder(View itemView) {
            super(itemView);
            runType = itemView.findViewById(R.id.runType);
            surfaceType = itemView.findViewById(R.id.surfaceType);
            totalDistance = itemView.findViewById(R.id.totalDistance);
            totalTime = itemView.findViewById(R.id.totalTime);
            avgPace = itemView.findViewById(R.id.avgPace);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            updateButton = itemView.findViewById(R.id.updateButton);
            dateTitleView = itemView.findViewById(R.id.dateTitleView);
            titleTitleView = itemView.findViewById(R.id.titleTitleView);
        }



    }
    public static String convertDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    public static String convertRunTime(int seconds)
    {
        int MinutesPerHour = 60;
        int SecondsPerMinute = 60;

        int minutes = seconds / SecondsPerMinute;
        seconds -= minutes * SecondsPerMinute;

        int hours = minutes / MinutesPerHour;
        minutes -= hours * MinutesPerHour;


        if (hours > 0){
            return String.format("%01d:%02d:%02d", hours, minutes, seconds);
        }
        else{
            return String.format("%02d:%02d", minutes, seconds);
        }

    }


}
































