package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostAdapters;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostsAttributes;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
public class CustomerPostsFrame extends Fragment implements LocationListener {
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    String user_id;
    FirebaseAuth firebaseAuth;
    ArrayList<PostsAttributes> list;
    private RecyclerView.Adapter mAdapter;
    SwipeRefreshLayout refresher;
    View view;
    LocationManager locationManager;
    double lattitude;
    double longitude;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //location related stuffs
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        view = inflater.inflate(R.layout.recyclerview_for_post, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //if permission is already granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CustomerPostsFrame.this);
        }
        //phone permissions

        //database initialization
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getInstance().getReference("Customer Posts");
        user_id = firebaseAuth.getCurrentUser().getUid();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        PostsAttributes postsAttributes = ds1.getValue(PostsAttributes.class);
                        postsAttributes.setCurrent_long(longitude);
                        postsAttributes.setCurrent_lat(lattitude);
                        list.add(postsAttributes);
                    }
                }
                mAdapter = new PostAdapters(getActivity(), list, 1);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("Error", "Failed to read value.", error.toException());
            }
        });

        //on refresh listener
        refresher = view.findViewById(R.id.refesher);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearRecyclerVIew();
                refreshData();
                refresher.setRefreshing(false);
            }
        });
        return view;
    }
    public void clearRecyclerVIew() {
        list.clear();
        mAdapter = new PostAdapters(getContext(), list, 0);
        recyclerView.setAdapter(mAdapter);
    }


    public void refreshData() {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Customer Posts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        PostsAttributes postsAttributes = ds1.getValue(PostsAttributes.class);
                        postsAttributes.setCurrent_long(longitude);
                        postsAttributes.setCurrent_lat(lattitude);
                        list.add(postsAttributes);
                    }
                }
                mAdapter = new PostAdapters(getActivity(), list, 1);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("Error", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        this.lattitude = location.getLatitude();
        this.longitude = location.getLongitude();

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
    //listens once the premission is granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, CustomerPostsFrame.this);
            }
        }
    }
}
