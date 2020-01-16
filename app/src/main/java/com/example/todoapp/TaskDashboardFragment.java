package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.ProfileRecyclerAdapter;
import com.example.todoapp.adapter.TaskDashRecyclerAdapter;
import com.example.todoapp.adapter.TaskRecyclerAdapter;
import com.example.todoapp.data.TaskDatabaseHandler;
import com.example.todoapp.data.UserDatabaseHandler;
import com.example.todoapp.model.TaskList;
import com.example.todoapp.model.UserAccounts;

import java.util.ArrayList;
import java.util.List;

public class TaskDashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskDashRecyclerAdapter recyclerAdapter;
    private ArrayList<TaskList> taskList;
    private ArrayAdapter arrayAdapter;
    private Button completeTask;
    private ProgressBar progressBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_dashboard, container, false);

        // Todo: implement recyclerView
  /*      recyclerView = view.findViewById(R.id.dashboard_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        taskList = new ArrayList<>();
        TaskDatabaseHandler db = new TaskDatabaseHandler(getActivity());

        recyclerAdapter = new TaskDashRecyclerAdapter(getActivity(), taskList);
        recyclerView.setAdapter(recyclerAdapter);*/

        completeTask = view.findViewById(R.id.dashboard_complete_task);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setMax(100);
        progressBar.setProgress(0);



        completeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = 5;

                if(count < 100) {
                    progressBar.setProgress(count++);
                }

            }
        });
        return view;
    }
}
