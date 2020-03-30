package com.assetslookup.ui.goals;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.GoalBox;

import java.text.NumberFormat;
import java.util.List;

public class GoalDetailBoxAdapter extends RecyclerView.Adapter<GoalDetailBoxAdapter.BoxViewHolder> {

    List<GoalBox> boxes;

    public GoalDetailBoxAdapter(List boxes) {
        this.boxes = boxes;
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_goal_detail_item, viewGroup, false);
        return new BoxViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder boxViewHolder, int i) {
        boxViewHolder.txtDescription.setText(boxes.get(i).getDescription());
        NumberFormat nFormat = NumberFormat.getCurrencyInstance();
        boxViewHolder.txtValue.setText(nFormat.format(boxes.get(i).getValue()));
    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

    public class BoxViewHolder extends RecyclerView.ViewHolder{
        TextView txtDescription;
        TextView txtValue;
        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }
}
