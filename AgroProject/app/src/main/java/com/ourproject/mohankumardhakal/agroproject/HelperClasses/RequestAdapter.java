package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    ArrayList<CustomerRequests> post_information_list;
    Context context;
    View view;
    CustomerRequests customerRequests;

    public RequestAdapter(Context context, ArrayList<CustomerRequests> list) {
        this.post_information_list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_post_sample, null);
        return new RequestAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.MyViewHolder holder, int position) {
        customerRequests = post_information_list.get(position);
        if (customerRequests != null) {
            holder.name.setText(customerRequests.getName_value());
            Log.i("name", customerRequests.getName_value());
            holder.contact.setText(customerRequests.getContact_value());
//            Log.i("location", customerRequests.getLocation());
//            holder.location.setText(customerRequests.getLocation());
        }
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return post_information_list.size();
    }


    //inner viewholder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView confirmButton, name, contact, title, location, quantity;
        ImageView call;


        public MyViewHolder(View view) {
            super(view);
            confirmButton = view.findViewById(R.id.confirmRequest);
            name = view.findViewById(R.id.name);
            contact = view.findViewById(R.id.contact);
            title = view.findViewById(R.id.post_title);
            location = view.findViewById(R.id.post_location);
            quantity = view.findViewById(R.id.qty);
            call = view.findViewById(R.id.call);
        }
    }
}
