package com.ourproject.mohankumardhakal.agroproject.HelperClasses;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

//adpater class for each post
public class PostAdapters extends RecyclerView.Adapter<PostAdapters.MyViewHolder> {
    @NonNull
    @Override
    public PostAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull PostAdapters.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }

}
