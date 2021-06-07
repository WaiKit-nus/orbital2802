package com.example.orbital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Homescreen extends AppCompatActivity implements View.OnClickListener {
    private CardView profile, events, joinedevents, feats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        //define cards
        profile = (CardView) findViewById(R.id.profile_card);
        events = (CardView) findViewById(R.id.events_card);
        joinedevents = (CardView) findViewById(R.id.joinedevents_card);
        feats = (CardView) findViewById(R.id.feats_card);

        //Add click Listener to clock
        profile.setOnClickListener(this);
        events.setOnClickListener(this);
        joinedevents.setOnClickListener(this);
        feats.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.profile_card: i = new Intent(this, Profile.class); startActivity(i); break;
            case R.id.events_card: i = new Intent(this, EventList.class); startActivity(i);break;
            case R.id.joinedevents_card: i = new Intent(this, Joined.class); startActivity(i);break;
            case R.id.feats_card: i = new Intent(this, Feats.class); startActivity(i); break;
            default:break;
        }


    }
}