package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserSessionCheck extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            try {
                startActivity(new Intent(UserSessionCheck.this, Application_main.class));
            } catch (Exception e) {
                Log.i("Exception :", e.getMessage().toString());
            }
        }

    }
}
