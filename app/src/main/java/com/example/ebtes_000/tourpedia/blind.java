package com.example.ebtes_000.tourpedia;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Locale;
import android.widget.ArrayAdapter;


/**
 * Created by bashayer on 22/04/2016.
 */
public class blind extends AppCompatActivity {

    private ListView lv;
    static final int check = 1111;
    static String [] texts = {"Welcome to Tourpedia, What do you want to do?"
             , "Identify landmarks" , "Guidance" ,"Make a plan" , "Setting", "Filter"};
    String [] guide = {"Attraction","Restaurant","Restroom","Taxi-Stand"};
    String [] plan = {"add plan","edit plan", "delete plan"};
    TextToSpeech tts ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();   // to hide the actionBar
        setContentView(R.layout.blind);

        lv = (ListView) findViewById(R.id.lvVoiceReturn);
        ImageButton record = (ImageButton) findViewById(R.id.recordButton);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT , "Speak");
                startActivityForResult(i, check);
            }
        });


        Button listen = (Button) findViewById(R.id.bTextToVoice);
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < texts.length ; i++)
                    ConvertToSpeech(texts[i]);
            }
        });

        tts = new TextToSpeech(blind.this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.ENGLISH);}
            }
        });

    }//onCreate end


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == check && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , results));
            String str = results.get(0);
            switch (str) {
                case "Identify landmarks":
                    // call identify class
                    break;
                case "Guidance":
                    tts.speak("which type of places you want?",TextToSpeech.QUEUE_FLUSH , null);
                    // call guide arr

                    break;
                case "Make a plan":
                   // tts.speak("what do you want?",TextToSpeech.QUEUE_ADD , null,"1");
                    // call plan arr
                    break;
                case "Setting":
                    // call setting
                    break;
                case "Filter":
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    void ConvertToSpeech(String str) {
        tts.speak(str , TextToSpeech.QUEUE_ADD , null);
    }


}
