package com.example.jayjay.informer;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class SearchPollingStation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_polling_station);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.county_spinner);
        spinner.setItems(
                "Baringo",
                "Bomet",
                "Bungoma",
                "Busia",
                "Elgeyo Marakwet",
                "Embu",
                "Garissa",
                "Homa Bay",
                "Isiolo",
                "Kajiado",
                "Kakamega",
                "Kericho",
                "Kiambu",
                "Kilifi",
                "Kirinyaga",
                "Kisii",
                "Kisumu",
                "Kitui",
                "Kwale",
                "Laikipia",
                "Lamu",
                "Machakos",
                "Makueni",
                "Mandera",
                "Meru",
                "Migori",
                "Marsabit",
                "Mombasa",
                "Muranga",
                "Nairobi",
                "Nakuru",
                "Nandi",
                "Narok",
                "Nyamira",
                "Nyandarua",
                "Nyeri",
                "Samburu",
                "Siaya",
                "Taita Taveta",
                "Tana River",
                "Tharaka Nithi",
                "Trans Nzoia",
                "Turkana",
                "Uasin Gishu",
                "Vihiga",
                "Wajir",
                "West Pokot");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
