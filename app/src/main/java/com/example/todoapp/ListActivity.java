package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.adapter.TaskRecyclerAdapter;
import com.example.todoapp.data.TaskDatabaseHandler;
import com.example.todoapp.model.TaskList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private List<TaskList> taskList;
    private TaskDatabaseHandler db; // may change this later
    private FloatingActionButton fab;
    private FloatingActionButton back;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText taskItem;
    private EditText taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        /*
        Retrieve resources
        initialise them
        pass db items into recyclerView
         */
        recyclerView = findViewById(R.id.list_recycler_view);
        fab = findViewById(R.id.fab_add_another_task);
        back = findViewById(R.id.fab_back_to_screen);

        db = new TaskDatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();

        /* retrieve tasks from database */
        taskList = db.getAllTaskList();

        for (TaskList taskList : taskList) {
            Log.d("list_activity_db", "onCreate: " + taskList.getTaskName());
        }

        taskRecyclerAdapter = new TaskRecyclerAdapter(this, taskList);
        recyclerView.setAdapter(taskRecyclerAdapter);
        taskRecyclerAdapter.notifyDataSetChanged();


        /* Floating action button */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });

        /* navigate back button */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
            }
        });
    }

    private void navigateBack() {
        startActivity(new Intent(ListActivity.this, NavigationActivity.class));
        finish();
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.task_manager_popup, null);

        taskItem = view.findViewById(R.id.task_manager_item);
        taskDescription = view.findViewById(R.id.task_manager_description);
        saveButton = view.findViewById(R.id.save_button);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!taskItem.getText().toString().isEmpty() && !taskDescription.getText().toString().isEmpty()) {
                    saveItem(view);
                } else {
                    Snackbar.make(view, "empty field not allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveItem(View view) {

        TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(this);
        TaskList taskList = new TaskList();

        String newTask = taskItem.getText().toString().trim();
        String newDescr = taskDescription.getText().toString().trim();

        taskList.setTaskName(newTask);
        taskList.setTaskDescription(newDescr);

        taskDatabaseHandler.addTaskName(taskList);

        Snackbar.make(view, "Task added!", Snackbar.LENGTH_SHORT).show();

        /* exit popup screen */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                /* for some reason have to add full path to class or doesn't recognise */
                startActivity(new Intent(ListActivity.this, com.example.todoapp.ListActivity.class));
                finish();
            }
        },1200); // delay for ~1sec
    }
}
