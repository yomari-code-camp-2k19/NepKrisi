package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.R;

public class ProfileFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView pfname, plname, paddress, pBio, pphon, pj;
    String Uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, null);
        pfname = view.findViewById(R.id.pfirst_name);
        plname = view.findViewById(R.id.plast_name);
        paddress = view.findViewById(R.id.plocation);
        pBio = view.findViewById(R.id.pbio);
        pphon = view.findViewById(R.id.pphone);
        pj = view.findViewById(R.id.pjob);

        FirebaseUser currentfirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Uid = currentfirebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("customer_info");
        //Read from database
        databaseReference.orderByChild("userid").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot infosnapshot : dataSnapshot.getChildren()) {
                    String firstname = infosnapshot.child("firstname").getValue().toString();
                    String lastname = infosnapshot.child("lastname").getValue().toString();
                    String address = infosnapshot.child("address").getValue().toString();
                    String bio = infosnapshot.child("bio").getValue().toString();
                    String job = infosnapshot.child("job").getValue().toString();
                    String contact = infosnapshot.child("contact").getValue().toString();
                    pfname.setText(firstname);
                    plname.setText(lastname);
                    paddress.setText(address);
                    pBio.setText(bio);
                    pphon.setText(contact);
                    pj.setText(job);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
