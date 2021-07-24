package com.example.orbital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.hbb20.CountryCodePicker;

public class Whatsapp extends AppCompatActivity {


    TextView mEventName, mEventperson, mEventnum;
    //CountryCodePicker countryCodePicker;
    EditText message;
    Button sendbtn;

    String messagestr, phonestr = "";
    String mName, mperson, mnum;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        mEventName = findViewById(R.id.ws_event_name);
        mEventnum = findViewById(R.id.ws_event_num);
        mEventperson = findViewById(R.id.ws_event_person);
       // countryCodePicker = findViewById(R.id.event_countrycode);
        message = findViewById(R.id.message_event);
        sendbtn = findViewById(R.id.btn_ws);


        mName = getIntent().getStringExtra("EventName");
        mperson = getIntent().getStringExtra("PersonName");
        mnum = getIntent().getStringExtra("Number");

        mEventName.setText(mName);
        mEventnum.setText(mnum);
        mEventperson.setText(mperson);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagestr = message.getText().toString();

                if (!messagestr.isEmpty()) {
                    //phonestr= countryCodePicker.getFullNumber();
                    if (isWhatappInstalled()) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mnum +
                                "&text=" + messagestr));
                        startActivity(i);
                        message.setText("");
                    }else {
                        Toast.makeText(Whatsapp.this, "ws not install", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Whatsapp.this, "Please Fill in your message", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private boolean isWhatappInstalled() {

        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {

            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;


        } catch (PackageManager.NameNotFoundException e) {

            whatsappInstalled = false;
            Log.d("Failed to find Whatsapp:", e.toString());

        }

        return whatsappInstalled;
    }
}