package com.example.orbital;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PastEventInfo extends AppCompatActivity {

    private TextView eventTitle_view, eventLocation_view, eventDate_view, eventTime_view, eventDescription_view, eventOrganiser_view;
    private ImageView eventImage_view;
    protected Button survey;
    protected Button chat;

    protected String eventUID;
    protected String seteventDay, seteventMonth, seteventYear, seteventTitle, seteventUID;

    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasteventinfo);

        survey = findViewById(R.id.survey);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        eventTitle_view = findViewById(R.id.pasteventTitle);
        eventLocation_view = findViewById(R.id.pasteventLocation);
        eventDate_view = findViewById(R.id.pasteventDate);
        eventTime_view = findViewById(R.id.pasteventTime);
        eventDescription_view = findViewById(R.id.pasteventDiscription);
        eventOrganiser_view = findViewById(R.id.pasteventOrganiser);
        eventImage_view = findViewById(R.id.pasteventImage);
        eventUID = getIntent().getStringExtra("UID");

        retrieveData();

        survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PastEventInfo.this, Survey.class);
                intent.putExtra("UID", eventUID);
                startActivity(intent);
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

}
