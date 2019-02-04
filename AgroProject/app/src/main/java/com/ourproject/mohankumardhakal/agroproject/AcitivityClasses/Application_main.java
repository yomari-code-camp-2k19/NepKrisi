package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.CustomerPostsFrame;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.FarmersPostFrame;
import com.ourproject.mohankumardhakal.agroproject.R;
public class Application_main extends AppCompatActivity implements LocationListener {
    ViewPager pager;
    TextView tv1, tv2;
    String user_id;
    double lattitude, longitude;
    FirebaseAuth firebaseAuth;
    protected LocationManager locationManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //logging out the user from main activity
        switch (id) {
            case R.id.menu:
                Intent intent = new Intent(this, Customer_signin.class);
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                user_id = firebaseUser.getUid();
                intent.putExtra("uid", user_id);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_main);
        pager = findViewById(R.id.container);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //getting the current location of the user
        //checks if permission isn't granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Application_main.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //if permission is already granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Application_main.this);
        }

        String name = getIntent().getStringExtra("Name");
        tv1 = findViewById(R.id.tab1);
        tv2 = findViewById(R.id.tab2);
        //setsAdapter for different fragments
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //what to do when the particular tab is selected
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv1.setBackgroundColor(Color.CYAN);
                } else if (position == 1) {
                    tv2.setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //knows which tab is clicked in viewPager
    public void tabClick(View view) {
        if (view.getId() == R.id.tab1) {
            pager.setCurrentItem(0);
        } else if (view.getId() == R.id.tab2) {
            pager.setCurrentItem(1);
        }
    }

    //dialog to pop up to ask for content with farmers by customer
    public void showPopUp(View v) {
        Dialog dialog = new Dialog(this, R.style.Dialog);
        dialog.setTitle("Request Here");
        View view = LayoutInflater.from(this).inflate(R.layout.product_request_dialog, null);
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.lattitude = location.getLatitude();
        this.longitude = location.getLongitude();
//        Toast.makeText(this, ""+lattitude+"and"+longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //viewpageadapter inner class to hold different fragment
    private class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                FarmersPostFrame farmersPostFrame = new FarmersPostFrame();
                farmersPostFrame.setArguments(sendDatatoFragment(lattitude,longitude));
                return farmersPostFrame;
            } else {
                CustomerPostsFrame customerPostsFrame = new CustomerPostsFrame();
                customerPostsFrame.setArguments(sendDatatoFragment(lattitude,longitude));
                return customerPostsFrame;

            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    //function to send data to fragment
    public Bundle sendDatatoFragment(double lattitude,double longitude) {
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id);
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("lattitude", lattitude);
        return bundle;
    }

    //listens once the premission is granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Application_main.this);
            }
        }
    }
}
