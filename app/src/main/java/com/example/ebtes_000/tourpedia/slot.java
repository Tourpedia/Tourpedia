package com.example.ebtes_000.tourpedia;

/**
 * Created by ebtes_000 on 08/04/16.
 */
public class slot {
   /* private Place aPlace; // the attraction
    private String startTime; // maybe date
    private double aDuration;*/
    private String aPlace; // the attraction
    private String startTime; // maybe date
    private String endTime;
    public slot(){
        aPlace = "";
        startTime = "";
        endTime = "";
    }

    public slot(String a, String s, String d){
        aPlace = a; // the attraction
        startTime = s;
        endTime = d;
    }
}
