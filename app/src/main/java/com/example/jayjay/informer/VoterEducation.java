package com.example.jayjay.informer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by JayJay on 06/09/2016.
 */
public class VoterEducation extends AppCompatActivity {
    Button goToVoterRegbutton;
    Button goToVotingbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voter_education_main);

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
    }
//public class VoterEducation extends Fragment {
//
//    @Nullable
//    @Override
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.voter_education_main, container, false);
//
//        return rootView;
//    }



//    @Override
//    public void onBackPressed() {
//
//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//        }
//        else {
//            getFragmentManager().popBackStack();
//        }
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        if( id == android.R.id.home){
//            onBackPressed();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
