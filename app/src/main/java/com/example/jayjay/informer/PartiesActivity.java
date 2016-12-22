package com.example.jayjay.informer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Spinner partySpinner;
    private final List<Parties> party_objects = new ArrayList<Parties>();
    private NetworkImageView partySymbolNetworkImageView;
    private ImageLoader partySymbolImageLoader;

    private TextView partyAbbrv, partyWebsite, partySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parties);

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
                                    intent = new Intent(PartiesActivity.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(PartiesActivity.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(PartiesActivity.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(PartiesActivity.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(PartiesActivity.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(PartiesActivity.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(PartiesActivity.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(PartiesActivity.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(PartiesActivity.this, FaqList.class);
                                }
                                if (intent != null) {
                                    PartiesActivity.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();
            //partySymbol = (TextView) findViewById(R.id.partysymbol);
            partyAbbrv = (TextView) findViewById(R.id.partyabbrv);
            partyWebsite = (TextView) findViewById(R.id.partywebsite);

            databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.child("partiesData").orderByChild("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<String> party_name = new ArrayList<String>();

                    for (DataSnapshot partySnapshot : dataSnapshot.getChildren()) {
                        Parties partySnap = partySnapshot.getValue(Parties.class);
                        party_name.add(partySnap.getName());
                        party_objects.add(partySnap);
                    }
                    Log.e("TAG", "party_objects" + party_objects.size());
                    Collections.sort(party_name);
                    partySpinner = (Spinner) findViewById(R.id.party_spinner);
                    partySpinner.setOnItemSelectedListener(PartiesActivity.this);
                    ArrayAdapter<String> partyNamesAdapter = new ArrayAdapter<String>(PartiesActivity.this, android.R.layout.simple_spinner_item, party_name);
                    partyNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    partySpinner.setAdapter(partyNamesAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            partySymbolNetworkImageView = (NetworkImageView) findViewById(R.id
                    .symbolimage);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

        String item = parent.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        partySymbolImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        //Image URL - This can point to any image file supported by Android
        final String partysymbolurl = party_objects.get(i).getSymbol().toString();
        partySymbolImageLoader.get(partysymbolurl, ImageLoader.getImageListener(partySymbolNetworkImageView,
                R.mipmap.defaultimage, android.R.drawable.ic_dialog_alert));
        partySymbolNetworkImageView.setImageUrl(partysymbolurl, partySymbolImageLoader);
        //partySymbol.setText("Click to view party symbol: " + party_objects.get(i).getSymbol());
        Log.e("TAG", "symb" + party_objects.get(i).getSymbol());
        //Linkify.addLinks(partySymbol, Linkify.ALL);
        partyAbbrv.setText("" + party_objects.get(i).getAbr());
        Log.e("TAG", "abr" + party_objects.get(i).getAbr());
        partyWebsite.setText("Click to view more details about the party: " + party_objects.get(i).getWebsite());
        Log.e("TAG", "website" + party_objects.get(i).getWebsite());
        Linkify.addLinks(partyWebsite, Linkify.ALL);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
