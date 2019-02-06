package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.ListViewAdapter;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Worker_data extends Fragment {
    ExpandableListView expandableListView;
    ListViewAdapter customExpandableListViewAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_data, null);
        add = view.findViewById(R.id.add_worker);

        /*//FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //String userID = currentFirebaseUser.getUid();
        String u_id = "Worker/" + "farmer1";

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(u_id);

        expandableListView = view.findViewById(R.id.exp_list_view);
        SetStandardGroups();
        customExpandableListViewAdapter = new ListViewAdapter(getContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(customExpandableListViewAdapter);



*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Worker_form());
            }
        });
        return view;
    }

    public boolean loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        return true;
    }

    public void SetStandardGroups() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        myRef.addChildEventListener(new ChildEventListener() {
            int counter = 0;
            List<String> childItem = new ArrayList<>();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                listDataHeader.add(dataSnapshot.getKey());
                Log.e("TAG", listDataHeader.get(counter));
                childItem = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String childNames = ds.getKey().toString() + ":" + ds.getValue().toString();
                    Log.e("TAG", "childNames :  " + childNames);
                    childItem.add(childNames);
                }

                listDataChild.put(listDataHeader.get(counter), childItem);
                counter++;
                Log.e("TAG", "counter :" + counter);

//                customExpandableListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
