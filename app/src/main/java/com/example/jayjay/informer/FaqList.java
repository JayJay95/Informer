package com.example.jayjay.informer;

/**
 * Created by JayJay on 10/31/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

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

public class FaqList extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqlist);
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
                                    intent = new Intent(FaqList.this, HomeActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 2) {
                                    intent = new Intent(FaqList.this, VoterEducation.class);
                                }
                                if (drawerItem.getIdentifier() == 3) {
                                    intent = new Intent(FaqList.this, SearchPollingStation.class);
                                }
                                if (drawerItem.getIdentifier() == 4) {
                                    intent = new Intent(FaqList.this, PartiesActivity.class);
                                }
                                if (drawerItem.getIdentifier() == 5) {
                                    intent = new Intent(FaqList.this, ViolationReports.class);
                                }
                                if (drawerItem.getIdentifier() == 6) {
                                    intent = new Intent(FaqList.this, CountDown.class);
                                }
                                if (drawerItem.getIdentifier() == 7) {
                                    intent = new Intent(FaqList.this, Alerts.class);
                                }
                                if (drawerItem.getIdentifier() == 8) {
                                    intent = new Intent(FaqList.this, VoteInvite.class);
                                }
                                if (drawerItem.getIdentifier() == 9) {
                                    intent = new Intent(FaqList.this, FaqList.class);
                                }
                                if (intent != null) {
                                    FaqList.this.startActivity(intent);
                                }
                            }
                            return false;
                        }
                    })
                    .build();

            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            // Listview Group click listener
            expListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    // Toast.makeText(getApplicationContext(),
                    // "Group Clicked " + listDataHeader.get(groupPosition),
                    // Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

//        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

            // Listview on child click listener
            expListView.setOnChildClickListener(new OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    // TODO Auto-generated method stub
                    Toast.makeText(
                            getApplicationContext(),
                            listDataHeader.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Why use X to mark our correct choice of a candidate in elections when we were taught in school that X stands for the incorrect?");
        listDataHeader.add("Can I be allowed to vote using a police abstract when I registered using my lost ID?");
        listDataHeader.add("Can a voter register in registration centre A and vote in polling station B?");
        listDataHeader.add("How do I transfer my registration from one constituency to another?");
        listDataHeader.add("How do I confirm my registration status e.g. on my phone without going to IEBC office?");
        listDataHeader.add(" What is the validity period of the current register?");
        listDataHeader.add("Are Kenyans living abroad allowed to register as voters?");
        listDataHeader.add("What do I need to register as a voter?");
        listDataHeader.add("Between the final result and electronic result which is the correct result?");
        listDataHeader.add("What is the date of the next general election?");
        listDataHeader.add("In case a candidate withdraws what happens? Is the election allowed to proceed without the name of the person or the election is cancelled?");

        // Adding child data
        List<String> useX = new ArrayList<String>();
        useX.add("The law has not prescribed X as the mark of choice only. It prescribes any other mark e.g. a dot (.), a tick (√), a dash (—), cross (+) or even a thumbprint, as long as it expresses the intention of the voter.");


        List<String> policeAbstract = new ArrayList<String>();
        policeAbstract.add("NO. You cannot be allowed to vote using a police abstract. The law demands that you produce the document you used to register as a voter. What this means is that if you registered using an ID, you must produce the ID, if you registered using a passport you must produce the passport and not vice versa.");


        List<String> centreA = new ArrayList<String>();
        centreA.add("NO. One is supposed to register where he intends to vote. This is because a voter register is polling station specific. i.e. names of persons who registered in registration centre A will find their names in the register of voters for polling station A and not for polling station B or C or any other polling station.");

        List<String> transferReg = new ArrayList<String>();
        transferReg.add("To transfer your registration from one constituency to another, visit the IEBC office in your constituency of choice and upon presentation of you identification documents asked the registration officer to facilitate the transfer by filling FORM D.");

        List<String> confirmReg = new ArrayList<String>();
        confirmReg.add("The Commission is currently testing SMS channel for querying voter registration status. You can try it out by sending your ID number to 22464. It will be launched when all concerns are addressed.");

        List<String> validityPeriod = new ArrayList<String>();
        validityPeriod.add("The current voter register is valid until it is declared invalid either by law or IEBC. The Election Act mandates IEBC to conduct a fresh voter registration, if necessary, at intervals of not less than eight years, and not more than twelve years, immediately after the Commission reviews the names and boundaries of the constituencies in accordance with Article 89 (2) of the Constitution.\n" +
                "\n");

        List<String> kenyansAbroad = new ArrayList<String>();
        kenyansAbroad.add("YES. The commission is making plans to enable them register at the Kenyan embassies and consulates at their convenience.");

        List<String> needVoter = new ArrayList<String>();
        needVoter.add("To register as a voter you must be a Kenyan citizen. This is evidenced by your national ID or passport and you must be 18 years and above.");

        List<String> finalResult = new ArrayList<String>();
        finalResult.add("The electronic results give the same or nearly the same tally with the final results by hard copy. But the law is yet to recognize electronic results so we call it “provisional results”. The results recorded on the statutory forms/results declaration forms signed by the presiding officer or the returning officer and the agents are the official results and are binding in law. The provisional results are lifted from the entries on these forms.");

        List<String> electionDate = new ArrayList<String>();
        electionDate.add("Second Tuesday of August 2017.");

        List<String> candidateWithdraw = new ArrayList<String>();
        candidateWithdraw.add("It depends. If a candidate withdraws and two or more candidates are left in the race, the election proceeds. If a candidate withdraws and one candidate is left in the race we declare a \"no contest\".");


        listDataChild.put(listDataHeader.get(0), useX); // Header, Child data
        listDataChild.put(listDataHeader.get(1), policeAbstract);
        listDataChild.put(listDataHeader.get(2), centreA);
        listDataChild.put(listDataHeader.get(3), transferReg);
        listDataChild.put(listDataHeader.get(4), confirmReg);
        listDataChild.put(listDataHeader.get(5), validityPeriod);
        listDataChild.put(listDataHeader.get(6), kenyansAbroad);
        listDataChild.put(listDataHeader.get(7), needVoter);
        listDataChild.put(listDataHeader.get(8), finalResult);
        listDataChild.put(listDataHeader.get(9), electionDate);
        listDataChild.put(listDataHeader.get(10), candidateWithdraw);

    }
}