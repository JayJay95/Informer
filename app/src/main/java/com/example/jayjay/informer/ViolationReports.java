package com.example.jayjay.informer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViolationReports extends AppCompatActivity {

    private EditText editFirstName;
    private EditText editLastName;
    private EditText editDescription;
    private EditText editPerpetrator;
    private EditText editTextCounty;
    private TextView textViewPersons;
    private Button btnRecordVideo;
    private Button btnCapturePicture;
    private Button btnRecordAudio;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_reports);

        Firebase.setAndroidContext(this);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
        btnRecordAudio = (Button) findViewById(R.id.btnRecordAudio);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editTextCounty = (EditText) findViewById(R.id.editTextCounty);
        editDescription = (EditText) findViewById(R.id.editTextDescription);
        editPerpetrator = (EditText) findViewById(R.id.editTextPerpetrator);

        textViewPersons = (TextView) findViewById(R.id.textViewPersons);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object
                Firebase ref = new Firebase(Config.FIREBASE_URL);

                //Getting values to store
                String firstname = editFirstName.getText().toString().trim();
                String lastname = editLastName.getText().toString().trim();
                String county = editTextCounty.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                String perpetrator = editPerpetrator.getText().toString().trim();

                //Creating Person object
                ReportPOJO reportpojo = new ReportPOJO();

                //Adding values
                reportpojo.setFirstName(firstname);
                reportpojo.setLastName(lastname);
                reportpojo.setCounty(county);
                reportpojo.setDescription(description);
                reportpojo.setPerpetrator(perpetrator);

                //Storing values to firebase
                ref.child("ReportPOJO").setValue(reportpojo);


                //Value event listener for realtime data update
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //Getting the data from snapshot
                            ReportPOJO reportpojo = postSnapshot.getValue(ReportPOJO.class);

                            //Adding it to a string
                            String string = "First Name: " + reportpojo.getFirstName() + "\n Last Name: " + reportpojo.getLastName() + "\n Perpetrator: " + reportpojo.getPerpetrator() + "\n Description: " + reportpojo.getDescription() +
                                    "\n County: " + reportpojo.getCounty() + "\n\n";

                            //Displaying it on textview
                            textViewPersons.setText(string);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

            }
        });
    }


}