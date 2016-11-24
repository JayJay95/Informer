package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowReports extends AppCompatActivity {

    DatabaseReference databaseReference;
    private FirebaseListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ReportPOJO");

        ListView reportlist = (ListView) findViewById(R.id.report_list_view);

        //Initialize listAdapter and set properties such as model class, food layout and reference to the realtime database
        listAdapter = new FirebaseListAdapter<ReportPOJO>(this, ReportPOJO.class, R.layout.report_card, databaseReference) {

            @Override
            protected void populateView(View v, ReportPOJO model, int position) {
                TextView firstname = (TextView) v.findViewById(R.id.reportcard_firstname);
                TextView lastname = (TextView) v.findViewById(R.id.reportcard_lastname);
                TextView county = (TextView) v.findViewById(R.id.reportcard_county);
                TextView constituency = (TextView) v.findViewById(R.id.reportcard_constituency);
                TextView ward = (TextView) v.findViewById(R.id.reportcard_ward);
                TextView pollingstation = (TextView) v.findViewById(R.id.reportcard_pollingstation);
                TextView description = (TextView) v.findViewById(R.id.reportcard_description);
                TextView perpetrator = (TextView) v.findViewById(R.id.reportcard_perpetrator);

                firstname.setText(model.getFirstName());
                lastname.setText(model.getLastName());
                county.setText(model.getCounty());
                constituency.setText(model.getConstituency());
                ward.setText(model.getWard());
                pollingstation.setText(model.getPollingStation());
                description.setText(model.getDescription());
                perpetrator.setText(model.getPerpetrator());
            }

            @Override
            protected void onCancelled(DatabaseError error) {
                super.onCancelled(error);

                Log.e("firebase_error", error.toString());
            }
        };

        reportlist.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ShowReports.this, ViolationReports.class));
            }
        });
    }

}
