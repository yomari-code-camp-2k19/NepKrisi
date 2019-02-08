package com.example.mohankumardhakal.football_arena.Frames;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.HelperPackage.AdapterClass;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamFrame extends Fragment {
    ArrayList<TeamInformation> list = new ArrayList<>();
    AQuery aQuery;
    ListView listView;
    int i;
    AdapterClass myAdapter;
    TeamInformation teamInformation;
    String fetchUrl = "http://api.football-api.com/2.0/standings/1005?Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_frame, null);
        aQuery = new AQuery(getActivity());
        listView = view.findViewById(R.id.listview);
        fetchData();
        return view;
    }

    private void fetchData() {
        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                for (i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        teamInformation = new TeamInformation();
                        teamInformation.comp_id = object.getInt("comp_id");
                        teamInformation.round = object.getInt("round");
                        teamInformation.stage_id = object.getInt("stage_id");
                        teamInformation.team_id = object.getInt("team_id");
                        setImage(teamInformation.team_id);
                        teamInformation.overall_gp = object.getInt("overall_gp");
                        teamInformation.overall_w = object.getInt("overall_w");
                        teamInformation.overall_d = object.getInt("overall_d");
                        teamInformation.overall_l = object.getInt("overall_l");
                        teamInformation.overall_gs = object.getInt("overall_gs");
                        teamInformation.overall_ga = object.getInt("overall_ga");
                        teamInformation.home_gp = object.getInt("home_gp");
                        teamInformation.home_w = object.getInt("home_w");
                        teamInformation.home_d = object.getInt("home_d");
                        teamInformation.home_l = object.getInt("home_l");
                        teamInformation.home_gs = object.getInt("home_gs");
                        teamInformation.home_ga = object.getInt("home_ga");
                        teamInformation.away_w = object.getInt("away_w");
                        teamInformation.away_d = object.getInt("away_d");
                        teamInformation.away_l = object.getInt("away_l");
                        teamInformation.away_gs = object.getInt("away_gs");
                        teamInformation.away_ga = object.getInt("away_ga");
                        teamInformation.gd = object.getInt("gd");
                        teamInformation.points = object.getInt("points");
                        teamInformation.season = object.getString("season");
                        teamInformation.comp_group = object.getString("comp_group");
                        teamInformation.team_name = object.getString("team_name");
                        teamInformation.country = object.getString("country");
                        teamInformation.recent_form = object.getString("recent_form");
                        teamInformation.status = object.getString("status");
                        teamInformation.description = object.getString("description");
                        teamInformation.away_gp = object.getInt("away_gp");
                        teamInformation.position = object.getInt("position");
                        list.add(teamInformation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                myAdapter = new AdapterClass(getContext(), list, 0);
                listView.setAdapter(myAdapter);
            }
        });
    }
    //setting images for each team
    public void setImage(int id) {
        switch (id) {
            case 10442:
                teamInformation.teamLogo = (R.mipmap.hoffenheim);
                break;
            case 9259:
                teamInformation.teamLogo = (R.drawable.ic_manchester_city);
                break;
            case 11922:
                teamInformation.teamLogo = (R.drawable.ic_juventus);
                break;
            case 16793:
                teamInformation.teamLogo = (R.mipmap.youngboys);

                break;
            case 10285:
                teamInformation.teamLogo = (R.drawable.ic_bayern_munchen);
                break;
            case 13183:
                teamInformation.teamLogo = (R.mipmap.ajax);
                break;
            case 10747:
                teamInformation.teamLogo = (R.mipmap.aek);
                break;
            case 14267:
                teamInformation.teamLogo = (R.mipmap.benfica);
                break;
            case 10040:
                teamInformation.teamLogo = (R.mipmap.lyon);
                break;
            case 17205:
                teamInformation.teamLogo = (R.mipmap.shakhtar);
                break;
            case 14796:
                teamInformation.teamLogo = (R.mipmap.cska);
                break;
            case 16110:
                teamInformation.teamLogo = (R.drawable.ic_real_madrid);
                break;
            case 11998:
                teamInformation.teamLogo = (R.drawable.ic_roman);
                break;
            case 8649:
                teamInformation.teamLogo = (R.mipmap.plzen);
                break;
            case 9260:
                teamInformation.teamLogo = (R.drawable.ic_manchester_united);
                break;
            case 16261:
                teamInformation.teamLogo = (R.drawable.ic_valencia);
                break;
            case 10061:
                teamInformation.teamLogo = (R.drawable.ic_paris_saint_germain);
                break;
            case 15173:
                teamInformation.teamLogo = (R.mipmap.brand);
                break;
            case 14871:
                teamInformation.teamLogo = (R.mipmap.cska);
                break;
            case 10576:
                teamInformation.teamLogo = (R.mipmap.shakhtar);
                break;
            case 15692:
                teamInformation.teamLogo = (R.drawable.ic_atletico_de_madrid);
                break;
            case 10303:
                teamInformation.teamLogo = (R.drawable.ic_borussia_dortmund);
                break;
            case 6752:
                teamInformation.teamLogo = (R.mipmap.brugeekv);
                break;
            case 10020:
                teamInformation.teamLogo = (R.drawable.ic_monaco);
                break;
            case 9406:
                teamInformation.teamLogo = (R.drawable.ic_tottenham_hotspur);
                break;
            case 15702:
                teamInformation.teamLogo = (R.drawable.ic_barcelona);
                break;
            case 13364:
                teamInformation.teamLogo = (R.mipmap.psv);
                break;
            case 11917:
                teamInformation.teamLogo = (R.drawable.ic_internazionale_milano);
                break;
            case 11947:
                teamInformation.teamLogo = (R.drawable.ic_napoli);
                break;
            case 9249:
                teamInformation.teamLogo = (R.drawable.ic_liverpool);
                break;
            case 14408:
                teamInformation.teamLogo = (R.mipmap.fcportopng);
                break;
            case 17029:
                teamInformation.teamLogo = (R.mipmap.galatasaray);
                break;
        }
    }
}
