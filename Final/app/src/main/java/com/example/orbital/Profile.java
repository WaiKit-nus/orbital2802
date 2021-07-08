package com.example.orbital;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private TextView address;
    private TextView dob;
    private TextView contact;
    private TextView gender;
    private TextView name;
    private TextView email;
    private Button edit_btn;
    private ImageView profile_pic;

    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db ;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        name = findViewById(R.id.name_textview);
        email = findViewById(R.id.email_textview);
        address = findViewById(R.id.address_realText);
        dob = findViewById(R.id.dob_realText);
        contact = findViewById(R.id.contact_realText);
        gender = findViewById(R.id.gender_realText);
        edit_btn = (Button) findViewById(R.id.edit_button);
        profile_pic = findViewById(R.id.profile_image);

        StorageReference profileRef = storageReference.child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_pic);
            }
        });
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