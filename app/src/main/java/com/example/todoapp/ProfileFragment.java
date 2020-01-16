package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.ProfileRecyclerAdapter;
import com.example.todoapp.data.UserDatabaseHandler;
import com.example.todoapp.model.UserAccounts;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProfileRecyclerAdapter recyclerViewAdapter;
    private ArrayList<UserAccounts> userArrayList;
    private ArrayAdapter arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_manager, container, false);

        /* Create List of user accounts */
        recyclerView = view.findViewById(R.id.profile_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userArrayList = new ArrayList<>();
        UserDatabaseHandler db = new UserDatabaseHandler(getActivity());

        final List<UserAccounts> userAccountsList = db.getAllUserAccounts();
        for (UserAccounts userAccounts: userAccountsList) {
            Log.d("NavigationActivity", "onCreate " + userAccounts.getUserName());
            userArrayList.add(userAccounts);
        }

        /* configure recycler adapter */
        recyclerViewAdapter = new ProfileRecyclerAdapter(getActivity(), userArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}
