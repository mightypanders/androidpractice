package com.panders.statusrequester;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public TextView txtAerie;
    public TextView txtGen;
    public TextView txtCell;
    public TextView txtLastChange;
    public TextView txtLastReq;
    public String url = "http://status.chaospott.de/status.json";
    public RequestQueue req;
    public jsonHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGen = (TextView) findViewById(R.id.txtGeneral);
        txtAerie = (TextView) findViewById(R.id.txtAerie);
        txtCell = (TextView) findViewById(R.id.txtCellar);
        txtLastChange = (TextView) findViewById(R.id.lastchange);
        req = Volley.newRequestQueue(this);
    }

    public void request(View view) {
        JsonObjectRequest jsoReq = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseJson(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
        req.add((jsoReq));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseJson(JSONObject response) throws JSONException {
        if (response.getJSONObject("state").getBoolean("open"))
            txtGen.setText(R.string.open);
        else
            txtGen.setText(R.string.closed);
        txtLastChange.setText("Ich hasse Date...");
        JSONArray arr = response.getJSONObject("sensors").getJSONArray("door_locked");
        for (int i = 0; i <= arr.length(); i++) {
            JSONObject door = (JSONObject) arr.get(i);
            if (door.getString("location").equals("aerie")) {
                if (door.getBoolean("value")) {
                    txtAerie.setText(R.string.closed);
                } else {
                    txtAerie.setText(R.string.open);
                }
            }else if (door.getString("location").equals("cellar")) {
                if(door.getBoolean("value"))
                    txtCell.setText(R.string.closed);
                else
                    txtCell.setText(R.string.open);
            }
        }

    }
}


