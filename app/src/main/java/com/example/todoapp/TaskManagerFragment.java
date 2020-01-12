package com.example.todoapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.adapter.ProfileRecyclerAdapter;
import com.example.todoapp.data.TaskDatabaseHandler;
import com.example.todoapp.model.TaskList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TaskManagerFragment extends Fragment {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText taskItem;
    private EditText taskDescription;
    private TaskDatabaseHandler taskDatabaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_manager, container, false);

        TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(getActivity());

        /* test database */
        List<TaskList> taskLists = taskDatabaseHandler.getAllTaskList();
        for (TaskList taskList : taskLists) {
            Log.d("task_mangager_db", "onCreate: " + taskList.getTaskName() + " date: "
            + taskList.getTimeStamp());
        }

        /* Do something with this */
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });

        return view;
    }

    private void saveItem(View view) {

        TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(getActivity());
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
                /* for some reason have to add full path to class or doesn't recognise */
                getActivity().startActivity(new Intent(getActivity(),com.example.todoapp.ListActivity.class));

            }
        },1200); // delay for ~1sec
    }

    /* retrieve pop up resources into view */
    private void createPopupDialog() {
        builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.task_manager_popup, null);

        taskItem = view.findViewById(R.id.task_item);
        taskDescription = view.findViewById(R.id.task_description);

        /* save tasks into database through save button */
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!taskItem.getText().toString().isEmpty() && !taskDescription.getText()
                        .toString().isEmpty()) {

                    saveItem(view);

                } else {
                    Snackbar.make(view, "empty field not allowed", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(view);
        dialog = builder.create(); // create dialogue object
        dialog.show();
    }
}
