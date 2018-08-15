package agentsparkles.simplerunninglog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

class statsAdapter extends RecyclerView.Adapter<statsAdapter.ViewHolder> {
    runDatabase db;
    List<detailedStats> stats;

    public statsAdapter(runDatabase db, List<detailedStats> stats) {

        this.stats = stats;
        this.db = db;
    }

    @NonNull
    @Override
    public statsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_search,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull statsAdapter.ViewHolder holder, final int position) {


        holder.searchTitle.setText(stats.get(position).getSearchTitle());
        holder.searchRange.setText(stats.get(position).getSearchRange());
        holder.totalRuns.setText(stats.get(position).getTotalActivities());
        holder.totalDistance.setText(stats.get(position).getTotalDistance());
        holder.avgPace.setText(stats.get(position).getAvgPace());


    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchTitle;
        TextView searchRange;
        TextView totalRuns;
        TextView totalDistance;
        TextView totalTime;
        TextView avgPace;



        ViewHolder(View itemView) {
            super(itemView);
            searchTitle = itemView.findViewById(R.id.searchTitle);
            searchRange = itemView.findViewById(R.id.searchRange);
            totalRuns = itemView.findViewById(R.id.totalActivities);
            totalDistance = itemView.findViewById(R.id.totalMileage);
            avgPace = itemView.findViewById(R.id.weeklyTime);

        }



    }}