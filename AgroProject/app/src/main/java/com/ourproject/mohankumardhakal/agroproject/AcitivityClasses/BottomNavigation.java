package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.CustomerRequestManager;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.ProfileFragment;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.Worker_data;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.Worker_form;
import com.ourproject.mohankumardhakal.agroproject.R;

public class BottomNavigation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //loads fragment from bottom navigation bar
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.firstfragment, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.table:
                    fragment = new ProfileFragment();
                    //toggleNavigationBar();
                    break;
                case R.id.livescore:
                    fragment=new Worker_data();
                   break;
                case R.id.stats:
                    fragment=new CustomerRequestManager();
                    break;
            }
            return loadFragment(fragment);
        }
    };
}
