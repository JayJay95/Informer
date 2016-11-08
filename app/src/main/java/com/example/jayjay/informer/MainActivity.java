package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kobakei.ratethisapp.RateThisApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.app.FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        }

        RateThisApp.Config config = new RateThisApp.Config(3, 5);
        RateThisApp.init(config);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // If the criteria is satisfied, "Rate this app" dialog will be shown
        RateThisApp.showRateDialogIfNeeded(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_logout) {
            mFirebaseAuth.signOut();
            loadLogInView();
        }
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        android.app.FragmentManager fm = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_timer) {

            Intent openCountDown = new Intent(MainActivity.this, CountDown.class);
            startActivity(openCountDown);
            //fm.beginTransaction().replace(R.id.content_frame,new CountDown()).commit();

        } else if (id == R.id.nav_educate) {

            Intent openVoterEducation = new Intent(MainActivity.this, VoterEducation.class);
            startActivity(openVoterEducation);

            //fm.beginTransaction().replace(R.id.content_frame, new VoterEducation()).commit();
        } else if (id == R.id.nav_station) {

            Intent openPollingStations = new Intent(MainActivity.this, PollingStations.class);
            startActivity(openPollingStations);

        } else if (id == R.id.nav_candidate) {

        } else if (id == R.id.nav_report) {
            Intent openViolationReports = new Intent(MainActivity.this, ViolationReports.class);
            startActivity(openViolationReports);

        } else if (id == R.id.nav_faqlist) {
            Intent openFaqList = new Intent(MainActivity.this, FaqList.class);
            startActivity(openFaqList);

        } else if (id == R.id.nav_alerts) {
            Intent openAlerts = new Intent(MainActivity.this, Alerts.class);
            startActivity(openAlerts);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
