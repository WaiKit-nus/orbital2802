package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText address;
    private EditText dob;
    private EditText contact;
    private EditText gender;
    private ImageView edit_imageView;
    private Uri imageUri;

    private Button confirm;
    private Button reset;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(edit_imageView);
            }
        });


        name = findViewById(R.id.edit_name_textview);
        email = findViewById(R.id.edit_email);
        address = findViewById(R.id.edit_address);
        dob = findViewById(R.id.edit_dob);
        contact = findViewById(R.id.edit_contact);
        gender = findViewById(R.id.edit_gender);
        reset = findViewById(R.id.button_reset);
        confirm = findViewById(R.id.button_confirm);
        edit_imageView = findViewById(R.id.profile_image_e);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.getText().clear();
                address.getText().clear();
                dob.getText().clear();
                contact.getText().clear();
                gender.getText().clear();
                email.getText().clear();
            }
        });

        edit_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(dob.getText().toString()) || TextUtils.isEmpty(contact.getText().toString()) || TextUtils.isEmpty(gender.getText().toString()))
                    Toast.makeText(Editprofile.this, "Please fill up all details", Toast.LENGTH_SHORT).show();
                else
                {
                    editDatabase();
                    openProfilePage();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1000 && resultCode == RESULT_OK){
            imageUri = data.getData();
            edit_imageView.setImageURI(imageUri);

            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        StorageReference fileRef = storageReference.child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Editprofile.this, "Uploaded Image", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(edit_imageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Editprofile.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void openProfilePage() {
        Intent intent = new Intent(Editprofile.this, Profile.class);
        startActivity(intent);
    }
}