package com.ourproject.mohankumardhakal.agroproject.HelperClasses;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

//adpater class for farmers post
public class PostAdapters extends RecyclerView.Adapter<PostAdapters.MyViewHolder> {
    ArrayList<PostsAttributes> post_information_list;
    PostsAttributes info;
    Context context;
    View view;
    int index;
    String token;
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    //for customers data
    public PostAdapters(Context context, ArrayList<PostsAttributes> list, int i) {
        this.post_information_list = list;
        this.context = context;
        this.index = i;
    }


    @NonNull
    @Override
    public PostAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (index == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_request_sample, null);
            return new MyViewHolder(view, index);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_sample, null);
            return new MyViewHolder(view, index);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final PostAdapters.MyViewHolder holder, int position) {
        if (index == 1) {
            info = post_information_list.get(position);
            holder.post_title.setText(info.getPost_title());
            holder.post_desc.setText(info.getPost_description());
            holder.location.setText(String.valueOf(info.getPost_location()));

        } else {
            info = post_information_list.get(position);
            holder.post_title.setText(info.getPost_title());
            holder.post_desc.setText(info.getPost_description());
            holder.distance.setText(getDistance(info.getLattitude(), info.getLongitude(), info.getCurrent_lat(), info.getCurrent_long()));
            holder.location.setText(String.valueOf(info.getPost_location()));
            Glide.with(context).load(info.getImageUri()).into(holder.image);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopUp(v, info.getUser_id(), info.getPost_id(),info.getPost_location());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return post_information_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView button, distance, post_title, post_desc, location;
        ImageView image;


        public MyViewHolder(View view, int index) {
            super(view);
            if (index == 1) {
                post_title = view.findViewById(R.id.post_title);
                post_desc = view.findViewById(R.id.post_desc);
                location = view.findViewById(R.id.postedFrom);
            } else {

                post_title = view.findViewById(R.id.post_title);
                post_desc = view.findViewById(R.id.post_desc);
                location = view.findViewById(R.id.post_location);
                image = view.findViewById(R.id.my_image);
                distance = view.findViewById(R.id.distance);
                button = view.findViewById(R.id.query);
            }
        }
    }

    public String getDistance(double lat, double long_1, double lattitude, double longitude) {
        float results[] = new float[10];
        Location.distanceBetween(lat, long_1, lattitude, longitude, results);
        int kmValue = (int) (results[0] / 1000);
        return String.valueOf(kmValue) + "Km";
    }

    //dialog to pop up to ask for content with farmers by customer
    public void showPopUp(View v, final String user_id, final String post_id,final String location) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setTitle("Request Here");
        View view = LayoutInflater.from(context).inflate(R.layout.product_request_dialog, null);
        dialog.setContentView(view);
        dialog.show();
        dialog.setCancelable(false);
        final EditText name, contact, qty;
        name = view.findViewById(R.id.name);
        contact = view.findViewById(R.id.contact);
        qty = view.findViewById(R.id.qty);
        Button send = view.findViewById(R.id.send);
        Button cancel = view.findViewById(R.id.cancel);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_value = name.getText().toString();
                String contact_value = contact.getText().toString();
                String qty_value = qty.getText().toString();
                CustomerRequests customerRequests = new CustomerRequests(name_value, contact_value, qty_value, post_id,user_id,location);
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
                DatabaseReference dbRef = database.getInstance().getReference("Customer Request");
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String current_user_id=firebaseAuth.getUid();

                dbRef.child(user_id).child(current_user_id).child(post_id).setValue(customerRequests).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Request to db with id" + user_id, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "request store denied see log", Toast.LENGTH_SHORT).show();
                            Log.i("Error", task.getException().getMessage().toString());
                        }
                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



}
