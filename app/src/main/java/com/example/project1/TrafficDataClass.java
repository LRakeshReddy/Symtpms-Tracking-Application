package com.example.project1;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TrafficDataClass extends AsyncTask<String, Void, String>{
    public String jsonData;

    protected String doInBackground(String... trafficUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(trafficUrl[0]).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            jsonData = response.toString();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }
}
