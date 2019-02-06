package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> list;

    public SpinnerAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView view = new TextView(context);
        view.setPadding(10, 10, 10, 10);
        view.setText(list.get(position));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView view = new TextView(context);
        view.setPadding(10, 10, 10, 10);
        view.setText(list.get(position));
        return view;
    }
}

