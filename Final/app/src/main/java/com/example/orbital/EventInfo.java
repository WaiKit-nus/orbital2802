package com.example.orbital;

import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventInfo extends AppCompatActivity {
    private Button button_chat, button_join;
    private TextView eventTitle_view, eventLocation_view, eventDate_view, eventTime_view, eventDescription_view, eventOrganiser_view;
    private ImageView eventImage_view;

    protected String eventDate;
    protected int eventDay, eventMonth, eventYear;
    protected String time;
    protected int hour, min;
    protected String eventUID, seteventDay, seteventMonth, seteventYear, seteventTitle;

    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_event);

        getWindow().setStatusBarColor(Color.GRAY);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        button_chat = (Button) findViewById(R.id.btn_chat);
        button_join = (Button) findViewById(R.id.btn_join);
        eventTitle_view = findViewById(R.id.eventTitle);
        eventLocation_view = findViewById(R.id.eventLocation);
        eventDate_view = findViewById(R.id.eventDate);
        eventTime_view = findViewById(R.id.eventTime);
        eventDescription_view = findViewById(R.id.eventDiscription);
        eventOrganiser_view = findViewById(R.id.eventOrganiser);
        eventImage_view = findViewById(R.id.eventImage);
        eventUID = getIntent().getStringExtra("UID");
        retrieveData();


        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadJoinEvent();
                openJoined();
                Toast.makeText(EventInfo.this,"Event Joined!", Toast.LENGTH_SHORT).show();

            }
        });

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();

            }
        });

    }

    public void retrieveData(){
        DocumentReference documentReference = db.collection("Events").document(eventUID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                eventTitle_view.setText(value.getString("eventName"));
                eventLocation_view.setText(value.getString("eventLocation"));
                eventDate_view.setText(value.getString("eventDate"));
                eventDescription_view.setText(value.getString("eventDescription"));
                eventTime_view.setText(value.getString("eventTime"));
                eventOrganiser_view.setText(value.getString("eventOrganiser"));
                eventDate = value.getString("eventDate");
                time = value.getString("eventTime");
                seteventDay = value.getString("Day");
                seteventMonth = value.getString("Month");
                seteventTitle = value.getString("eventName");
            }
        });
        StorageReference profileRef = storageReference.child("Events/" + eventUID + "/profile.png");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(eventImage_view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Failed", e.toString() );
            }
        });
    }

    public void convertInfoForCalander(){
        String[] splitted = eventDate.split("\\s+");
        eventDay = Integer.parseInt(splitted[0]);
        eventMonth = Integer.parseInt(splitted[1]);
        eventYear = Integer.parseInt(splitted[2]);

        String[] splitTime = time.split(":", 2);
        hour = Integer.parseInt(splitTime[0]);
        min = Integer.parseInt(splitTime[1]);
    }

    public void openJoined() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");

        //convertInfoForCalander();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(eventYear,eventMonth,eventDay,hour,min);
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

    protected void uploadJoinEvent()
    {
        Map<String, Object> eventJoined = new HashMap<>();
        eventJoined.put("uid", eventUID);
        eventJoined.put("Day", seteventDay);
        eventJoined.put("Month", seteventMonth);
        eventJoined.put("eventName", seteventTitle);
        db.collection("Users").document(mAuth.getUid()).collection("UpcomingEvents").document(eventUID).set(eventJoined).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                 @Override
                                                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                     if(task.isSuccessful())
                                                                                                                                                     {
                                                                                                                                                         Toast.makeText(EventInfo.this, "Events Joined", Toast.LENGTH_SHORT).show();
                                                                                                                                                     }
                                                                                                                                                     else
                                                                                                                                                         Log.d("Failed", "Failed to complete Update");
                                                                                                                                                 }
                                                                                                                                                 }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventInfo.this, "UploadFailed", Toast.LENGTH_SHORT).show();
                Log.d("Print", e.toString());
            }
        });
    }


    public void openChat() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
}