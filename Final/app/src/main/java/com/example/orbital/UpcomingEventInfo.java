package com.example.orbital;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class UpcomingEventInfo extends AppCompatActivity {

    private TextView eventTitle_view, eventLocation_view, eventDate_view, eventTime_view, eventDescription_view, eventOrganiser_view;
    private ImageView eventImage_view;
    protected Button attend;
    protected Button chat;

    protected String eventUID;
    protected String seteventDay, seteventMonth, seteventYear, seteventTitle, seteventUID;

    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomingeventinfo);

        attend = findViewById(R.id.btn_attend);
        chat = findViewById(R.id.joinedchat_btn);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        eventTitle_view = findViewById(R.id.upcomingeventTitle);
        eventLocation_view = findViewById(R.id.upcomingeventLocation);
        eventDate_view = findViewById(R.id.upcomingeventDate);
        eventTime_view = findViewById(R.id.upcomingeventTime);
        eventDescription_view = findViewById(R.id.upcomingeventDiscription);
        eventOrganiser_view = findViewById(R.id.upcomingeventOrganiser);
        eventImage_view = findViewById(R.id.upcomingeventImage);
        eventUID = getIntent().getStringExtra("UID");

        retrieveData();

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrCodeScanner();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(UpcomingEventInfo.this);
            builder.setMessage("Attendance Taken! Thank you!");
            eventUID = intentResult.getContents();
            uploadJoinEvent();
            deleteEvent();
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else
        {
            Toast.makeText(getApplicationContext(),"Failed to scan. Please retry!",Toast.LENGTH_SHORT).show();
        }
    }

    public void openChat() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }

    public void qrCodeScanner()
    {
        //Initialise intent integrator
        IntentIntegrator intentIntegrator = new IntentIntegrator(UpcomingEventInfo.this);
        intentIntegrator.setPrompt("For flash, use Volume Up Key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(QRCapture.class);
        intentIntegrator.initiateScan();
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

    protected void uploadJoinEvent()
    {
        Map<String, Object> eventJoined = new HashMap<>();
        eventJoined.put("uid", eventUID);
        eventJoined.put("Day", seteventDay);
        eventJoined.put("Month", seteventMonth);
        eventJoined.put("eventName", seteventTitle);
        db.collection("Users").document(mAuth.getUid()).collection("PastEvents").document(eventUID).set(eventJoined).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                 @Override
                                                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                     if(task.isSuccessful())
                                                                                                                                                     {
                                                                                                                                                         Toast.makeText(UpcomingEventInfo.this, "Events Joined", Toast.LENGTH_SHORT).show();
                                                                                                                                                     }
                                                                                                                                                     else
                                                                                                                                                         Log.d("Failed", "Failed to complete Update");
                                                                                                                                                 }
                                                                                                                                             }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpcomingEventInfo.this, "UploadFailed", Toast.LENGTH_SHORT).show();
                Log.d("Print", e.toString());
            }
        });
    }

    protected void deleteEvent(){
        db.collection("Users").document(mAuth.getUid()).collection("EventsJoined").document(eventUID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Delete", "Document deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failure", "Failed to delete the document");
            }
        });
    }

}
