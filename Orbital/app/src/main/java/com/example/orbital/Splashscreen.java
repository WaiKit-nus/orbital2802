package com.example.orbital;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class Splashscreen extends AppCompatActivity {
    TextToSpeech toSpeech;
    int result;
    String text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    textView=(TextView) findViewById(R.id.slogan);
                    toSpeech= new TextToSpeech(Splashscreen.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status==TextToSpeech.SUCCESS)
                            {
                                result=toSpeech.setLanguage(Locale.UK);
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                                {
                                    Toast.makeText(getApplicationContext(),"Fearure not supported in your device", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    text= textView.getText().toString();
                                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                }

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Feature not supported in your device", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    sleep(3000);
                    Intent intent = new Intent (getApplicationContext(),Homescreen.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        myThread.start();


    }
}
