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
    private Boolean selected;
    public slot(){
        aPlace = "";
        startTime = "";
        endTime = "";
        selected = false;
    }

    public slot(String a, String s, String d){
        aPlace = a; // the attraction
        startTime = s;
        endTime = d;
    }
    public String getaPlace(){
        return aPlace;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public Boolean getSelected() {return selected;}
    public void setaPlace (String p){ aPlace = p;}
    public void setStartTime (String s){ startTime = s;}
    public void setEndTime (String e){ endTime = e;}
    public void setSelected (Boolean b){
        selected = b;
    }

    @Override
    public String toString() {
        return this.aPlace + "\nStart: " + this.startTime  + "      End: "+ this.endTime;
        //return this.aPlace + "                       " + this.startTime  + " - "+ this.endTime;
       // return this.aPlace + "         [from: " + this.startTime  + " - to: "+ this.endTime+"]";
    }
}
