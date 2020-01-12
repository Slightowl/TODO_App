package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.todoapp.adapter.TaskRecyclerAdapter;
import com.example.todoapp.data.TaskDatabaseHandler;
import com.example.todoapp.model.TaskList;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private List<TaskList> taskList;
    private TaskDatabaseHandler db; // may change this later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.list_recycler_view);

        db = new TaskDatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();

        // retrieve tasks from database
        taskList = db.getAllTaskList();

        for (TaskList taskList : taskList) {
            Log.d("list_activity_db", "onCreate: " + taskList.getTaskName());
        }

        taskRecyclerAdapter = new TaskRecyclerAdapter(this, taskList);
        recyclerView.setAdapter(taskRecyclerAdapter);
        taskRecyclerAdapter.notifyDataSetChanged();
    }
}
