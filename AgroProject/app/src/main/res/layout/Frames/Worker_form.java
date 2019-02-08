package com.example.mohankumardhakal.football_arena.Frames;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.WorkerInfo;
import com.ourproject.mohankumardhakal.agroproject.R;

public class Worker_form extends Fragment {

        FirebaseDatabase mFirebaseDatabase;
        DatabaseReference myRef;
        FirebaseAuth firebaseAuth;
        TextView worker_name,cont_no,addr,start_date,salary;
        Button submit;
        String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_worker_form,null);

        worker_name=view.findViewById(R.id.worker_name);
        cont_no=view.findViewById(R.id.contact_no);
        addr=view.findViewById(R.id.address);
        start_date=view.findViewById(R.id.start_date);
        salary=view.findViewById(R.id.salary);

        submit=view.findViewById(R.id.add_worker);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Worker");

       // FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       // userID = currentFirebaseUser.getUid();

        userID="farmer1";

        submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customerForm(userID);
                }
            });
            return view;
        }

        public void customerForm(String userID) {

            String name=worker_name.getText().toString();
            final String con_no=cont_no.getText().toString();
            final String address= addr.getText().toString();
            final String startDate = start_date.getText().toString();
            final String sal = salary.getText().toString();


            if (name== "" || con_no == "" || address == "" ) {
                Toast.makeText(getContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                return;
            } else {

                WorkerInfo wi=new WorkerInfo(con_no,address,startDate,sal);
                myRef.child(userID).child(name).setValue(wi).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getContext(), com.example.mohankumardhakal.football_arena.Frames.Worker_data.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }


}
