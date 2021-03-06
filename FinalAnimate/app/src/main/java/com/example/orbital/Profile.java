package com.example.orbital;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private TextView password;
    private TextView address;
    private TextView dob;
    private TextView contact;
    private TextView gender;
    private TextView name;
    private TextView email;
    private Button edit_btn;

    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name_textview);
        email = findViewById(R.id.email_textview);
        password = findViewById(R.id.password_realText);
        address = findViewById(R.id.address_realText);
        dob = findViewById(R.id.dob_realText);
        contact = findViewById(R.id.contact_realText);
        gender = findViewById(R.id.gender_realText);
        edit_btn = (Button) findViewById(R.id.edit_button);

        retrieveParticulars();
        email.setText(mAuth.getCurrentUser().getEmail().toString());
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    private void retrieveParticulars(){
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                address.setText(value.getString("Address"));
                contact.setText(value.getString("Contact"));
                dob.setText(value.getString("Date of Birth"));
                gender.setText(value.getString("Gender"));
                name.setText(value.getString("Name"));
            }
        });
    }

    protected void editProfile()
    {
        Intent intent = new Intent(this, Editprofile.class);
        startActivity(intent);
    }
}