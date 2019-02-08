package com.example.mohankumardhakal.football_arena.Frames;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.mohankumardhakal.football_arena.ActivityClasses.FollowedTeamAndPlayerDetailActivity;
import com.example.mohankumardhakal.football_arena.HelperPackage.DbForChoosenTeam;
import com.example.mohankumardhakal.football_arena.HelperPackage.DbHelper;
import com.example.mohankumardhakal.football_arena.HelperPackage.TeamInformation;
import com.mefittech.mohankumardhakal.football_arena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class FollowPlayerOrTeam extends android.support.v4.app.Fragment {
    TeamInformation teamInformation;
    AQuery aQuery;
    ArrayList<TeamInformation> list;
    String fetchUrl = "http://api.football-api.com/2.0/standings/1005?Authorization=565ec012251f932ea4000001fa542ae9d994470e73fdb314a8a56d76";
    DbHelper dbHelper;
    LinearLayout container1;
    ListView myListView;
    MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.follow_player_team, null);
        aQuery = new AQuery(getContext());
        dbHelper = new DbHelper(getContext());
        container1 = view.findViewById(R.id.container);
        list = new ArrayList<>();
        myListView = view.findViewById(R.id.myListView);
        if (isNetworkAvailable()) {
            setTeamtoFollow();
        }
//        populateData();
        ArrayList<TeamInformation> list1 = dbHelper.getTeamInfo();
        myAdapter = new MyAdapter(getActivity(), list1);
        myListView.setAdapter(myAdapter);
        return view;
    }

    //checking network connection
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public class MyAdapter extends ArrayAdapter<TeamInformation> {
        Context context;
        ArrayList<TeamInformation> list;

        public MyAdapter(@NonNull Context context, ArrayList<TeamInformation> list) {
            super(context, 0, list);
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;
            if (view == null) {
                 view = LayoutInflater.from(context).inflate(R.layout.player_list_of_your_team, null);
            }
            DbForChoosenTeam mydb = new DbForChoosenTeam(context);
            final TeamInformation tf = getItem(position);
            tf.name = view.findViewById(R.id.playername);
            tf.logo = view.findViewById(R.id.logo);
            tf.follow = view.findViewById(R.id.followTeam);
            //setting content using viewholder for every team
            tf.name.setText(tf.team_name);
            tf.logo.setImageResource(tf.teamLogo);
            if (mydb.checkId(tf.team_id)) {
                tf.follow.setImageResource(R.drawable.ic_follow);
                tf.isSelected = true;
            } else {
                tf.follow.setImageResource(R.drawable.add);
            }

            //onClick listener for following team
            (tf.follow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamInformation teamInformation1 = (TeamInformation) tf.follow.getTag();
                    DbForChoosenTeam dbHelper = new DbForChoosenTeam(context);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", teamInformation1.team_id);
                    contentValues.put("name", teamInformation1.team_name);
                    // insert the team into the table
                    if (tf.isSelected == false) {
                        teamInformation1.selectedItem();
                        dbHelper.insertData(contentValues);
                        tf.isSelected = !tf.isSelected;

                    } else {
                        //delete the unfollowed team from the database
                        teamInformation1.setdeSelected();
                        if (dbHelper.isDataExists()) {
                            dbHelper.deleteTeamInfo(teamInformation1.team_id);
                        }
                        tf.isSelected = !tf.isSelected;
                    }
                    //retreiving the data from database to check validity
                    if (dbHelper.isDataExists()) {
                        ArrayList<TeamInformation> list2 = dbHelper.getTeamInfo();
                        for (TeamInformation tif : list2) {
                        }
                    } else {
                    }
                }
            });
            view.setTag(tf);
            tf.follow.setTag(getItem(position));
            //let the user click on the name if the team is followed
            tf.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tf.isSelected == true) {
                        Intent intent = new Intent(context, FollowedTeamAndPlayerDetailActivity.class);
                        intent.putExtra("id", tf.team_id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Click on Plus to follow Team", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }

    //set team name for follow
    public void setTeamtoFollow() {
        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        teamInformation = new TeamInformation();
                        teamInformation.team_name = object.getString("team_name");
                        teamInformation.country = object.getString("country");
                        teamInformation.team_id = object.getInt("team_id");
                        setImage(teamInformation.team_id);
                        list.add(teamInformation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (dbHelper.isDataExists()) {

                } else {
                    for (int i = 0; i < list.size(); i++) {
                        TeamInformation teamInfo = list.get(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", teamInfo.team_id);
                        contentValues.put("name", teamInfo.team_name);
                        dbHelper.insertData(contentValues);
                    }
                }
            }
        });

    }

    //    }

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

