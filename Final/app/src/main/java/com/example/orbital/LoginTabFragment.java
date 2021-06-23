package com.example.orbital;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    private EditText email;
    private EditText password;
    private TextView forgotPw;
    private Button login_btn;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_frag,container,false);

        login_btn = root.findViewById(R.id.btn_login);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);
        forgotPw = root.findViewById(R.id.forgot_password_Textview);

        mAuth = FirebaseAuth.getInstance();

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to receive a link to reset your password.");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extract Email and send Reset Link
                        String reset_email = resetEmail.getText().toString();
                        mAuth.sendPasswordResetEmail(reset_email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Email has been sent.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Link is not sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                passwordResetDialog.create().show();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        return root;
    }

    public void loginUser(){
        String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();

        if(txt_email.isEmpty() || txt_password.isEmpty())
            Toast.makeText(getActivity(), "Please input your Username/Password", Toast.LENGTH_SHORT).show();
        else{
        mAuth.signInWithEmailAndPassword(txt_email,txt_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                openHomescreen();
            }
        });}
    }

    public void openHomescreen() {
        Intent intent = new Intent(getContext(), Homescreen.class);
        startActivity(intent);
    }
}
