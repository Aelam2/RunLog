package agentsparkles.simplerunninglog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

class runAdapter extends RecyclerView.Adapter<runAdapter.ViewHolder> {

    List<runEntry> runs;

    public runAdapter(List<runEntry> runs) {
        this.runs = runs;
    }

    @NonNull
    @Override
    public runAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull runAdapter.ViewHolder holder, int position) {
        holder.runDate.setText(String.valueOf(runs.get(position).getDate()));
        holder.runTitle.setText(runs.get(position).getTitle());
        holder.runType.setText(runs.get(position).getRunType());
        holder.surfaceType.setText(runs.get(position).getSurfaceType());
        holder.totalDistance.setText(String.valueOf(runs.get(position).getTotalDistance()));
        holder.totalTime.setText(String.valueOf(runs.get(position).getTotalTime()));
    }

    @Override
    public int getItemCount() {
        return runs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView runTitle;
        public TextView runDate;
        public TextView runType;
        public TextView surfaceType;
        public TextView totalDistance;
        public TextView totalTime;

        public ViewHolder(View itemView) {
            super(itemView);
            runTitle = itemView.findViewById(R.id.runTitle);
            runDate = itemView.findViewById(R.id.runDate);
            runType = itemView.findViewById(R.id.runType);
            surfaceType = itemView.findViewById(R.id.surfaceType);
            totalDistance = itemView.findViewById(R.id.totalDistance);
            totalTime = itemView.findViewById(R.id.totalTime);

        }
    }
}































