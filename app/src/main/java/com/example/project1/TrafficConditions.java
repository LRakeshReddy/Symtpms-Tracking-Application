package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

public class TrafficConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_conditions);
    }

    public void getLocationsData(View view) {
        try {
            EditText startLoc = findViewById(R.id.startDestination);
            String startLocation = startLoc.getText().toString();
            EditText endLoc = findViewById(R.id.endDestination);
            String endLocation = endLoc.getText().toString();

            String origin = URLEncoder.encode(startLocation, "UTF-8");
            String destination = URLEncoder.encode(endLocation, "UTF-8");

            getTrafficData(origin, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getTrafficData(String startAddress, String endAddress) {
        String apiKey = "AIzaSyD7lw_JPcgzFTPQa0WdyeUOl1W2O4dY_IU";

        try {
            String trafficUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + startAddress + "&destinations=" + endAddress + "&key=" + apiKey + "&departure_time=now";
            TrafficDataClass trafficDataClass = new TrafficDataClass();
            String jsonData = trafficDataClass.execute(trafficUrl).get();

            JSONObject matrix = new JSONObject(jsonData);
            JSONArray rows = matrix.getJSONArray("rows");
            JSONObject rowsData = rows.getJSONObject(0);

            JSONArray elements = rowsData.getJSONArray("elements");
            JSONObject elementsData = elements.getJSONObject(0);

            JSONObject directions = elementsData.getJSONObject("distance");
            JSONObject duration = elementsData.getJSONObject("duration");
            JSONObject tDuration = elementsData.getJSONObject("duration_in_traffic");

            double distance = Double.parseDouble(directions.getString("value"));
            double avgDuration = Double.parseDouble(duration.getString("value"));
            double trafficDuration = Double.parseDouble(tDuration.getString("value"));

            distance = distance/1000;
            avgDuration = avgDuration/3600;
            trafficDuration = trafficDuration/3600;

            double avgVelocity = distance/avgDuration;
            double currVelocity = distance/trafficDuration;

            System.out.print(avgVelocity);
            System.out.println(currVelocity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}