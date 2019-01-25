package com.ourproject.mohankumardhakal.agroproject;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_sample);

    }

    public void showPopUp(View v) {
        Dialog dialog = new Dialog(this, R.style.Dialog);
        dialog.setTitle("Request Here");
        View view = LayoutInflater.from(this).inflate(R.layout.product_request_dialog, null);
        dialog.setContentView(view);
        dialog.show();
    }
}
