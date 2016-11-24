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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private VideoView mVideoView;
    private Uri mVideoUri;
    private ImageView mImageView;
    private Bitmap mImageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_reports);

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
            Uri mVideoUri = Uri.fromFile(new File(mFileName));

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