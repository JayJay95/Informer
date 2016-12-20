package com.example.jayjay.informer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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

public class ValidBallot extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_ballot);

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
                                    intent = new Intent(ValidBallot.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(ValidBallot.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(ValidBallot.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(ValidBallot.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(ValidBallot.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(ValidBallot.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(ValidBallot.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(ValidBallot.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(ValidBallot.this, FaqList.class);
                                }
                                if (drawerItem.getIdentifier() == 10) {
                                    intent = new Intent(ValidBallot.this, PollingStations.class);
                                }
                                if (intent != null) {
                                    ValidBallot.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();
        }
    }
}
