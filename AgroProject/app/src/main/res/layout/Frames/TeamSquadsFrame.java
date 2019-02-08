package com.example.mohankumardhakal.football_arena.Frames;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohankumardhakal.football_arena.ActivityClasses.TableSelctionContentActivity;
import com.example.mohankumardhakal.football_arena.PredictionActivitiesAndFrames.TeamListActivity;
import com.mefittech.mohankumardhakal.football_arena.R;

public class TeamSquadsFrame extends Fragment {
    TextView groupStage, roundOf16, quarterFinal, semiFinal, Final, predictions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_selection_layout, null);
        groupStage = view.findViewById(R.id.groupStage);
        roundOf16 = view.findViewById(R.id.roundOf16);
        quarterFinal = view.findViewById(R.id.quarterFinal);
        semiFinal = view.findViewById(R.id.semiFinal);
        Final = view.findViewById(R.id.Final);
        predictions = view.findViewById(R.id.predictions);
        groupStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TableSelctionContentActivity.class);
                intent.putExtra("value", "groupStage");
                startActivity(intent);
            }
        });
        roundOf16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new com.example.mohankumardhakal.football_arena.Frames.ScheduleFrame());
            }
        });
        semiFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not available Yet", Toast.LENGTH_SHORT).show();
            }
        });
        Final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not available Yet", Toast.LENGTH_SHORT).show();
            }
        });
        quarterFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not available Yet", Toast.LENGTH_SHORT).show();
            }
        });
        predictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TeamListActivity.class));
            }
        });
        return view;
    }

    //loads fragment from bottom navigation bar
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.firstfragment, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}
