package com.example.mohankumardhakal.football_arena.Frames;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mefittech.mohankumardhakal.football_arena.R;

public class ScheduleFrame extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.round_of_16_fixture, null);
    }
}
