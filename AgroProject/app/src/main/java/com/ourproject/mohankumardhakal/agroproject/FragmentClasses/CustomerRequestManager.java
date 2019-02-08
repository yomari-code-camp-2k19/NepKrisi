package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.CustomerRequests;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.RequestAdapter;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
public class CustomerRequestManager extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;
    String current_user_id;
    ArrayList<CustomerRequests> arrayList;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_request_manager, null);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("Customer Request");
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        arrayList=new ArrayList<>();
        //recycler view initialization
        recyclerView = view.findViewById(R.id.myRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //fetching database values
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        for (DataSnapshot ds3 : ds2.getChildren()) {
                            CustomerRequests customerRequests = ds3.getValue(CustomerRequests.class);
                            if (customerRequests.getUser_id().equals(current_user_id)) {
                                arrayList.add(customerRequests);
                            }
                        }
                    }
                }
                if (arrayList!=null) {
                    mAdapter = new RequestAdapter(getContext(), arrayList);
                    recyclerView.setAdapter(mAdapter);
                }
                else {
                    Toast.makeText(getContext(), "No data in arraylist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
