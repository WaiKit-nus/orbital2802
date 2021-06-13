package com.example.orbital;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

public class EventList extends AppCompatActivity implements View.OnClickListener{
    private CardView coding, dlc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        //define cards
        coding = (CardView) findViewById(R.id.coding_card);
        dlc = (CardView) findViewById(R.id.dlc_card);

        coding.setOnClickListener(this);
        dlc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.coding_card: i = new Intent(this, CodingEvent.class); startActivity(i); break;
            case R.id.dlc_card: i = new Intent(this, CodingEvent.class); startActivity(i);break;
            default:break;
        }


    }
}