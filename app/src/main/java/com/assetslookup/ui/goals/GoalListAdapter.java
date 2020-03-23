package com.assetslookup.ui.goals;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalsListViewHolder> {

    List<Goal> goals;
    private Fragment fragment;

    public GoalListAdapter(List<Goal> goals, Fragment fragment) {
        this.goals = goals;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public GoalsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_goals_list_item, viewGroup, false);
        GoalsListViewHolder viewHolder = new GoalsListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsListViewHolder goalsListViewHolder, int i) {
        goalsListViewHolder.txtName.setText(goals.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class GoalsListViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public GoalsListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
