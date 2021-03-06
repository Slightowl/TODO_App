package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
Samuel Lightowler - [--/--/20]

Mobile application which allows a user to create
account with persistent data. CRUD operations allow
user to create, update and delete tasks.
*/

// Todo: Refactor code, lots of optimisation
// Todo: ListActivity back button has bug which can throw you off app (not crash)
// Todo: fix home page

public class NavigationActivity extends AppCompatActivity {

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
