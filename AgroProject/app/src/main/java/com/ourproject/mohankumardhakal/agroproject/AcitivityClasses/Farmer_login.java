package com.ourproject.mohankumardhakal.agroproject.AcitivityClasses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ourproject.mohankumardhakal.agroproject.R;

public class Farmer_login extends Activity {

    Button login;
    TextView signup;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmer_login);
       login=findViewById(R.id.login_btn);
       signup=findViewById(R.id.sign_up);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Application_main.class);
                startActivityForResult(myIntent, 0);
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
}
