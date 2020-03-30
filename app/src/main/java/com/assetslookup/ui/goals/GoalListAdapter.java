package com.assetslookup.ui.goals;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Goal;
import com.assetslookup.data.internal.IFragmentInteraction;


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
    public void onBindViewHolder(@NonNull GoalsListViewHolder goalsListViewHolder, final int i) {
        goalsListViewHolder.txtName.setText(goals.get(i).getName());
        goalsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(1, goals.get(i));
            }
        });
    }

    private void sendMessage(int what, Object obj) {
        Message m = new Message();
        m.what = what;
        m.obj = obj;
        ((IFragmentInteraction)fragment).sendMessage(m);
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
