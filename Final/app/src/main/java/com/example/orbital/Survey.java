package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Survey extends AppCompatActivity {

    private RatingBar ratingBar1, ratingBar2, ratingBar3;
    private EditText editText1,editText2,editText3;
    private Button submitButtom;


    protected String eventUID;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    StorageReference storageReference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        ratingBar1 = findViewById(R.id.ratingStarGroup1);
        ratingBar2 = findViewById(R.id.ratingStarGroup2);
        ratingBar3 = findViewById(R.id.ratingStarGroup3);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        submitButtom = findViewById(R.id.submitSurveyBtn);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        eventUID = getIntent().getStringExtra("UID");

        submitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if Survey is filled
                if(ratingBar1.getRating() == 0.0 || ratingBar2.getRating() == 0.0 || ratingBar3.getRating() == 0.0 || TextUtils.isEmpty(editText1.toString()) || TextUtils.isEmpty(editText2.toString()) || TextUtils.isEmpty(editText3.toString())){
                    Toast.makeText(Survey.this, "Please Fill up all the survey!", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadToDatabase();
                    Intent intent = new Intent(Survey.this, PastEventInfo.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void uploadToDatabase() {
        Map<String, Object> surveyDetails = new HashMap<>();
        surveyDetails.put("Coordination:", String.valueOf(ratingBar1.getRating()));
        surveyDetails.put("Engagement:",String.valueOf(ratingBar2.getRating()));
        surveyDetails.put("Overall:", String.valueOf(ratingBar3.getRating()));
        surveyDetails.put("Challenge Faced:", editText1.getText().toString());
        surveyDetails.put("Area of Improvement:", editText2.getText().toString());
        surveyDetails.put("Event Highlight:", editText3.getText().toString());

        userID = mAuth.getCurrentUser().getUid();
        db.collection("Events").document(eventUID).collection("Feedback").document(userID).set(surveyDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Survey.this, "Survey Completed! Thank You", Toast.LENGTH_SHORT).show();
                }
                else
                    Log.d("Failed", "Failed to upload Survey");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Survey.this, "Please try again", Toast.LENGTH_SHORT).show();
                Log.d("Print", e.toString());
            }
        });
    }
}