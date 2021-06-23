package com.example.orbital;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class CodingEvent extends AppCompatActivity {
    private Button button_chat, button_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_event);

        getWindow().setStatusBarColor(Color.GRAY);

        button_chat = (Button) findViewById(R.id.btn_chat);
        button_join = (Button) findViewById(R.id.btn_join);

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoined();
                Toast.makeText(CodingEvent.this,"Event Joined!", Toast.LENGTH_SHORT).show();

            }
        });

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();

            }
        });

    }

    public void openJoined() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2021,5,25,14,0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2021,5,25,17,0);


        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime.getTimeInMillis());

        intent.putExtra(CalendarContract.Events.TITLE, "Coding Games for Kids");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,  "Volunteer with the kids!");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Zoom");
        //intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        startActivity(intent);
    }


    public void openChat() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
}