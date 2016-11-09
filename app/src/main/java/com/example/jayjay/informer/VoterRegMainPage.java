package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by JayJay on 11/8/2016.
 */

public class VoterRegMainPage extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    Button sliderButton;
    ImageButton speechVoterRegButton;
    private TextToSpeech tts;
    private TextView voterRegContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votereg_mainpage);

        sliderButton = (Button) findViewById(R.id.beginSliderButton);
        sliderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(VoterRegMainPage.this, VoterRegSlider.class);
                startActivity(i);
            }
        });

        tts = new TextToSpeech(this, this);
        voterRegContent = (TextView) findViewById(R.id.voterRegMainPageText);
        speechVoterRegButton = (ImageButton) findViewById(R.id.speakVoterRegButton);
        speechVoterRegButton.setOnClickListener(new View.OnClickListener() {

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
                speechVoterRegButton.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        String text = voterRegContent.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
