package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateParticulars extends AppCompatActivity {
    private EditText name;
    private EditText address;
    private EditText dob;
    private EditText contact;
    private EditText gender;
    private ImageView profileImage;
    private Button reset;
    private Button confirmBtn;

    //Firebase Cloud Firestore
    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db ;
    //FirebaseDatabase rootNode;
    //DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_particulars);
        //FirebaseFirestore.setLoggingEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        //rootNode = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        profileImage = findViewById(R.id.update_profile_image);
        name = findViewById(R.id.update_name_textview);
        address = findViewById(R.id.update_Address);
        dob = findViewById(R.id.update_DOB);
        contact = findViewById(R.id.update_Contact);
        gender = findViewById(R.id.update_Gender);
        reset = findViewById(R.id.update_button_reset);
        confirmBtn = findViewById(R.id.update_button_confirm);

        /*profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                registerForActivityResult(openGalleryIntent, 1000);
            }
        });*/
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.getText().clear();
                address.getText().clear();
                dob.getText().clear();
                contact.getText().clear();
                gender.getText().clear();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTextField();
            }
        });
    }

    public void checkTextField(){
        String txt_name = name.toString();
        String txt_address = address.toString();
        String txt_dob = dob.toString();
        String txt_contact = contact.toString();
        String txt_gender = gender.toString();
        if(TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_dob) || TextUtils.isEmpty(txt_contact) || TextUtils.isEmpty(txt_gender))
                {
                    Toast.makeText(UpdateParticulars.this, "Please fill in your particulars", Toast.LENGTH_SHORT).show();
                }else if(contact.getText().length() != 8)
                {
                    Toast.makeText(UpdateParticulars.this, "Please check your contact", Toast.LENGTH_SHORT).show();
                }/*else if(!gender.toString().equals("M") || !gender.toString().equals("F") || !gender.toString().equals("m") || !gender.toString().equals("f"))
                {
                    Toast.makeText(UpdateParticulars.this, "Please put your gender as M/F", Toast.LENGTH_SHORT).show();
                }*/
                else
                {
                    Log.d("Working:", "onClick: Button Logging");
                    updateParticulars(); //FIRESTORE THAT WORK
                    //updateParticularsRD();
                    openHomescreenPage();
                }
    }

    //FIRESTORE THAT WORK
    public void updateParticulars(){
        Map<String, Object> users = new HashMap<>();
        users.put("Name",name.getText().toString());
        users.put("Address",address.getText().toString());
        users.put("Date of Birth", dob.getText().toString());
        users.put("Contact", contact.getText().toString());
        users.put("Gender", gender.getText().toString());
        userID = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(userID).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(UpdateParticulars.this, "Values added", Toast.LENGTH_SHORT).show();
                }
                else
                    Log.d("Failed", "Failed to complete Update");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateParticulars.this, "UploadFailed", Toast.LENGTH_SHORT).show();
                Log.d("Print", e.toString());
            }
        });
        /*DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Success", "User profile is updated for " + userID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failure:", e.toString());
            }
        });*/
    }

    /*public void updateParticularsRD(){
        Map<String, Object> users = new HashMap<>();
        users.put("Name",name.getText().toString());
        users.put("Address",address.getText().toString());
        users.put("Date of Birth", dob.getText().toString());
        users.put("Contact", contact.getText().toString());
        users.put("Gender", gender.getText().toString());
        userID = mAuth.getCurrentUser().getUid();
        rootNode.getReference().child("Users").child(userID).updateChildren(users);
    }*/

    public void openHomescreenPage() {
        Intent intent = new Intent(UpdateParticulars.this, Homescreen.class);
        startActivity(intent);
    }
}