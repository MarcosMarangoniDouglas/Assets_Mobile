package com.assetslookup.ui.goals;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assetslookup.R;
import com.assetslookup.data.db.entities.Goal;
import com.assetslookup.data.db.entities.Image;
import com.assetslookup.data.internal.IFragmentInteraction;
import com.assetslookup.data.internal.ImageHelper;
import com.assetslookup.data.internal.ImageHelperImpl;
import com.assetslookup.ui.shared.BaseChildNestedFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalDetail extends BaseChildNestedFragment implements IFragmentInteraction {

    String goalId;
    Goal goal;
    TextView txtName;
    RecyclerView rvBoxes;
    ImageView imagGraph;

    public GoalDetail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            goalId = args.getString("GOAL_ID");

        }
    }

    private void refreshGoal() {
        if(goalId.isEmpty()) return;
        assetsService.getGoal(goalId).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                goal = response.body();
                txtName.setText(goal.getName());
                getGraph(goal);
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {

            }
        });
    }

    private void getGraph(final Goal goal) {
        assetsService.getGraph(goal).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Image image = response.body();
                ImageHelper imageHelper = new ImageHelperImpl();
                Bitmap bitmap = imageHelper.convertBase64ToBitmap(image.getImage());
                imagGraph.setImageBitmap(bitmap);
                rvBoxes.setLayoutManager(new LinearLayoutManager(getContext()));
                rvBoxes.setAdapter(new GoalDetailBoxAdapter(goal.getBoxes()));
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d("DBX", t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = view.findViewById(R.id.txtGoalName);
        imagGraph = view.findViewById(R.id.imgGraph);
        rvBoxes = view.findViewById(R.id.rvGoalsBoxes);
        refreshGoal();
    }

    @Override
    public void sendMessage(Message message) {

    }
}