package com.example.mohankumardhakal.football_arena.Frames;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.mefittech.mohankumardhakal.football_arena.R;

public class MatchStatFrame extends Fragment {
    AQuery aQuery;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_stat_fragment, null);
        int id = getArguments().getInt("match_id");
        aQuery= new AQuery(getActivity());
        String url = "http://api.football-api.com/2.0/commentaries/" + id + "?Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
        PlayerDetailsFrame playerDetailsFrame = new PlayerDetailsFrame();
        Activity activity=getActivity();
        playerDetailsFrame.datatransaction(url, view,aQuery,activity);
        return view;
    }
}
