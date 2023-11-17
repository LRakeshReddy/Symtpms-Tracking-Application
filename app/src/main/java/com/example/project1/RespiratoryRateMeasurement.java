package com.example.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RespiratoryRateMeasurement {

    public String measureRR(List<String[]> X, List<String[]> Y, List<String[]> Z) {
        float currentValue;
        float previousValue = (float) 10.0;
        int k=0;
        int rr=0;
        try {
            for (int i=1; i<1279; i++) {
                String x = X.get(i)[0];
                String y = Y.get(i)[0];
                String z = Z.get(i)[0];
                currentValue = (float) Math.sqrt(Math.pow(Double.parseDouble(x) , 2.0)
                        + Math.pow(Double.parseDouble(y), 2.0)
                        + Math.pow(Double.parseDouble(z), 2.0));

                if(Math.abs(previousValue - currentValue) > 0.123) {
                    k++;
                }
                previousValue = currentValue;
            }
            rr = (int) ((float) k*(60.0/45.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(rr);
    }
}
