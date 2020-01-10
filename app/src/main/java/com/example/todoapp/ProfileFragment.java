package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.data.DatabaseHandler;
import com.example.todoapp.model.UserAccounts;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> userArrayList;
    private ArrayAdapter arrayAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = listView.findViewById(R.id.profileListView);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_manager, container, false);

        /* Create List of user accounts */
        listView = view.findViewById(R.id.profileListView);
        userArrayList = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(getActivity());

        //DatabaseHandler db = new DatabaseHandler(ProfileFragment.this);

        /*  TEST ACCOUNTS  */
        /*                 */

        List<UserAccounts> userAccountsList = db.getAllUserAccounts();

        for (UserAccounts userAccounts: userAccountsList) {
            Log.d("MainActivity", "onCreate " + userAccounts.getUserName());
            userArrayList.add(userAccounts.getUserName());
        }

        /* create array adapter */

        arrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_list_item_1, userArrayList
        );

        /* implement with listview */
        listView.setAdapter(arrayAdapter);

        return view;
    }
}
