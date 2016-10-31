package com.example.jayjay.informer;

/**
 * Created by JayJay on 10/31/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class FaqList extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqlist);

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