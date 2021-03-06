package com.example.mohankumardhakal.football_arena.Frames;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.HelperPackage.RecyclerAdapterForLiveMatches;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiveMatchesFrame extends Fragment {
    String fetchUrl = null;
    ArrayList<TeamInformation> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    TeamInformation livescore;
    private AQuery aQuery;
    TextView error;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_score, null);
        fetchUrl = "http://api.football-api.com/2.0/matches?comp_id=1005%2C1399&Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
        aQuery = new AQuery(getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);
        //ensures the size is always fixed
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        error = view.findViewById(R.id.error);
        fetchRequiredData();
        return view;
    }

    public void fetchRequiredData() {
        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            JSONObject object = array.getJSONObject(i);
                            livescore = new TeamInformation();
                            livescore.homeTeam = object.getString("localteam_name");
                            livescore.awayTeam = object.getString("visitorteam_name");
                            livescore.homeSideScore = object.getInt("localteam_score");
                            livescore.awaySideScore = object.getInt("visitorteam_score");
                            livescore.match_date = object.getString("formatted_date");
                            livescore.match_id = object.getInt("id");
                            setHomeSideImage(livescore.homeTeam);
                            setAwaySideImage(livescore.awayTeam);
                            list.add(livescore);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter = new RecyclerAdapterForLiveMatches(getActivity(), list);
                        mAdapter = new RecyclerAdapterForLiveMatches(getActivity(), list);
                        recyclerView.setAdapter(mAdapter);
                    }
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }

        });

    }

    public void setHomeSideImage(String name) {
        switch (name) {
            case "Hoffenheim":
                livescore.hometeamlogo = (R.mipmap.hoffenheim);
                break;
            case "Manchester City":
                livescore.hometeamlogo = (R.drawable.ic_manchester_city);
                break;
            case "Juventus":
                livescore.hometeamlogo = (R.drawable.ic_juventus);
                break;
            case "Young Boys":
                livescore.hometeamlogo = (R.mipmap.youngboys);

                break;
            case "Bayern München":
                livescore.hometeamlogo = R.drawable.ic_bayern_munchen;
                break;
            case "Ajax":
                livescore.hometeamlogo = R.mipmap.ajax;
                break;
            case "AEK Athens":
                livescore.hometeamlogo = R.mipmap.aek;
                break;
            case "Benfica":
                livescore.hometeamlogo = R.mipmap.benfica;
                break;
            case "Olympique Lyonnais":
                livescore.hometeamlogo = R.mipmap.lyon;
                break;
            case "Shakhtar Donetsk":
                livescore.hometeamlogo = R.mipmap.shakhtar;
                break;
            case "CSKA Moskva":
                livescore.hometeamlogo = R.mipmap.cska;
                break;
            case "Real Madrid":
                livescore.hometeamlogo = R.drawable.ic_real_madrid;
                break;
            case "Roma":
                livescore.hometeamlogo = R.drawable.ic_roman;
                break;
            case "Viktoria Plzeň":
                livescore.hometeamlogo = R.mipmap.plzen;
                break;
            case "Manchester United":
                livescore.hometeamlogo = R.drawable.ic_manchester_united;
                break;
            case "Valencia":
                livescore.hometeamlogo = R.drawable.ic_valencia;
                break;
            case "PSG":
                livescore.hometeamlogo = R.drawable.ic_paris_saint_germain;
                break;
            case "Crvena Zvezda":
                livescore.hometeamlogo = R.mipmap.brand;
                break;
            case "Lokomotiv Moskva":
                livescore.hometeamlogo = R.mipmap.cska;
                break;
            case "Schalke 04":
                livescore.hometeamlogo = R.mipmap.shakhtar;
                break;
            case "Atlético Madrid":
                livescore.hometeamlogo = R.drawable.ic_atletico_de_madrid;
                break;
            case "Borussia Dortmund":
                livescore.hometeamlogo = R.drawable.ic_borussia_dortmund;
                break;
            case "Club Brugge":
                livescore.hometeamlogo = R.mipmap.brugeekv;
                break;
            case "Monaco":
                livescore.hometeamlogo = R.drawable.ic_monaco;
                break;
            case "Tottenham Hotspur":
                livescore.hometeamlogo = R.drawable.ic_tottenham_hotspur;
                break;
            case "Barcelona":
                livescore.hometeamlogo = R.drawable.ic_barcelona;
                break;
            case "PSV":
                livescore.hometeamlogo = R.mipmap.psv;
                break;
            case "Internazionale":
                livescore.hometeamlogo = R.drawable.ic_internazionale_milano;
                break;
            case "Napoli":
                livescore.hometeamlogo = R.drawable.ic_napoli;
                break;
            case "Liverpool":
                livescore.hometeamlogo = R.drawable.ic_liverpool;
                break;
            case "Porto":
                livescore.hometeamlogo = R.mipmap.fcportopng;
                break;
            case "Galatasaray":
                livescore.hometeamlogo = R.mipmap.galatasaray;
                break;

        }
    }

    public void setAwaySideImage(String name) {
        switch (name) {
            case "Hoffenheim":
                livescore.awayteamlogo = (R.mipmap.hoffenheim);
                break;
            case "Manchester City":
                livescore.awayteamlogo = (R.drawable.ic_manchester_city);
                break;
            case "Juventus":
                livescore.awayteamlogo = (R.drawable.ic_juventus);
                break;
            case "Young Boys":
                livescore.awayteamlogo = (R.mipmap.youngboys);

                break;
            case "Bayern München":
                livescore.awayteamlogo = R.drawable.ic_bayern_munchen;
                break;
            case "Ajax":
                livescore.awayteamlogo = R.mipmap.ajax;
                break;
            case "AEK Athens":
                livescore.awayteamlogo = R.mipmap.aek;
                break;
            case "Benfica":
                livescore.awayteamlogo = R.mipmap.benfica;
                break;
            case "Olympique Lyonnais":
                livescore.awayteamlogo = R.mipmap.lyon;
                break;
            case "Shakhtar Donetsk":
                livescore.awayteamlogo = R.mipmap.shakhtar;
                break;
            case "CSKA Moskva":
                livescore.awayteamlogo = R.mipmap.cska;
                break;
            case "Real Madrid":
                livescore.awayteamlogo = R.drawable.ic_real_madrid;
                break;
            case "Roma":
                livescore.awayteamlogo = R.drawable.ic_roman;
                break;
            case "Viktoria Plzeň":
                livescore.awayteamlogo = R.mipmap.plzen;
                break;
            case "Manchester United":
                livescore.awayteamlogo = R.drawable.ic_manchester_united;
                break;
            case "Valencia":
                livescore.awayteamlogo = R.drawable.ic_valencia;
                break;
            case "PSG":
                livescore.awayteamlogo = R.drawable.ic_paris_saint_germain;
                break;
            case "Crvena Zvezda":
                livescore.awayteamlogo = R.mipmap.brand;
                break;
            case "Lokomotiv Moskva":
                livescore.awayteamlogo = R.mipmap.cska;
                break;
            case "Schalke 04":
                livescore.awayteamlogo = R.mipmap.shakhtar;
                break;
            case "Atlético Madrid":
                livescore.awayteamlogo = R.drawable.ic_atletico_de_madrid;
                break;
            case "Borussia Dortmund":
                livescore.awayteamlogo = R.drawable.ic_borussia_dortmund;
                break;
            case "Club Brugge":
                livescore.awayteamlogo = R.mipmap.brugeekv;
                break;
            case "Monaco":
                livescore.awayteamlogo = R.drawable.ic_monaco;
                break;
            case "Tottenham Hotspur":
                livescore.awayteamlogo = R.drawable.ic_tottenham_hotspur;
                break;
            case "Barcelona":
                livescore.awayteamlogo = R.drawable.ic_barcelona;
                break;
            case "PSV":
                livescore.awayteamlogo = R.mipmap.psv;
                break;
            case "Internazionale":
                livescore.awayteamlogo = R.drawable.ic_internazionale_milano;
                break;
            case "Napoli":
                livescore.awayteamlogo = R.drawable.ic_napoli;
                break;
            case "Liverpool":
                livescore.awayteamlogo = R.drawable.ic_liverpool;
                break;
            case "Porto":
                livescore.awayteamlogo = R.mipmap.fcportopng;
                break;
            case "Galatasaray":
                livescore.awayteamlogo = R.mipmap.galatasaray;
                break;

        }
    }

}
