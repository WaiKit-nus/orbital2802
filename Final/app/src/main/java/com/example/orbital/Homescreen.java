package com.example.orbital;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Homescreen extends AppCompatActivity implements View.OnClickListener {
    private CardView profile, events, joinedevents, survey;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        logout = findViewById(R.id.logout_btn);

        //define cards
        profile = (CardView) findViewById(R.id.profile_card);
        events = (CardView) findViewById(R.id.events_card);
        joinedevents = (CardView) findViewById(R.id.joinedevents_card);
        survey = (CardView) findViewById(R.id.survey_card);

        //Add click Listener to clock
        profile.setOnClickListener(this);
        events.setOnClickListener(this);
        joinedevents.setOnClickListener(this);
        survey.setOnClickListener(this);
        logoutUser();
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.profile_card: i = new Intent(this, Profile.class); startActivity(i); break;
            case R.id.events_card: i = new Intent(this, EventList.class); startActivity(i);break;
            case R.id.joinedevents_card: i = new Intent(this, Joined.class); startActivity(i);break;
            case R.id.survey_card: i = new Intent(this, Survey.class); startActivity(i); break;
            default:break;
        }


    }

   public void logoutUser(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Homescreen.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Homescreen.this, Loginpage.class));
            }
        });
    }
}