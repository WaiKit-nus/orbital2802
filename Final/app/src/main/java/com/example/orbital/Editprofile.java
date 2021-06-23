package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText address;
    private EditText dob;
    private EditText contact;
    private EditText password;
    private EditText gender;

    private Button confirm;
    private Button reset;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        name = findViewById(R.id.edit_name_textview);
        email = findViewById(R.id.edit_email);
        address = findViewById(R.id.edit_address);
        dob = findViewById(R.id.edit_dob);
        contact = findViewById(R.id.edit_contact);
        password = findViewById(R.id.confirmPasswordEdit);
        gender = findViewById(R.id.edit_gender);
        reset = findViewById(R.id.button_reset);
        confirm = findViewById(R.id.button_confirm);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.getText().clear();
                address.getText().clear();
                dob.getText().clear();
                contact.getText().clear();
                gender.getText().clear();
                email.getText().clear();
                password.getText().clear();
            }
        });

        /*confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(password.getText().toString()))
                    Toast.makeText(Editprofile.this, "Please input your password!", Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(dob.getText().toString()) || TextUtils.isEmpty(contact.getText().toString()) || TextUtils.isEmpty(gender.getText().toString()))
                    Toast.makeText(Editprofile.this, "Please fill up all details", Toast.LENGTH_SHORT).show();
                else
                {
                    if(password.getText().toString() == currentUser.)
                }
            }
        });*/
    }

    private void editDatabase(){
        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("Name", name.getText().toString());
        userDetail.put("Email", email.getText().toString());
        userDetail.put("Address", address.getText().toString());
        userDetail.put("Date of Birth", dob.getText().toString());
        userDetail.put("Contact", contact.getText().toString());
        userDetail.put("Gender", gender.getText().toString());

        userID = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(userID).update(userDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Editprofile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Editprofile.this,"Please try again later!", Toast.LENGTH_SHORT).show();
                Log.d("FailureToEditProfile", e.getMessage());
            }
        });
    }
}