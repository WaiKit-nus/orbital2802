package com.example.orbital;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;

public class Splashscreen extends AppCompatActivity {

    ImageView appname, applogo;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        appname = findViewById(R.id.logo_words);
        applogo = findViewById(R.id.logo_round);
        lottieAnimationView = findViewById(R.id.lottie);

        applogo.animate().translationY(-2500).setDuration(1000).setStartDelay(5000);
        appname.animate().translationY(2000).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.animate().translationY(1500).setDuration(1000).setStartDelay(5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(Splashscreen.this,Loginpage.class));

            }
        },6000);

    }



}
