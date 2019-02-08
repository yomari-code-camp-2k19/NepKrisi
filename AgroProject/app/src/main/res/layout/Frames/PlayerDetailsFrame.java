package com.example.mohankumardhakal.football_arena.Frames;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.HelperPackage.MatchStatAdapter;
import com.example.mohankumardhakal.football_arena.HelperPackage.PlayerStatAdapter;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayerDetailsFrame extends Fragment {
    ListView listView;
    AQuery aQuery;
    TeamInformation playerStat;
    ArrayList<TeamInformation> list = new ArrayList<>();
    PlayerStatAdapter playerStatAdapter;
    Thread thread;
    Handler handler;
    ImageView imageView;
    Animation translate, alpha;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_detail, null);
        listView = view.findViewById(R.id.player_listview);
        aQuery = new AQuery(getActivity());
        int id = getArguments().getInt("match_id");
        final String fetchUrl = "http://api.football-api.com/2.0/commentaries/" + id + "?Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
        imageView=view.findViewById(R.id.image);
        imageView.setVisibility(View.VISIBLE);
        translate = AnimationUtils.loadAnimation(getActivity(), R.anim.translation);
        alpha = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        imageView.startAnimation(alpha);
        fetchPlayerStatsFromServer(fetchUrl);
        return view;
    }

    public void fetchPlayerStatsFromServer(String fetchUrl) {
        aQuery.ajax(fetchUrl, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {
                    try {
                        JSONObject name = object.getJSONObject("player_stats");
                        JSONObject localteam = name.getJSONObject("localteam");
                        JSONObject visitorteam = name.getJSONObject("visitorteam");
                        JSONArray localplayer = localteam.getJSONArray("player");
                        JSONArray visitorplayer = visitorteam.getJSONArray("player");
                        JSONObject matchStat = object.getJSONObject("match_stats");
                        JSONArray localteamMatchStat = matchStat.getJSONArray("localteam");
                        JSONArray visitorteamMatchStat = matchStat.getJSONArray("visitorteam");
                        for (int i = 0; i < localplayer.length(); i++) {
                            playerStat = new TeamInformation();
                            JSONObject object1 = localplayer.getJSONObject(i);
                            JSONObject object2 = visitorplayer.getJSONObject(i);
                            if (object1 != null) {
                                playerStat.playerName = object1.getString("name");
                                playerStat.position2 = object1.getString("pos");
                            }
                            if (object2 != null) {
                                playerStat.playerName1 = object2.getString("name");
                                playerStat.position1 = object2.getString("pos");
                            }
                            list.add(playerStat);
                        }
                        playerStatAdapter = new PlayerStatAdapter(getActivity(), list);
                        imageView.setVisibility(View.INVISIBLE);
                        listView.setAdapter(playerStatAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

    }

    public void datatransaction(String fetchUrl, final View view, AQuery aQuery, final Activity activity) {
        aQuery.ajax(fetchUrl, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                //creating list for storing data
                ArrayList<TeamInformation> list1 = new ArrayList<>();
                //initializing listView
                ListView listView = view.findViewById(R.id.listview);
                if (object != null) {
                    try {
                        TeamInformation playerStat = new TeamInformation();
                        JSONObject matchStat = object.getJSONObject("match_stats");
                        JSONArray localteamMatchStat = matchStat.getJSONArray("localteam");
                        JSONArray visitorteamMatchStat = matchStat.getJSONArray("visitorteam");
                        //has only one object so only one index that is 0
                        JSONObject object1 = localteamMatchStat.getJSONObject(0);
                        JSONObject object2 = visitorteamMatchStat.getJSONObject(0);

                        if (object1 != null) {
                            playerStat.possesiontime = object1.getString("possesiontime");
                            playerStat.saves = object1.getInt("saves");
                            playerStat.corners = object1.getInt("corners");
                            playerStat.shotsTotal = object1.getInt("shots_total");
                            playerStat.shotsOnGoal = object1.getInt("shots_ongoal");
                            playerStat.foulscommitted = object1.getInt("fouls");
                            playerStat.offsides = object1.getInt("offsides");
                            playerStat.yCards = object1.getInt("yellowcards");
                            playerStat.rCards = object1.getInt("redcards");

                        } else {
                        }
                        if (object2 != null) {
                            playerStat.possesiontime1 = object2.getString("possesiontime");
                            playerStat.saves1 = object2.getInt("saves");
                            playerStat.corners1 = object2.getInt("corners");
                            playerStat.shotsTotal1 = object2.getInt("shots_total");
                            playerStat.shotsOnGoal1 = object2.getInt("shots_ongoal");
                            playerStat.foulscommitted1 = object2.getInt("fouls");
                            playerStat.offsides1 = object2.getInt("offsides");
                            playerStat.yCards1 = object2.getInt("yellowcards");
                            playerStat.rCards1 = object2.getInt("redcards");
                        }

                        list1.add(playerStat);
                        MatchStatAdapter matchStatAdapter = new MatchStatAdapter(activity, list1);
                        listView.setAdapter(matchStatAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "null main obj", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}