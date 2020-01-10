package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.todoapp.data.DatabaseHandler;
import com.example.todoapp.model.UserAccounts;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Reference navigation resource and pass in listener
        Get home fragment as default fragment
         */
        BottomNavigationView BottomNavigation = findViewById(R.id.bottom_navigation);
        BottomNavigation.setOnNavigationItemSelectedListener(BottomNavListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                 new HomeFragment()).commit();
    }

    /*
    Create listener for Navigation bar which
    checks which Fragment has been selected
    */
    private BottomNavigationView.OnNavigationItemSelectedListener BottomNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_task_dashboard:
                            selectedFragment = new TaskDashboardFragment();
                            break;
                        case R.id.nav_task_manager:
                            selectedFragment = new TaskManagerFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }
                    /*
                    begin fragment transaction
                    This will display the current selected fragment
                     */
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
