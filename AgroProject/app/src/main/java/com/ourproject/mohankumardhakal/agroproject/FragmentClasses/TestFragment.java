package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostsAttributes;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.SpinnerAdapter;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;

public class TestFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference dbRef;
    ArrayList<PostsAttributes> list;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ArrayList<String> product_titlelist;
    int minIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        list = new ArrayList<>();
        product_titlelist = new ArrayList<>();
        spinner = view.findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
//        getData();
        return view;
    }

    public void getData() {
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Farmer Posts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        PostsAttributes postsAttributes = ds2.getValue(PostsAttributes.class);
                        product_titlelist.add(postsAttributes.getPost_title());
                    }
                }

                spinnerAdapter = new SpinnerAdapter(getContext(), product_titlelist);
                spinner.setAdapter(spinnerAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "new toast", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
