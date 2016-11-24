package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class VotingMainPage extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    Button sliderButton;
    Button speechVotingButton;
    private TextToSpeech tts;
    private TextView votingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_main_page);

        sliderButton = (Button) findViewById(R.id.beginSliderButton);
        sliderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(VotingMainPage.this, VotingProcessSlider.class);
                startActivity(i);
                speechVotingButton.setEnabled(false);
            }
        });

        tts = new TextToSpeech(this, this);
        votingContent = (TextView) findViewById(R.id.votingMainPageText);
        speechVotingButton = (Button) findViewById(R.id.speakVotingButton);
        speechVotingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                speakOut();

            }
        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);
            tts.setSpeechRate(1);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speechVotingButton.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        String text = votingContent.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}