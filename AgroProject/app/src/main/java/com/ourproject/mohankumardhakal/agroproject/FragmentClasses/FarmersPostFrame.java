package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ourproject.mohankumardhakal.agroproject.R;

public class FarmersPostFrame extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_for_post, null);
        recyclerView = view.findViewById(R.id.recyclerView);
system.out.println("This is the remote changes i made on my another github");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
