package com.example.mohankumardhakal.serverdata;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Server_Activity extends AppCompatActivity {

    String url = "https://userurstrength.000webhostapp.com/dbinsert.php";
    EditText cname, pname, gstarted, games, shotongoal, shots, ycard, rcard, mplayed, gscored, assist;
    Button submit, refill;
    String clname, plname, gmstarted, gamesplayed, shotsongoal, tshots,
            minplayed, assists, goalscored, redcard, yellowcard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        cname = (EditText) findViewById(R.id.Ecname);
        pname = findViewById(R.id.Epname);
        gstarted = findViewById(R.id.Egamestarted);
        games = findViewById(R.id.Egames);
        shotongoal = findViewById(R.id.EShotsOnGoal);
        shots = findViewById(R.id.EShots);
        mplayed = findViewById(R.id.Emplayed);
        rcard = findViewById(R.id.ERcard);
        ycard = findViewById(R.id.Eycard);
        assist = findViewById(R.id.Eassists);
        gscored = findViewById(R.id.Egoal);
        submit = findViewById(R.id.submit);
        refill = findViewById(R.id.refill);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTextData();
                sendDataToServer(clname, plname, gmstarted, shotsongoal, tshots, assists, goalscored, redcard, yellowcard, yellowcard, gamesplayed);
            }
        });

    }


    public void sendDataToServer(final String clname, final String plname, final String gmstarted, final String shotsongoal, final String tshots,
                                 final String assists, final String goalscored, final String rcard, final String ycard, final String minplayed, final String gamesPlayed) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Server_Activity.this, "Server Message on Response"+response, Toast.LENGTH_SHORT).show();
                                Log.i("response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Server_Activity.this, "Error....", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("PlayerName", plname);
                params.put("ClubName", clname);
                params.put("GoalScored",goalscored);
                params.put("GamesPlayed", gamesPlayed);
                params.put("MinutesPlayed", minplayed);
                params.put("RedCard", rcard);
                params.put("Assists", assists);
                params.put("YellowCard",ycard);
                params.put("ShotsOnGoal",shotsongoal);
                params.put("TotalShots",tshots);
                params.put("GameStarted", gmstarted);
                return params;
            }


        };
        MySingleton.getMinstance(Server_Activity.this).addToRequestQueue(stringRequest);


    }

    public void getEditTextData() {
        clname = cname.getText().toString();
        plname = pname.getText().toString();
        gmstarted = gstarted.getText().toString();
        gamesplayed = games.getText().toString();
        shotsongoal = shotongoal.getText().toString();
        tshots = shots.getText().toString();
        minplayed = mplayed.getText().toString();
        yellowcard = ycard.getText().toString();
        redcard = rcard.getText().toString();
        goalscored = gscored.getText().toString();
        assists = assist.getText().toString();
    }

}

