package com.example.jayjay.informer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPollingStation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Spinner countyspinner;
    private Spinner constituencyspinner;
    private Spinner wardspinner;
    private ArrayAdapter<Stations> countyNamesAdapter;
    private final List<Stations> county_object = new ArrayList<Stations>();
    private AutoCompleteTextView actv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_polling_station);

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
                .withHeaderBackground(R.drawable.header)
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
                                intent = new Intent(SearchPollingStation.this, HomeActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(SearchPollingStation.this, VoterEducation.class);
                            }
                            if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(SearchPollingStation.this, SearchPollingStation.class);
                            }
                            if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(SearchPollingStation.this, FaqList.class);
                            }
                            if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(SearchPollingStation.this, ViolationReports.class);
                            }
                            if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(SearchPollingStation.this, CountDown.class);
                            }
                            if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(SearchPollingStation.this, Alerts.class);
                            }
                            if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(SearchPollingStation.this, VoteInvite.class);
                            }
                            if (intent != null) {
                                SearchPollingStation.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("pollingStationsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> county_name = new ArrayList<String>();

                for (DataSnapshot countySnapshot : dataSnapshot.getChildren()) {
                    String countyName = countySnapshot.child("county_name").getValue(String.class);
                    county_name.add(countyName);
                }
                countyspinner = (Spinner) findViewById(R.id.county_spinner);
                countyspinner.setOnItemSelectedListener(SearchPollingStation.this);
                ArrayAdapter<String> countyNamesAdapter = new ArrayAdapter<String>(SearchPollingStation.this, android.R.layout.simple_spinner_item, county_name);
                countyNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countyspinner.setAdapter(countyNamesAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        databaseReference.child("pollingStationsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> constituency_name = new ArrayList<String>();

                for (DataSnapshot constituencySnapshot : dataSnapshot.getChildren()) {
                    String constituencyName = constituencySnapshot.child("constituency_name").getValue(String.class);
                    constituency_name.add(constituencyName);
                }
                constituencyspinner = (Spinner) findViewById(R.id.constituency_spinner);
                constituencyspinner.setOnItemSelectedListener(SearchPollingStation.this);
                ArrayAdapter<String> constituencyNamesAdapter = new ArrayAdapter<String>(SearchPollingStation.this, android.R.layout.simple_spinner_item, constituency_name);
                constituencyNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                constituencyspinner.setAdapter(constituencyNamesAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        databaseReference.child("pollingStationsData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> caw_name = new ArrayList<String>();

                for (DataSnapshot cawSnapshot : dataSnapshot.getChildren()) {

                    String cawName = cawSnapshot.child("caw_name").getValue(String.class);
                    caw_name.add(cawName);
                }
                wardspinner = (Spinner) findViewById(R.id.caw_spinner);
                wardspinner.setOnItemSelectedListener(SearchPollingStation.this);
                ArrayAdapter<String> cawNamesAdapter = new ArrayAdapter<String>(SearchPollingStation.this, android.R.layout.simple_spinner_item, caw_name);
                cawNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                wardspinner.setAdapter(cawNamesAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

//        //Child the root before all the push() keys are found and add a ValueEventListener()
//        databaseReference.child("pollingStationsData").orderByChild("county_name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                final List<String> county_name = new ArrayList<String>();
//
//                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
//                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
//                    //Get the suggestion by childing the key of the string you want to get.
////                    String countyName = suggestionSnapshot.child("county_name").getValue(String.class);
//////                    //Add the retrieved string to the list
////                    county_name.add(countyName);
//                    Stations countySnap = suggestionSnapshot.getValue(Stations.class);
//                    county_name.add(countySnap.getCounty_name());
//                    county_object.add(countySnap);
//
//                }
//                Log.e("TAG", "county_objects size: " + county_object.size());
//                actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
//                ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(SearchPollingStation.this,android.R.layout.simple_list_item_1, county_name);
//                actv.setThreshold(3);
//                actv.setAdapter(autoComplete);
//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Snackbar.make(view, "Clicked " + adapterView.getItemAtPosition(i).toString(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


