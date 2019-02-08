package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.FragmentClasses.FarmersPostFrame;
import com.ourproject.mohankumardhakal.agroproject.R;
import static android.content.ContentValues.TAG;
public class Farmer_login extends Activity {
    Button login;
    TextView signup, farmname, email, password;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    String farm_name_s;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        login = findViewById(R.id.login_btn);
        signup = findViewById(R.id.sign_up);
        email = findViewById(R.id.email);
        farmname = findViewById(R.id.farm_name);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                check(view);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Register_farmer.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    public void check(final View view) {
        String email_s = email.getText().toString();
        String password_s = password.getText().toString();
        farm_name_s = farmname.getText().toString();

        if (TextUtils.isEmpty(farm_name_s)) {
            Toast.makeText(getApplication(), "Please enter the name of the farm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email_s)) {
            Toast.makeText(getApplication(), "Please enter your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password_s)) {
            Toast.makeText(getApplication(), "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email_s, password_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    myRef = firebaseDatabase.getInstance().getReference("User Info");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                String fname = postSnapshot.child("farm_name").getValue(String.class);
                                farm_name_s = farmname.getText().toString();
                                if (fname.equals(farm_name_s)) {
                                    Intent myIntent = new Intent(Farmer_login.this, FarmersCreatePost.class);
                                    startActivity(myIntent);
                                    finish();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.e(TAG, "Failed to read app title value.", error.toException());
                        }
                    });


                    Toast.makeText(getApplication(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Email and password not correct ot unregistered. Please check and continue", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}