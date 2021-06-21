package com.example.orbital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    private EditText email;
    private EditText password;
    private Button login_btn;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_frag,container,false);

        login_btn = root.findViewById(R.id.btn_login);
        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();

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
