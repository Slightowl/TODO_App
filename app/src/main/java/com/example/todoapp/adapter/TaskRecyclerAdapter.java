package com.example.todoapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.data.TaskDatabaseHandler;
import com.example.todoapp.model.TaskList;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TaskList> taskLists;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public TaskRecyclerAdapter(Context context, List<TaskList> taskLists) {
        this.context = context;
        this.taskLists = taskLists;
    }

    @NonNull
    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerAdapter.ViewHolder viewHolder, int position) {
        TaskList taskList = taskLists.get(position); // obj

        viewHolder.taskName.setText(taskList.getTaskName());
        viewHolder.taskDescr.setText(taskList.getTaskDescription());
        viewHolder.timeStamp.setText(taskList.getTimeStamp());

    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView taskName;
        public TextView taskDescr;
        public TextView timeStamp;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            taskName = itemView.findViewById(R.id.task_title);
            taskDescr = itemView.findViewById(R.id.task_description);
            timeStamp = itemView.findViewById(R.id.timestamp);
            editButton = itemView.findViewById(R.id.edit_task_button);
            deleteButton = itemView.findViewById(R.id.delete_task_button);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            /* get current position from adapter */
            int position;
            position = getAdapterPosition();
            TaskList taskList = taskLists.get(position);

            switch (v.getId()) {
                case R.id.edit_task_button:
                    editTask(taskList);
                    break;
                case R.id.delete_task_button:
                    deleteTask(taskList.getId());
                    break;
            }
        }

        /*
        delete task from database and
        also delete the card from the
        recyclerView
         */
        private void deleteTask(int id) {
            TaskDatabaseHandler db = new TaskDatabaseHandler(context);
            db.deleteTask(id); // Todo: check this
            taskLists.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }

        private void editTask(final TaskList newTask) {

            //TaskList taskList = taskLists.get(getAdapterPosition());

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.task_manager_popup, null);

            Button saveButton;
            final EditText taskName;
            final EditText taskDescription;
            TextView title;

            taskName = view.findViewById(R.id.task_manager_item);
            taskDescription = view.findViewById(R.id.task_manager_description);
            saveButton = view.findViewById(R.id.save_button);

            /* update text to say 'update' */
            saveButton.setText(R.string.update_text);

            title = view.findViewById(R.id.popup_title);

            title.setText(R.string.edit_item);
            taskName.setText(newTask.getTaskName());
            taskDescription.setText(newTask.getTaskDescription());

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    /* update the task */
                    TaskDatabaseHandler taskDatabaseHandler = new TaskDatabaseHandler(context);

                    newTask.setTaskName(taskName.getText().toString());
                    newTask.setTaskDescription(taskDescription.getText().toString());

                    if (!taskName.getText().toString().isEmpty() && !taskDescription.getText().toString().isEmpty()) {
                        taskDatabaseHandler.updateTask(newTask);

                        /*
                        Notify the system to update the
                        task live instead of having to restart
                        the app to see the change
                         */
                        notifyItemChanged(getAdapterPosition(), newTask);
                    } else {
                        Snackbar.make(view, "Field is empty", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
