package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jayjay.informer.app.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.jayjay.informer.app.AppController;
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

/**
 * Created by JayJay on 06/09/2016.
 */
public class VoterEducation extends AppCompatActivity {
    Button goToVoterRegbutton;
    Button goToVotingbutton;
    Button goToVoterRightsbutton;
    Button goToVoterDutiesbutton;
    Button goToElectionOffencesbutton;
    Button goToBallotMarkingbutton;
    private FirebaseAuth firebaseAuth;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;
    private NetworkImageView votingprocessNetworkImageView;
    private ImageLoader votingprocessImageLoader;
    private NetworkImageView ballotmarkingNetworkImageView;
    private ImageLoader ballotmarkingImageLoader;
    private NetworkImageView voterrightsgNetworkImageView;
    private ImageLoader voterrightsImageLoader;
    private NetworkImageView voterdutiesNetworkImageView;
    private ImageLoader voterdutiesImageLoader;
    private NetworkImageView electionoffencesNetworkImageView;
    private ImageLoader electionoffencesImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voter_education_main);

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
                                    intent = new Intent(VoterEducation.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(VoterEducation.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(VoterEducation.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(VoterEducation.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(VoterEducation.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(VoterEducation.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(VoterEducation.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(VoterEducation.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(VoterEducation.this, FaqList.class);
                                }
                                if (intent != null) {
                                    VoterEducation.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();

            mNetworkImageView = (NetworkImageView) findViewById(R.id
                    .vrthumbnail);
            votingprocessNetworkImageView = (NetworkImageView) findViewById(R.id
                    .votingprocessthumbnail);
            ballotmarkingNetworkImageView = (NetworkImageView) findViewById(R.id
                    .ballotmarkingthumbnail);
            voterrightsgNetworkImageView = (NetworkImageView) findViewById(R.id
                    .voterrightsthumbnail);
            voterdutiesNetworkImageView = (NetworkImageView) findViewById(R.id
                    .voterdutiesthumbnail);
            electionoffencesNetworkImageView = (NetworkImageView) findViewById(R.id
                    .electionoffencesthumbnail);


            goToVoterRegbutton = (Button) findViewById(R.id.voterregbutton);
            goToVoterRegbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, VoterRegMainPage.class);
                    startActivity(i);
                }
            });

            goToVotingbutton = (Button) findViewById(R.id.votingprocessbutton);
            goToVotingbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, VotingMainPage.class);
                    startActivity(i);
                }
            });
            goToVoterRightsbutton = (Button) findViewById(R.id.voterrightsbutton);
            goToVoterRightsbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, VoterRights.class);
                    startActivity(i);
                }
            });
            goToVoterDutiesbutton = (Button) findViewById(R.id.voterdutiesbutton);
            goToVoterDutiesbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, VoterDuties.class);
                    startActivity(i);
                }
            });
            goToElectionOffencesbutton = (Button) findViewById(R.id.electionoffencesbutton);
            goToElectionOffencesbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, ElectionOffences.class);
                    startActivity(i);
                }
            });
            goToBallotMarkingbutton = (Button) findViewById(R.id.ballotmarkingbutton);
            goToBallotMarkingbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(VoterEducation.this, BallotMarkingMainPage.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String url = "http://www.iebc.or.ke/images/gallery/voterreg2016/Voter%20Registration%20%20(12).JPG";
        mImageLoader.get(url, ImageLoader.getImageListener(mNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        mNetworkImageView.setImageUrl(url, mImageLoader);

        votingprocessImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String votingprocessurl = "http://www.iebc.or.ke/images/gallery/KabeteandOlooluaBy-election/Kabete%20and%20Oloolua%20By-election%20(22).JPG";
        votingprocessImageLoader.get(votingprocessurl, ImageLoader.getImageListener(votingprocessNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        votingprocessNetworkImageView.setImageUrl(votingprocessurl, votingprocessImageLoader);

        ballotmarkingImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String ballotmarkingurl = "http://www.iebc.or.ke/images/gallery/KabeteandOlooluaBy-election/Kabete%20and%20Oloolua%20By-election%20(5).JPG";
        ballotmarkingImageLoader.get(ballotmarkingurl, ImageLoader.getImageListener(ballotmarkingNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        ballotmarkingNetworkImageView.setImageUrl(ballotmarkingurl, ballotmarkingImageLoader);

        voterrightsImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String voterrightssurl = "http://www.iebc.or.ke/images/gallery/KabeteandOlooluaBy-election/Kabete%20and%20Oloolua%20By-election%20(19).JPG";
        voterrightsImageLoader.get(voterrightssurl, ImageLoader.getImageListener(voterrightsgNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        voterrightsgNetworkImageView.setImageUrl(voterrightssurl, voterrightsImageLoader);

        voterdutiesImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String voterdutiessurl = "http://www.iebc.or.ke/images/gallery/Masongaleni/Masongaleni%20and%20Nyagores%20MCA%20By-elections%20%20(18).JPG";
        voterdutiesImageLoader.get(voterdutiessurl, ImageLoader.getImageListener(voterdutiesNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        voterdutiesNetworkImageView.setImageUrl(voterdutiessurl, voterdutiesImageLoader);

        electionoffencesImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String electionoffencesurl = "http://www.iebc.or.ke/images/gallery/KabeteandOlooluaBy-election/Kabete%20and%20Oloolua%20By-election%20(6).JPG";
        electionoffencesImageLoader.get(electionoffencesurl, ImageLoader.getImageListener(electionoffencesNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable
                        .ic_dialog_alert));
        electionoffencesNetworkImageView.setImageUrl(electionoffencesurl, electionoffencesImageLoader);


    }


}
