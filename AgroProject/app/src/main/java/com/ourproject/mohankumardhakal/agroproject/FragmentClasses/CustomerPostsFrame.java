package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CustomerPostsFrame extends Fragment {
    RecyclerView recyclerView;
    FirebaseDatabase  database;
    DatabaseReference dbRef;
    String user_id;
    FirebaseAuth firebaseAuth;
    ArrayList<PostsAttributes> list;
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_for_post, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        //database initialization
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
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
        return view;
    }
}
