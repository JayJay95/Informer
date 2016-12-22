package com.example.jayjay.informer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViolationReports extends AppCompatActivity implements View.OnClickListener {

    private EditText editFirstName;
    private EditText editLastName;
    private EditText editDescription;
    private EditText editPerpetrator;
    private EditText editTextCounty;
    private EditText editTextConstituency;
    private EditText editTextWard;
    private EditText editTextPollingStation;
    //private TextView textViewPersons;
    private ImageButton btnRecordVideo;
    private ImageButton btnCapturePicture;
    private ImageButton btnRecordAudio;
    private Button buttonSave;
    private Button showReports;

    private MediaRecorder mRecorder;
    private String mFileName = null;
    private String videoFileName = null;
    private static final String LOG_TAG = "Record_log";
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private int x = 001;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_reports);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        } else {
            Log.e("TAG", "User ID: " + firebaseAuth.getCurrentUser().getUid());
            PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
            PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_voter_education).withIcon(getResources().getDrawable(R.drawable.ic_menu_educate));
            PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Search for Polling Stations").withIcon(getResources().getDrawable(R.drawable.ic_polling_station));
            PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("View Political Parties").withIcon(getResources().getDrawable(R.drawable.ic_group_work_black_24dp));
            PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_reports).withIcon(getResources().getDrawable(R.drawable.ic_report_violation));
            PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName("Election Day Countdown").withIcon(getResources().getDrawable(R.drawable.ic_menu_timer));


            SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName("Get Alerts").withIcon(getResources().getDrawable(R.drawable.ic_menu_share));
            SecondaryDrawerItem item8 = new SecondaryDrawerItem().withIdentifier(8).withName("Send Invites").withIcon(getResources().getDrawable(R.drawable.ic_chat_black_24dp));
            SecondaryDrawerItem item9 = new SecondaryDrawerItem().withIdentifier(9).withName("FAQs").withIcon(getResources().getDrawable(R.drawable.ic_faq_list));
            SecondaryDrawerItem item10 = new SecondaryDrawerItem().withIdentifier(10).withName("Maps").withIcon(getResources().getDrawable(R.drawable.ic_polling_station));

            // Create the AccountHeader
            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.myheader)
                    .addProfiles(
                            new ProfileDrawerItem().withName(firebaseAuth.getCurrentUser().getDisplayName()).withEmail(firebaseAuth.getCurrentUser().getEmail()).withIcon((R.drawable.profile))
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            return false;
                        }
                    })
                    .build();

            //create the drawer and remember the `Drawer` result object
            Drawer result = new DrawerBuilder()
                    .withAccountHeader(headerResult)
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            item1,
                            item2,
                            item3,
                            item4,
                            item5,
                            item6,
                            new DividerDrawerItem(),
                            item7,
                            item8,
                            item9,
                            item10
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 1) {
                                    intent = new Intent(ViolationReports.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(ViolationReports.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(ViolationReports.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(ViolationReports.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(ViolationReports.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(ViolationReports.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(ViolationReports.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(ViolationReports.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(ViolationReports.this, FaqList.class);
                                }
                                if (drawerItem.getIdentifier() == 10) {
                                    intent = new Intent(ViolationReports.this, PollingStations.class);
                                }
                                if (intent != null) {
                                    ViolationReports.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();
            Firebase.setAndroidContext(this);

            mStorage = FirebaseStorage.getInstance().getReference();
            mProgress = new ProgressDialog(this);

            buttonSave = (Button) findViewById(R.id.buttonSave);
            btnCapturePicture = (ImageButton) findViewById(R.id.btnCapturePicture);
            btnRecordAudio = (ImageButton) findViewById(R.id.btnRecordAudio);
            btnRecordVideo = (ImageButton) findViewById(R.id.btnRecordVideo);
            editFirstName = (EditText) findViewById(R.id.editFirstName);
            editLastName = (EditText) findViewById(R.id.editLastName);
            editTextCounty = (EditText) findViewById(R.id.editTextCounty);
            editTextConstituency = (EditText) findViewById(R.id.editTextConstituency);
            editTextWard = (EditText) findViewById(R.id.editTextWard);
            editTextPollingStation = (EditText) findViewById(R.id.editTextPollingStation);
            editDescription = (EditText) findViewById(R.id.editTextDescription);
            editPerpetrator = (EditText) findViewById(R.id.editTextPerpetrator);

            //textViewPersons = (TextView) findViewById(R.id.textViewPersons);

            showReports = (Button) findViewById(R.id.showReports);


            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating firebase object
                    Firebase ref = new Firebase(Config.FIREBASE_URL);

                    //Getting values to store
                    String firstname = editFirstName.getText().toString().trim();
                    String lastname = editLastName.getText().toString().trim();
                    String county = editTextCounty.getText().toString().trim();
                    String constituency = editTextConstituency.getText().toString().trim();
                    String ward = editTextWard.getText().toString().trim();
                    String pollingstation = editTextPollingStation.getText().toString().trim();
                    String description = editDescription.getText().toString().trim();
                    String perpetrator = editPerpetrator.getText().toString().trim();

//                //Creating Person object
//                ReportPOJO reportpojo = new ReportPOJO();
//
//                //Adding values
//                reportpojo.setFirstName(firstname);
//                reportpojo.setLastName(lastname);
//                reportpojo.setCounty(county);
//                reportpojo.setDescription(description);
//                reportpojo.setPerpetrator(perpetrator);
                    if (firstname.equals("")) {
                        editFirstName.setError("Your First Name is required!");
                        Toast.makeText(v.getContext(), "Your First Name is required!", Toast.LENGTH_LONG).show();
                    } else if (lastname.equals("")) {
                        editLastName.setError("Your Last Name is required!");
                        Toast.makeText(v.getContext(), "Your Last Name is required!", Toast.LENGTH_LONG).show();
                    } else if (county.equals("")) {
                        editTextCounty.setError("Please fill in the county!");
                        Toast.makeText(v.getContext(), "Please fill in the county!", Toast.LENGTH_LONG).show();
                    } else if (constituency.equals("")) {
                        editTextConstituency.setError("Please fill in the constituency!");
                        Toast.makeText(v.getContext(), "Please fill in the constituency!", Toast.LENGTH_LONG).show();
                    } else if (ward.equals("")) {
                        editTextWard.setError("Please fill in the ward!");
                        Toast.makeText(v.getContext(), "Please fill in the ward!", Toast.LENGTH_LONG).show();
                    } else if (pollingstation.equals("")) {
                        editTextPollingStation.setError("Please fill in the polling station!");
                        Toast.makeText(v.getContext(), "Please fill in the polling station!", Toast.LENGTH_LONG).show();
                    } else if (description.equals("")) {
                        editDescription.setError("Please fill in the description!");
                        Toast.makeText(v.getContext(), "Please fill in the description!", Toast.LENGTH_LONG).show();
                    } else if (perpetrator.equals("")) {
                        editPerpetrator.setError("Please fill in the perpetrator!!");
                        Toast.makeText(v.getContext(), "Please fill in the perpetrator!", Toast.LENGTH_LONG).show();
                    } else {
                        //Storing values to firebase
                        ref.child("ReportPOJO").push().setValue(new ReportPOJO(firstname, lastname, county, constituency, ward, pollingstation, description, perpetrator));

                        Toast.makeText(v.getContext(), "Report has been sent", Toast.LENGTH_LONG).show();
//                //Value event listener for realtime data update
//                    ref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot snapshot) {
//                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                                //Getting the data from snapshot
//                                ReportPOJO reportpojo = postSnapshot.getValue(ReportPOJO.class);
//
//                                //Adding it to a string
//                                String string = "First Name: " + reportpojo.getFirstName() + "\n Last Name: " + reportpojo.getLastName() + "\n Perpetrator: " + reportpojo.getPerpetrator() + "\n Description: " + reportpojo.getDescription() +
//                                        "\n County: " + reportpojo.getCounty() + "\n\n";
//
//                                //Displaying it on textview
//                                textViewPersons.setText(string);
//                            }
//                        }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//                        System.out.println("The read failed: " + firebaseError.getMessage());
//                    }
//                });
                    }
                }
            });

            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/recorded_audio.3gp";


            videoFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            videoFileName += "/recorded_video.mpeg";

            btnRecordAudio.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        startRecording();
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        stopRecording();
                        Toast.makeText(view.getContext(), "Recording stopped ...", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
            btnCapturePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);


                }
            });
            btnRecordVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }
                }
            });

            showReports.setOnClickListener(this);

        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        Toast.makeText(this, "Recording started ...", Toast.LENGTH_LONG).show();
    }

    private void stopRecording() {
        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        } catch (RuntimeException stopException) {
            //handle cleanup here
        }
        uploadAudio();
    }


    private void uploadAudio() {
        mProgress.setMessage("Uploading Audio ...");
        mProgress.show();

        String idOne = UUID.randomUUID().toString();
        StorageReference filepath = mStorage.child("Audio").child(idOne);
        Uri uri = Uri.fromFile(new File(mFileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mProgress.dismiss();
                Toast.makeText(getApplicationContext(), "Audio has been uploaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    //upload images and videos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            mProgress.setMessage("Uploading Image..");
            mProgress.show();

            //get the camera image
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();


            String idTwo = UUID.randomUUID().toString();
            StorageReference imagefilepath = mStorage.child("Images").child(idTwo);

            UploadTask uploadTask = imagefilepath.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    //handle success
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Image has been uploaded", Toast.LENGTH_LONG).show();

                }
            });

        }

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK && data != null) {

            mProgress.setMessage("Uploading Video..");
            mProgress.show();

            String idThree = UUID.randomUUID().toString();
            StorageReference videofilepath = mStorage.child("Video").child(idThree);
            Uri mVideoUri = Uri.fromFile(new File(videoFileName));

            UploadTask uploadTask = videofilepath.putFile(mVideoUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    //handle success
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Video has been uploaded", Toast.LENGTH_LONG).show();

                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        if (view == showReports) {
            finish();
            startActivity(new Intent(this, ShowReports.class));
        }

    }

}