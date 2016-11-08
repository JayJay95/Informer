package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by JayJay on 11/8/2016.
 */

public class VoterRegMainPage extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.votereg_mainpage);

        button = (Button) findViewById(R.id.beginSliderButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(VoterRegMainPage.this, VoterRegSlider.class);
                startActivity(i);
            }
        });
    }
}
