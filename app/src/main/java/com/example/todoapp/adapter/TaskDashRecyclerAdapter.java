package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.TaskList;

import java.util.List;

public class TaskDashRecyclerAdapter extends RecyclerView.Adapter<TaskDashRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TaskList> taskLists;

    public TaskDashRecyclerAdapter(Context context, List<TaskList> taskLists) {
        this.context = context;
        this.taskLists = taskLists;
    }

    @NonNull
    @Override
    public TaskDashRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDashRecyclerAdapter.ViewHolder viewHolder, int position) {
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
        public Button confirmButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            taskName = itemView.findViewById(R.id.task_title);
            taskDescr = itemView.findViewById(R.id.task_description);
            timeStamp = itemView.findViewById(R.id.timestamp);
            confirmButton = itemView.findViewById(R.id.delete_task_button);
            confirmButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            // Todo
        }
    }
}
