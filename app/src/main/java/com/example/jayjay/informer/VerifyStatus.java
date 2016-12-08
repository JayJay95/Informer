package com.example.jayjay.informer;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kobakei.ratethisapp.RateThisApp;
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

public class VerifyStatus extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button goToRegVerificationbutton;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 14;
    Button sendBtn;
    EditText txtMessage;
    Integer iebcNumber = 22464;
    String phoneNo;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_status);

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
            PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_polling_stations).withIcon(getResources().getDrawable(R.drawable.ic_polling_station));
            PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_faq).withIcon(getResources().getDrawable(R.drawable.ic_faq_list));
            PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_reports).withIcon(getResources().getDrawable(R.drawable.ic_report_violation));
            PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_countdown).withIcon(getResources().getDrawable(R.drawable.ic_menu_timer));


            SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_alerts).withIcon(getResources().getDrawable(R.drawable.ic_menu_share));
            SecondaryDrawerItem item8 = new SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_invites).withIcon(getResources().getDrawable(R.drawable.ic_chat_black_24dp));
            SecondaryDrawerItem item9 = new SecondaryDrawerItem().withIdentifier(9).withName(R.string.myMap).withIcon(getResources().getDrawable(R.drawable.ic_polling_station));

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
                            item9
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            if (drawerItem != null) {
                                Intent intent = null;
                                if (drawerItem.getIdentifier() == 1) {
                                    intent = new Intent(VerifyStatus.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(VerifyStatus.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(VerifyStatus.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(VerifyStatus.this, FaqList.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(VerifyStatus.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(VerifyStatus.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(VerifyStatus.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(VerifyStatus.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(VerifyStatus.this, PollingStations.class);
                                }
                                if (intent != null) {
                                    VerifyStatus.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();

            // Initialize Firebase Auth
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//
//        if (mFirebaseUser == null) {
//            // Not logged in, launch the Log In activity
//            loadLogInView();
//        }

            RateThisApp.Config config = new RateThisApp.Config(3, 5);
            RateThisApp.init(config);


            sendBtn = (Button) findViewById(R.id.btnSendSMS);
            txtMessage = (EditText) findViewById(R.id.editTextmsg);

            sendBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //sendSMSMessage();
                    if (txtMessage.length() > 0) {

                        sendSMSMessage();

                    } else {
                        Toast.makeText(getBaseContext(),
                                "Please enter your ID Number",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            });

//        protected void alertMessage() {
//            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    switch (which) {
//                        case DialogInterface.BUTTON_POSITIVE:
//                            // Yes button clicked
//                            Toast.makeText(VerifyStatus.this, "Yes Clicked",
//                                    Toast.LENGTH_LONG).show();
//                            break;
//
//                        case DialogInterface.BUTTON_NEGATIVE:
//                            // No button clicked
//                            // do nothing
//                            Toast.makeText(VerifyStatus.this, "No Clicked",
//                                    Toast.LENGTH_LONG).show();
//                            break;
//                    }
//                }
//            };
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Are you sure?")
//                    .setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
//        }


        }
    }

    protected void sendSMSMessage() {
        phoneNo = iebcNumber.toString();
        message = txtMessage.getText().toString();
        Log.e("TAG1", "mY mESSAGE: " + message);
        Log.e("TAG2", "mY no: " + phoneNo);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("TAG4", "check self permission: " + Manifest.permission.SEND_SMS);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                Log.e("TAG5", "rationale: " + Manifest.permission.SEND_SMS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                Log.e("TAG6", "MY_PERMISSIONS_REQUEST_SEND_SMS: " + MY_PERMISSIONS_REQUEST_SEND_SMS);
                Log.e("TAG6", "grantresults: " + grantResults.toString());

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PendingIntent pi = PendingIntent.getActivity(this, 0,
                            new Intent(this, VerifyStatus.class), 0);
                    SmsManager smsManager = SmsManager.getDefault();
//                    PendingIntent pi = PendingIntent.getActivity(this, 0,
//                            new Intent(this, VerifyStatus.class), 0);
                    smsManager.sendTextMessage(phoneNo, null, message, pi, null);
                    Log.e("TAG3", "mY no: " + message);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
