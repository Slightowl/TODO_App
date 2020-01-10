package com.example.todoapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.UserAccounts;

import java.util.List;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<UserAccounts> userAccountsList;

    /* constructor */
    public ProfileRecyclerAdapter(Context context, List<UserAccounts> userAccountsList) {
        this.context = context;
        this.userAccountsList = userAccountsList;
    }

    /* inflate view from profile_row */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.profile_row, viewGroup, false);

        /* pass through constructor first */
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        /* each user object inside list */
        UserAccounts userAccounts = userAccountsList.get(position);

        viewHolder.userName.setText(userAccounts.getUserName());
        viewHolder.password.setText(userAccounts.getPassword());

    }

    @Override
    public int getItemCount() {
        return userAccountsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView userName;
        public TextView password;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            userName = itemView.findViewById(R.id.userName);
            password = itemView.findViewById(R.id.password);
        }

        /* retrieve user data from array when item clicked */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            UserAccounts userAccounts = userAccountsList.get(position);

            Log.d("adapter_clicked", "onClick: " + position + " UserName: " + userAccounts.getUserName());

        }
    }
}
