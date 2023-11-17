package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class Activity2 extends AppCompatActivity {

    MainActivity mainActivity = new MainActivity();
    DBConnect dbConnect;
    HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Spinner symptomsSpinner = findViewById(R.id.symptomsDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.symptoms, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        symptomsSpinner.setAdapter(adapter);

        List<String> rates = mainActivity.getRates();
        String heartRate = rates.get(0);
        String respiratoryRate = rates.get(1);

        String[] array = getResources().getStringArray(R.array.symptoms);
        for (String s : array) {
            map.put(s.replace(" ", "_"), "0");
        }

        map.put("Heart_Rate", heartRate);
        map.put("Respiratory_Rate", respiratoryRate);

        RatingBar symptomsRatingBar = findViewById(R.id.symptomsRatingBar);
        symptomsRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                String ratingString = String.valueOf(symptomsRatingBar.getRating());
                String symptom = symptomsSpinner.getSelectedItem().toString();
                map.put(symptom.replace(" ", "_"), ratingString);
            }
        });
    }

    public void uploadSymptoms(View view) {
        dbConnect = new DBConnect(Activity2.this);
        dbConnect.uploadSymptoms(map);
    }
}