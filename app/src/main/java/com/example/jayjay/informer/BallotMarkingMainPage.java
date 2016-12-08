package com.example.jayjay.informer;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

import java.util.Locale;

public class BallotMarkingMainPage extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    Button speechBallotMarkingButton;
    Button validBallotButton;
    Button rejectBallotButton;
    private TextToSpeech tts;
    private TextView ballotMarkingContent;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballot_marking_main_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        } else {
            Log.e("TAG", "User ID: " + firebaseAuth.getCurrentUser().getUid());
        }


        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_voter_education).withIcon(getResources().getDrawable(R.drawable.ic_menu_educate));
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_polling_stations).withIcon(getResources().getDrawable(R.drawable.ic_polling_station));
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_faq).withIcon(getResources().getDrawable(R.drawable.ic_faq_list));
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_reports).withIcon(getResources().getDrawable(R.drawable.ic_report_violation));
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_countdown).withIcon(getResources().getDrawable(R.drawable.ic_menu_timer));

        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_alerts).withIcon(getResources().getDrawable(R.drawable.ic_menu_share));
        SecondaryDrawerItem item8 = new SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_invites).withIcon(getResources().getDrawable(R.drawable.ic_chat_black_24dp));


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
                        item8
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(BallotMarkingMainPage.this, HomeActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(BallotMarkingMainPage.this, VoterEducation.class);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(BallotMarkingMainPage.this, SearchPollingStation.class);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(BallotMarkingMainPage.this, FaqList.class);
                            }
                            if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(BallotMarkingMainPage.this, ViolationReports.class);
                            }
                            if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(BallotMarkingMainPage.this, CountDown.class);
                            }
                            if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(BallotMarkingMainPage.this, Alerts.class);
                            }
                            if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(BallotMarkingMainPage.this, VoteInvite.class);
                            }
                            if (intent != null) {
                                BallotMarkingMainPage.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();
        tts = new TextToSpeech(this, this);
        ballotMarkingContent = (TextView) findViewById(R.id.ballotMarkingMainPageText1);
        speechBallotMarkingButton = (Button) findViewById(R.id.speakBallotMarkingButton);
        speechBallotMarkingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                speakOut();

            }
        });

        validBallotButton = (Button) findViewById(R.id.validBallotButton);
        validBallotButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(BallotMarkingMainPage.this, ValidBallot.class);
                startActivity(i);
                onPause();
                //speechBallotMarkingButton.setEnabled(false);
            }
        });
        rejectBallotButton = (Button) findViewById(R.id.rejectBallotButton);
        rejectBallotButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(BallotMarkingMainPage.this, RejectBallot.class);
                startActivity(i);
                onPause();
                //speechBallotMarkingButton.setEnabled(false);
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
                speechBallotMarkingButton.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        String text = ballotMarkingContent.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}
