package com.assetslookup.ui.goals;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.assetslookup.R;
import com.assetslookup.data.db.entities.Goal;
import com.assetslookup.data.internal.IFragmentInteraction;

import com.assetslookup.ui.shared.BaseChildNestedFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalsListFragment extends BaseChildNestedFragment implements IFragmentInteraction {

    private RecyclerView goalsList;
    private List<Goal> goals;
    private GoalListAdapter goalListAdapter;
    private ProgressBar progressGoals;

    public GoalsListFragment() {
        goals = new ArrayList<>();
        goalListAdapter = new GoalListAdapter(goals, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goals_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goalsList = view.findViewById(R.id.goalsList);
        progressGoals = view.findViewById(R.id.progressGoals);
        goalsList.setLayoutManager(new LinearLayoutManager(getContext()));
        goalsList.setAdapter(goalListAdapter);
        refreshGoals();

    }

    public void refreshGoals() {
        assetsService.getAllGoals().enqueue(new Callback<List<Goal>>() {
            @Override
            public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                if(response.code() == 200) {
                    if(response.body() != null) {
                        goals.clear();
                        goals.addAll(response.body());
                        goalListAdapter.notifyDataSetChanged();
                        progressGoals.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Goal>> call, Throwable t) {
                Log.e("DBX", t.getMessage());
            }
        });
    }

    @Override
    public void sendMessage(Message message) {
        if(message.what == 1) {
            Goal goal = (Goal) message.obj;
            Bundle bundle = new Bundle();
            bundle.putString("GOAL_ID", goal.getId());
            fragmentManagerHelper.attach(GoalDetail.class, bundle);

        }
    }
}
