package com.example.ebtes_000.tourpedia.Blind;

/**
 * Created by bashayer on 13/04/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.speech.tts.TextToSpeech;
import java.util.ArrayList;
import java.util.Locale;


public class TextToSpeechClass extends Activity implements OnClickListener {

    static String [] texts = {"Welcome to Tourpedia, What do you want to do? "};
    // , "What do you want to do?" , "Identify landmarks" , "Guidance"
    TextToSpeech tts ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.text_to_speech);
        //Button b = (Button) findViewById(R.id.bTextToVoice);
        //b.setOnClickListener(this); //
        tts = new TextToSpeech(TextToSpeechClass.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v) {
        for (int i = 0 ; i < texts.length ; i++)
            tts.speak(texts[i] , TextToSpeech.QUEUE_FLUSH , null);
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */
}