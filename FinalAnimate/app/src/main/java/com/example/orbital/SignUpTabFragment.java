package com.example.orbital;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpTabFragment extends Fragment {
    private EditText pwText;
    private EditText confirmpwText;
    private EditText email;

    private Button signup_btn;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sign_up_frag,container,false);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        pwText = root.findViewById(R.id.pass);
        confirmpwText = root.findViewById(R.id.confirmPass);
        email = root.findViewById(R.id.email);
        signup_btn = root.findViewById(R.id.btn_signup);

        signup_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createUser();
                //includeEmailandPW();
                openUpdateParticularPage();
            }
        });
        return root;
    }

    public void createUser(){
        String txt_email = email.getText().toString();
        String txt_pw = pwText.getText().toString();
        String txt_confirmPW = confirmpwText.getText().toString();
        if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pw) || TextUtils.isEmpty(txt_confirmPW))
        {
            Toast.makeText(getActivity(), "Empty Credentials", Toast.LENGTH_SHORT).show();
        }else if(txt_pw.length() < 6)
        {
            Toast.makeText(getActivity(), "Password is too short. More than 6 digits.", Toast.LENGTH_SHORT).show();
        }
        else if(!txt_pw.equals(txt_confirmPW))
        {
            Toast.makeText(getActivity(), "Password is not the same", Toast.LENGTH_SHORT).show();
        }
        else
            registerUsertoFirebase(txt_email,txt_pw);
    }


    public void registerUsertoFirebase(String email, String pw)
    {
        mAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser currentUser = mAuth.getCurrentUser();
                currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Verification Email has been sent." ,Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("onFailure:", "Email not sent" + e.getMessage());
                    }
                });

                Toast.makeText(getActivity(), "Registered!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public void includeEmailandPW(){
        Map<String, Object> user = new HashMap<>();
        user.put("Email", email.getText().toString());
        user.put("Password", pwText.getText().toString());
        userID = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(userID).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(), "Email and PW added", Toast.LENGTH_SHORT).show();
                }
                else
                    Log.d("Failed", "Failed to complete Update");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "UploadFailed", Toast.LENGTH_SHORT).show();
                Log.d("Print", e.toString());
            }
        });

    }*/



    public void openUpdateParticularPage() {
        Intent intent = new Intent(getContext(), UpdateParticulars.class);
        startActivity(intent);
    }
}
