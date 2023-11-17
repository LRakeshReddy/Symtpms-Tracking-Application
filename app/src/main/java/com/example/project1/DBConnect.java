package com.example.project1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

public class DBConnect extends SQLiteOpenHelper {

    private static String DB_Name = "SymptomsDB";
    private static String DB_Table = "SymptomsTable";
    private static int DB_Version = 1;

    public DBConnect(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_Table + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Created_On TEXT, "
                + "Heart_Rate TEXT, "
                + "Respiratory_Rate TEXT, "
                + "Nausea TEXT, "
                + "Headache TEXT, "
                + "Diarrhea TEXT, "
                + "Soar_Throat TEXT, "
                + "Fever TEXT, "
                + "Muscle_Ache TEXT, "
                + "Loss_of_Smell_and_Taste TEXT, "
                + "Cough TEXT, "
                + "Shortness_of_Breath TEXT, "
                + "Feeling_Tired  TEXT)";

        db.execSQL(query);
    }

    public void uploadSymptoms(HashMap<String, String> symptomsData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        values.put("Created_On", String.valueOf(timestamp));
        values.put("Heart_Rate", symptomsData.get("Heart_Rate"));
        values.put("Respiratory_Rate", symptomsData.get("Respiratory_Rate"));
        values.put("Nausea", symptomsData.get("Nausea"));
        values.put("Headache", symptomsData.get("Headache"));
        values.put("Diarrhea", symptomsData.get("Diarrhea"));
        values.put("Soar_Throat", symptomsData.get("Soar_Throat"));
        values.put("Fever", symptomsData.get("Fever"));
        values.put("Muscle_Ache", symptomsData.get("Muscle_Ache"));
        values.put("Loss_of_Smell_and_Taste", symptomsData.get("Loss_of_Smell_and_Taste"));
        values.put("Cough", symptomsData.get("Cough"));
        values.put("Shortness_of_Breath", symptomsData.get("Shortness_of_Breath"));
        values.put("Feeling_Tired", symptomsData.get("Feeling_Tired"));

        db.insert(DB_Table, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        onCreate(db);
    }
}
