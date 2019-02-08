package com.example.mohankumardhakal.football_arena.Frames;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.mefittech.mohankumardhakal.football_arena.R;

public class StatisticsAboutMatchFrame extends Fragment {
    FloatingActionButton fab, fab1, fab2, fab3;
    Animation fabopen, fabclose, rotateforward, rotatebackward;
    boolean isOpen = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistics, null);
        fab = view.findViewById(R.id.fabmain);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);
        fab3 = view.findViewById(R.id.fab3);
        fabopen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotatebackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        rotateforward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
                Toast.makeText(getActivity(), "first menu", Toast.LENGTH_SHORT).show();
            }
        });
        //   Toast.makeText(getActivity(), ""+getArguments().getString("team_name"), Toast.LENGTH_SHORT).show();
        return view;

    }

    private void animateFab() {
        if (isOpen) {
            fab.startAnimation(rotateforward);
            fab1.startAnimation(fabopen);
            fab2.startAnimation(fabopen);
            fab3.startAnimation(fabopen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isOpen = false;
        } else {
            //setVisible();
            fab.startAnimation(rotatebackward);
            fab1.startAnimation(fabclose);
            fab2.startAnimation(fabclose);
            fab3.startAnimation(fabclose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isOpen = true;

        }
    }

}
