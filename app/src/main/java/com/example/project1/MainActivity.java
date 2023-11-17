package com.example.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String heartRate;
    public static String respiratoryRate;

    HeartRateMeasurement heartRateMeasurement = new HeartRateMeasurement();
    RespiratoryRateMeasurement respiratoryRateMeasurement = new RespiratoryRateMeasurement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getHeartRate(View view) throws IOException {
        TextView heartRateText = findViewById(R.id.heartRateText);
        List<Bitmap> frameList = new ArrayList<>();

        Toast.makeText(MainActivity.this, "Calculating Heart Beat...",
                Toast.LENGTH_LONG).show();

        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.heart_rate);
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(this, path);
        String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
        float vidDuration = Long.parseLong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))/1000;

        try {
            int videoDuration = Integer.parseInt(duration);
            int i=10;
            while (i<videoDuration) {
                Bitmap frameBitmap = metadataRetriever.getFrameAtIndex(i);
                frameList.add(frameBitmap);
                i += 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(MainActivity.this, "Nearly Done...",
                Toast.LENGTH_LONG).show();

        metadataRetriever.release();
        heartRate = heartRateMeasurement.measureHR(frameList, vidDuration);
        heartRateText.setText(heartRate + " beats per minute");
    }

    public void getRespiratoryRate(View view) throws IOException {
        TextView respiratoryText = findViewById(R.id.respiratoryRateText);

        Toast.makeText(MainActivity.this, "Calculating Respiratory Rate...",
                Toast.LENGTH_LONG).show();

        InputStreamReader inputStreamReader = new InputStreamReader(getResources().openRawResource(R.raw.respiratory_rate));
        CSVReader reader = new CSVReader(inputStreamReader);
        List<String[]> respRate = reader.readAll();
        List<String[]> listX = respRate.subList(1,1280);
        List<String[]> listY = respRate.subList(1281, 2560);
        List<String[]> listZ = respRate.subList(2561, respRate.size());

        respiratoryRate = respiratoryRateMeasurement.measureRR(listX, listY, listZ);
        respiratoryText.setText(respiratoryRate + " breaths per minute");

    }

    public void navigateToActivity2(View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void navigateToTrafficConditions(View view) {
        Intent intent = new Intent(this, TrafficConditions.class);
        startActivity(intent);
    }

    public List<String> getRates() {
        List<String> rates = new ArrayList<>();
        rates.add(heartRate);
        rates.add(respiratoryRate);

        return rates;
    }
}