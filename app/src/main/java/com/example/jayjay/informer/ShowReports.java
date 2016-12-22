package com.example.jayjay.informer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class ShowReports extends AppCompatActivity {

    DatabaseReference databaseReference;
    private FirebaseListAdapter listAdapter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);
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
                                    intent = new Intent(ShowReports.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(ShowReports.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(ShowReports.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(ShowReports.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(ShowReports.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(ShowReports.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(ShowReports.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(ShowReports.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(ShowReports.this, FaqList.class);
                                }
                                if (intent != null) {
                                    ShowReports.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();
//        ProgressDialog pd = new ProgressDialog(ShowReports.this);
//        pd.setTitle("Loading...");
//        pd.setMessage("Please wait.");
//        pd.setCancelable(false);
//        pd.show();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("ReportPOJO");
//
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

                    firstname.setText("Reported by:  " + model.getFirstName().toUpperCase());
                    firstname.setTypeface(null, Typeface.BOLD);
                    lastname.setText(model.getLastName().toUpperCase());
                    firstname.append(" " + lastname.getText());
                    lastname.setVisibility(View.INVISIBLE);

                    county.setText("County:  " + model.getCounty());
                    constituency.setText(model.getConstituency());
                    constituency.setVisibility(View.INVISIBLE);
                    ward.setText("Ward/Constituency:  " + model.getWard());
                    ward.append(", " + constituency.getText() + "\n");
                    pollingstation.setText("Polling Station:  " + model.getPollingStation());
                    pollingstation.append("\n");
                    description.setText("Election Offence:  " + model.getDescription());
                    description.append("\n");
                    perpetrator.setText("Perpetrator:  " + model.getPerpetrator());
                }

                @Override
                protected void onCancelled(DatabaseError error) {
                    super.onCancelled(error);

                    Log.e("firebase_error", error.toString());
                }
            };

            reportlist.setAdapter(listAdapter);
            //pd.dismiss();

//        reportlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final List item = (reportlist) parent.getItemAtPosition(position);
//                Toast.makeText(ShowReports.this, item + " selected", Toast.LENGTH_LONG).show();
//            }
//
//        });

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

}
