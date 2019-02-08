package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.CustomerPostsFrame;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.FarmersPostFrame;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.PieDemandChartFragment;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.PieSupplyChartFragment;
import com.ourproject.mohankumardhakal.agroproject.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager pager;
    FirebaseAuth firebaseAuth;
    TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = findViewById(R.id.containerview);
        tv1 = findViewById(R.id.tab1);
        tv2 = findViewById(R.id.tab2);
        tv3 = findViewById(R.id.tab3);
        tv4 = findViewById(R.id.tab4);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setViewPager();
    }

    private void setViewPager() {
        //setsAdapter for different fragments
        //for farmerPostFragment
        Log.i("Tag", "here1");
        pager.setAdapter(new HomeActivity.ViewPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //what to do when the particular tab is selected
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv1.setBackgroundColor(Color.CYAN);
                    tv2.setBackgroundColor(Color.WHITE);
                    tv3.setBackgroundColor(Color.WHITE);
                    tv4.setBackgroundColor(Color.WHITE);

                } else if (position == 1) {
                    tv2.setBackgroundColor(Color.CYAN);
                    tv1.setBackgroundColor(Color.WHITE);
                    tv3.setBackgroundColor(Color.WHITE);
                    tv4.setBackgroundColor(Color.WHITE);
                } else if (position == 2) {
                    tv3.setBackgroundColor(Color.CYAN);
                    tv2.setBackgroundColor(Color.WHITE);
                    tv1.setBackgroundColor(Color.WHITE);
                    tv4.setBackgroundColor(Color.WHITE);
                } else if (position == 3) {
                    tv4.setBackgroundColor(Color.CYAN);
                    tv3.setBackgroundColor(Color.WHITE);
                    tv2.setBackgroundColor(Color.WHITE);
                    tv1.setBackgroundColor(Color.WHITE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser != null) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("User Info");
                if (databaseReference.child(firebaseUser.getUid()) != null) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    startActivity(new Intent(HomeActivity.this, Farmer_login.class));
                    finish();
                } else {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    startActivity(new Intent(HomeActivity.this, Customer_signin.class));
                    finish();
                }

            }


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(HomeActivity.this, BottomNavigation.class));
//            finish();
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(HomeActivity.this, News_activity.class));

        } else if (id == R.id.nav_slideshow) {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("User Info");
                if (databaseReference.child(firebaseUser.getUid()) != null) {
                    startActivity(new Intent(HomeActivity.this, FarmersCreatePost.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, CustomerCreatePost.class));
                }

            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //loads fragment from bottom navigation bar
    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.firstfragment, fragment);
            transaction.commit();
        }
    }

    //viewpageadapter inner class to hold different fragment
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                tv1.setBackgroundColor(Color.CYAN);
                FarmersPostFrame farmersPostFrame = new FarmersPostFrame();
                return farmersPostFrame;
            } else if (position == 1) {
                CustomerPostsFrame customerPostsFrame = new CustomerPostsFrame();
                return customerPostsFrame;
            } else if (position == 2) {
                PieDemandChartFragment pieDemandChartFragment = new PieDemandChartFragment();
                return pieDemandChartFragment;

            } else {
                PieSupplyChartFragment pieSupplyChartFragment = new PieSupplyChartFragment();
                return pieSupplyChartFragment;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    //knows which tab is clicked in viewPager
    public void tabClick(View view) {
        if (view.getId() == R.id.tab1) {
            pager.setCurrentItem(0);
        } else if (view.getId() == R.id.tab2) {
            pager.setCurrentItem(1);
        } else if (view.getId() == R.id.tab3) {
            pager.setCurrentItem(2);

        } else if (view.getId() == R.id.tab4) {
            pager.setCurrentItem(3);

        }

    }
}
