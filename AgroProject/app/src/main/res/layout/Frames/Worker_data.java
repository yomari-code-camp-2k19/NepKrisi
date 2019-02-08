package com.example.mohankumardhakal.football_arena.Frames;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohankumardhakal.football_arena.HelperPackage.ExpandableListViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.ListViewAdapter;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.WorkerInfo;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Worker_data extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListViewAdapter customExpandableListViewAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

     database;
    DatabaseReference myRef;

    TextView add;

    //variables for dialog box
    FirebaseAuth firebaseAuth;
    TextView worker_name,cont_no,addr,start_date,salary;
    Button submit;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.worker_data, container, false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = currentFirebaseUser.getUid();
        String u_id="Worker/"+userID;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(u_id);

        expandableListView = view.findViewById(R.id.exp_list_view);
        SetStandardGroups();
        customExpandableListViewAdapter = new ListViewAdapter(getContext(),listDataHeader,listDataChild);
        expandableListView.setAdapter(customExpandableListViewAdapter);

       add=view.findViewById(R.id.add_worker);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worker_form();
            }
        });

        return view;
    }


    public void worker_form()
    {
        Log.e("TAG", "Inside the worker form " );
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_worker_form);
        dialog.setTitle("Worker Form");

        //actions to be performed within the form

        worker_name=dialog.findViewById(R.id.worker_name);
        cont_no=dialog.findViewById(R.id.contact_no);
        addr=dialog.findViewById(R.id.address);
        start_date=dialog.findViewById(R.id.start_date);
        salary=dialog.findViewById(R.id.salary);

        submit=dialog.findViewById(R.id.add_worker);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Worker");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentFirebaseUser.getUid();

        //userID="farmer1";
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerForm(userID);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void customerForm(String userID) {
        String name = worker_name.getText().toString();
        final String con_no = cont_no.getText().toString();
        final String address = addr.getText().toString();
        final String startDate = start_date.getText().toString();
        final String sal = salary.getText().toString();


        if (name == "" || con_no == "" || address == "") {
            Toast.makeText(getContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            return;
        } else {

            WorkerInfo wi = new WorkerInfo(con_no, address, startDate, sal);
            myRef.child(userID).child(name).setValue(wi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Successfully uploaded in the database", Toast.LENGTH_SHORT).show();


                    } else
                        Toast.makeText(getContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
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

                    String childNames =ds.getKey().toString()+":"+ ds.getValue().toString();
                    Log.e("TAG", "childNames :  " + childNames);
                    childItem.add(childNames);
                }

                listDataChild.put(listDataHeader.get(counter), childItem);
                counter++;
                Log.e("TAG", "counter :" + counter);

                customExpandableListViewAdapter.notifyDataSetChanged();
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
