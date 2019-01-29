package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.CustomerPostsFrame;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.PieSupplyChartFragment;
import com.ourproject.mohankumardhakal.agroproject.R;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.container);
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

    //viewpageadapter inner class to hold different fragment
    private class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                PieSupplyChartFragment pieSupplyChartFragment = new PieSupplyChartFragment();
//                farmersPostFrame.setArguments(sendDatatoFragment());
                return pieSupplyChartFragment;
            } else {
                CustomerPostsFrame customerPostsFrame = new CustomerPostsFrame();
//                customerPostsFrame.setArguments(sendDatatoFragment());
                return customerPostsFrame;

            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
//function to send data to fragment
    /*public Bundle sendDatatoFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("match_id", match_id);
        return bundle;
    }*/
}
