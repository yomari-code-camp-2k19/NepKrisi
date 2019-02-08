package com.example.mohankumardhakal.football_arena.Frames;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.ActivityClasses.GroupDetail;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TableFrame extends android.support.v4.app.Fragment {
    ArrayList<TeamInformation> list = new ArrayList<>();
    AQuery aQuery;
    int i;
    InnerClass innerClass;
    TeamInformation teamInformation;
    String fetchUrl;
    TextView groupA, groupB, groupC, groupD, groupE, groupF, groupG, groupH;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.table, null);
        fetchUrl = "http://api.football-api.com/2.0/standings/1005?Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
        aQuery = new AQuery(getActivity());
        groupA = view.findViewById(R.id.groupA);
        groupB = view.findViewById(R.id.groupB);
        groupC = view.findViewById(R.id.groupC);
        groupD = view.findViewById(R.id.groupD);
        groupE = view.findViewById(R.id.groupE);
        groupF = view.findViewById(R.id.groupF);
        groupG = view.findViewById(R.id.groupG);
        groupH = view.findViewById(R.id.groupH);
        groupA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group A");
            }
        });
        groupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group B");
            }
        });
        groupC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group C");
            }
        });
        groupD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group D");
            }
        });
        groupE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group E");
            }
        });
        groupF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group F");
            }
        });
        groupG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group G");
            }
        });
        groupH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData("Group H");
            }
        });
        return view;
    }

    private void fetchData(final String name) {

        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                for (i = 0; i < array.length(); i++) {
                    try {
                        teamInformation = new TeamInformation();
                        JSONObject object = array.getJSONObject(i);
                        teamInformation.comp_group = object.getString("comp_group");
                        if (teamInformation.comp_group.equals(name)) {
                            teamInformation.gd = object.getInt("gd");
                            teamInformation.points = object.getInt("points");
                            teamInformation.team_name = object.getString("team_name");
                            teamInformation.recent_form = object.getString("recent_form");
                            teamInformation.description = object.getString("description");
                            teamInformation.position = object.getInt("position");
                            list.add(teamInformation);
                            innerClass = new InnerClass(list);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                innerClass.giveItems();
            }
        });
    }

    public class InnerClass {
        ArrayList<TeamInformation> mylist;
        TeamInformation tf;
        ArrayList<String> team_names = new ArrayList<>();
        ArrayList<Integer> points = new ArrayList<>();
        ArrayList<Integer> goaldiff = new ArrayList<>();
        ArrayList<Integer> position = new ArrayList<>();
        public InnerClass(ArrayList<TeamInformation> list) {
            mylist = list;
        }
        public void giveItems() {

            for (int i = 0; i < mylist.size(); i++) {
                tf = mylist.get(i);
                team_names.add(tf.team_name);
                points.add(tf.points);
                goaldiff.add(tf.gd);
                position.add(tf.position);
            }
            intent = new Intent(getActivity(), GroupDetail.class);
            intent.putStringArrayListExtra("list", team_names);
            intent.putIntegerArrayListExtra("points", points);
            intent.putIntegerArrayListExtra("goaldiff", goaldiff);
            intent.putIntegerArrayListExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
    }
}