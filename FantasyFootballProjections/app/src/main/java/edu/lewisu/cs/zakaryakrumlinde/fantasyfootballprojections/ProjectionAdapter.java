package edu.lewisu.cs.zakaryakrumlinde.fantasyfootballprojections;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProjectionAdapter extends RecyclerView.Adapter<ProjectionAdapter.ProjectionViewHolder> {
    private List<Projection> projections;
    @NonNull
    @Override
    public ProjectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.row, viewGroup, false);
        ProjectionViewHolder viewHolder = new ProjectionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectionViewHolder projectionViewHolder, int i) {
        Projection projection = projections.get(i);
        String name = projection.getName();
        String standard = projection.getStandard();
        String ppr = projection.getPpr();
        projectionViewHolder.nameTextView.setText(name);
        projectionViewHolder.standardTextView.setText(standard);
        projectionViewHolder.pprTextView.setText(ppr);
    }

    @Override
    public int getItemCount() {
        if(projections != null)
            return projections.size();
        return 0;
    }

    public void setProjectionData(List<Projection> projectionData){
        projections = projectionData;
        notifyDataSetChanged();
    }

    class ProjectionViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTextView;
        private final TextView standardTextView;
        private final TextView pprTextView;

        public ProjectionViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_text);
            standardTextView = itemView.findViewById(R.id.standard_text);
            pprTextView = itemView.findViewById(R.id.ppr_text);
        }
    }
}
