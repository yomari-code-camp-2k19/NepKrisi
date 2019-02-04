package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.FarmerInfo;
import com.ourproject.mohankumardhakal.agroproject.R;

public class Farmer_profile_form extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    TextView firstname, lastname, username, pan, phoneno, location, aboutfarm;
    Button submit;
    String userID, email_value;
    String farm_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_profile_form);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        username = findViewById(R.id.username);
        pan = findViewById(R.id.pan);
        phoneno = findViewById(R.id.pan);
        location = findViewById(R.id.location);
        aboutfarm = findViewById(R.id.aboutfarm);
        submit = findViewById(R.id.submit);
        //getting value from previous activity
        farm_name = getIntent().getStringExtra("farm_name");
        email_value = getIntent().getStringExtra("email_value");
        //database initialization
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("User Info");
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentFirebaseUser.getUid();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                farmer_profile_form(userID);
            }
        });
    }

    public void farmer_profile_form(String userID) {

        String first_name = firstname.getText().toString();
        String last_name = lastname.getText().toString();
        String user_name = username.getText().toString();
        String pan_no = pan.getText().toString();
        String phone_no = phoneno.getText().toString();
        String location_farm = location.getText().toString();
        String about_farm = aboutfarm.getText().toString();


        if (first_name == "" || last_name == "" || user_name == "" || pan_no == "" || phone_no == "" || location_farm == "" || about_farm == "") {
            Toast.makeText(getApplication(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            FarmerInfo farmerInfo = new FarmerInfo(first_name, last_name, user_name, pan_no, phone_no, location_farm, about_farm, farm_name,email_value);
            myRef.child(userID).setValue(farmerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Farmer_profile_form.this, Farmer_login.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(Farmer_profile_form.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
