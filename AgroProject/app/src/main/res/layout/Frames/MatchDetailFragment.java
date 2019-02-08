package com.example.mohankumardhakal.football_arena.Frames;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.HelperPackage.MatchDetailListViewAdapter;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchDetailFragment extends Fragment {
    ListView match_listview;
    MatchDetailListViewAdapter mAdapter;
    AQuery aQuery;
    TeamInformation matchEvent;
    int id;
    ImageView imageView;
    Animation translate, alpha;
    String fetchUrl = "http://api.football-api.com/2.0/matches?comp_id=1005&from_date=18.9.2018&to_date=07.5.2019&Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
    //HelperForDataTransfer matchEvent;
    ArrayList<TeamInformation> list = new ArrayList<>();
    public int[] eventImage = {R.drawable.ic_amonestation, R.drawable.ic_red_card,
            R.drawable.ic_football_ball,
            R.drawable.ic_up_arrow, R.drawable.ic_downarrow_};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_detail, null);
        aQuery = new AQuery(getActivity());
        match_listview = view.findViewById(R.id.match_listview);
        id = getArguments().getInt("match_id");
        imageView = view.findViewById(R.id.image);
        alpha= AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        imageView.startAnimation(alpha);
        fetchDataFromserver();
        return view;
    }

    void fetchDataFromserver() {
        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);

                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        int match_id = object.getInt("id");
                        if (match_id == id) {
                            JSONArray eventArray = object.getJSONArray("events");
                            for (int j = 0; j < eventArray.length(); j++) {
                                matchEvent = new TeamInformation();
                                JSONObject object1 = eventArray.getJSONObject(j);
                                String type = matchEvent.type = object1.getString("type");
                                String team = matchEvent.team = object1.getString("team");
                                if (team.equals("localteam")) {
                                    matchEvent.homeEventplayer = object1.getString("player");
                                    matchEvent.homeminute = object1.getInt("minute");
                                    if (type.equals("yellowcard")) {
                                        matchEvent.homeCard = eventImage[0];

                                    } else if (type.equals("redcard")) {
                                        matchEvent.homeCard = eventImage[1];
                                    } else if (type.equals("goal")) {
                                        matchEvent.homeCard = eventImage[2];
                                    }
                                }
                                if (team.equals("visitorteam")) {
                                    matchEvent.awayeventPlayer = object1.getString("player");
                                    matchEvent.awayminute = object1.getInt("minute");
                                    if (type.equals("yellowcard")) {
                                        matchEvent.awayCard = eventImage[0];

                                    } else if (type.equals("redcard")) {
                                        matchEvent.awayCard = eventImage[1];
                                    } else if (type.equals("goal")) {
                                        matchEvent.awayCard = eventImage[2];
                                    }
                                }
                                list.add(matchEvent);
                            }
                            mAdapter = new MatchDetailListViewAdapter(getActivity(), list);
                            imageView.setVisibility(View.INVISIBLE);
                            match_listview.setAdapter(mAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}
