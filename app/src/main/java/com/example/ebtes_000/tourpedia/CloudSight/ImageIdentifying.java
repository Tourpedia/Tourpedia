package com.example.ebtes_000.tourpedia.CloudSight;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.ebtes_000.tourpedia.GlassActivity;
import com.example.ebtes_000.tourpedia.VariablesAndConstants;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

import com.example.ebtes_000.tourpedia.imgDescription;

/**
 * Created by DELL on 07/03/16.
 */


public class ImageIdentifying extends AsyncTask<String,Integer,String> {

    private static final String API_KEY = "ZbSTE2YuIA-jJzfqfPe9EQ";
    private static final String GOOGLE_API_KEY="AIzaSyAuZQkjoca-3CzwUTG3Kb4PMi2xPtQKyEg";
    private static final String CUSTOM_SEARCH_ENGINE_ID ="011298360869446109394:8dheuhdm_ja";
    private static final HttpRequestInitializer REQUEST_INITIALIZER=null;

    private static final int IDENTIFYING_START=1;
    private static final int SEARCHING_START=2;


    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    String infoUrl ;
    String s , textDescription,glassT;
    TextView message;
    TextView t;
    ViewFlipper flipper;


    @Override

    protected void onPreExecute(){


        message=imgDescription.message;
        t=imgDescription.description;
        flipper=imgDescription.viewFlipper;
        super.onPreExecute();
    }

    //@Override
    protected void onProgressUpdate(Integer... progress) {

        switch (progress[0]){

            case 1:
               message.setText("Now trying to identify the image..");
                message.setContentDescription("Now trying to identify the image..");
                break;

            case 2:
                message.setText("Getting info..");
                message.setContentDescription("Getting info..");
                break;

            case 3:
                message.setText("Sorry, we can't recognize your image");
                message.setContentDescription("Sorry, we can't recognize your image");
                break;
        }
    }

    @Override
    protected String doInBackground(String... url) {

      //  uploadImage();


       publishProgress(IDENTIFYING_START);


        CSApi api = new CSApi(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                API_KEY
        );
        CSPostConfig imageToPost = CSPostConfig.newBuilder()
                .withRemoteImageUrl(""+url[0])//http://i.imgur.com/xHGpFRL.gif   response.data.link
                .build();

        try {



            CSPostResult portResult = api.postImage(imageToPost);

            Log.d("debug", "Post result: " + portResult);

            Thread.sleep(25000);

           // while(!portResult.getStatus().equals("completed"));


            CSGetResult scoredResult = api.getImage(portResult);
            Log.d("debug", "" + scoredResult);

           //  return scoredResult.getStatus();

            if(scoredResult.getStatus().equals("completed")){

             publishProgress(SEARCHING_START);

             searchForInfoURL(scoredResult.getName());
             getInfoFromWikipedia();

            }
            else {
                publishProgress(3);
                s="Sorry, we can't recognize your image";
                textDescription ="Sorry, we can't recognize your image";
                glassT="Sorry, we can't recognize your image";
                return scoredResult.getStatus();
            }

            }
        catch ( Exception e) {
            //System.out.println("Error");
            Log.d("debug", "Post result exception: " + e.fillInStackTrace());

            s="Error, Connection timed out";
            textDescription ="Error, Connection timed out";
            glassT="Error, Connection timed out";


        }


        return "empty";
    }


    public void searchForInfoURL(String query){

        Customsearch customsearch=new Customsearch(HTTP_TRANSPORT,JSON_FACTORY,REQUEST_INITIALIZER);
        //List.setQ("effile tower in paris");
        try{
            Customsearch.Cse.List list= customsearch.cse().list(query).setCx(CUSTOM_SEARCH_ENGINE_ID).setKey(GOOGLE_API_KEY);

            infoUrl=list.execute().getItems().get(0).getLink();
            Log.d("debug", "Result: " + infoUrl);

        }catch (IOException e){

            Log.d("debug","error in search "+ e.getMessage());
            s="Error, Connection timed out";
            textDescription ="Error, Connection timed out";
            glassT="Error, Connection timed out";
        }



    }

    public void getInfoFromWikipedia(){
 /*
 String first = "This word is ";
String next = "<font color='#EE0000'>red</font>";
t.setText(Html.fromHtml(first + next));
 * */

        try {
            Document doc = Jsoup.connect(infoUrl).get();
            s = "<br/><b><font color='#7DBABB' >"+doc.title().substring(0,doc.title().indexOf("- Wiki"))+"</font><b> <br/>"; // we should remove it
            glassT = doc.title().substring(0,doc.title().indexOf("- Wiki"))+"\n";
            textDescription = doc.title().substring(0,doc.title().indexOf("- Wiki"));
            for ( Element table : doc.select("table.infobox")){
                for (Element row : table.getElementsByTag("tr")){ // table row
                    for (Element th : row.getElementsByTag("th")) {  //  cell header
                        s += "<br/><b><font color='#7DBABB' >"+th.text() + ": "+"</font><b>"; // print cell header
                        textDescription += th.text();
                        glassT += th.text() + ": "; }

                    Elements tds = row.getElementsByTag("td"); // cell data
                    if (tds.size() == 0) {//{
                        s += "<br/>"; // move to another header
                    glassT+= "\n";}
                       // t.append(Html.fromHtml(s)); }
                    else
                        for (Element td : tds) {
                            s += "<font color='#000000'>"+td.text()+"</font><br/>"; // print cell data
                            textDescription +=  td.text();
                            s += td.text()+"\n";
                            glassT += td.text()+"\n";}


                }
            }


            /*
            Document doc = Jsoup.connect(infoUrl).get();
            s = doc.title()+"\n"; // we should remove it
            for ( Element table : doc.select("table.infobox")){
                for (Element row : table.getElementsByTag("tr")){ // table row
                    for (Element th : row.getElementsByTag("th"))  //  cell header
                        s += th.text() + ":\n"; // print cell header

                    Elements tds = row.getElementsByTag("td"); // cell data
                    if (tds.size() == 0)
                        s+= "================== \n"; // move to another header
                    else
                        for (Element td : tds)
                            s += td.text()+"\n"; // print cell data

                }}
            * */

            //  Elements ths = row.getElementsByTag("th");
            //  Elements rows = table.getElementsByTag("tr");

            //Element e = doc.select("#firstHeading").first();
            // for(int i=0 ; i<e.size() ; i++)
            // s+= "\n" + e.get(i).text();
            //s = ""+e;
            //t.setText(e.toString());


        } catch (IOException e) {
            e.printStackTrace();

            s="Error, Connection timed out";
            textDescription ="Error, Connection timed out";
            glassT="Error, Connection timed out";
        }

    }


  @Override
    protected void onPostExecute(String result){

      if(infoUrl!=null)
      t.setText(Html.fromHtml(s+"<br/> <a href=\""+infoUrl+"\"> Click here to read more </a> <br/>"));
      else t.setText(Html.fromHtml(s));
               //t.setText(s);
               t.setContentDescription(textDescription);
      message.setText("");
      message.setContentDescription("");

      if(VariablesAndConstants.isFromGlass){

          GlassActivity.sendToGlass(glassT);

          VariablesAndConstants.isFromGlass=false;
      }
      flipper.showNext();

       }

}
