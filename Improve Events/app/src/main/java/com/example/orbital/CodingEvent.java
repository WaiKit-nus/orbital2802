package com.example.orbital;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CodingEvent extends AppCompatActivity {
    private Button button_chat, button_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_event);

        getWindow().setStatusBarColor(Color.GRAY);

        button_chat = (Button) findViewById(R.id.btn_chat);
        button_join = (Button) findViewById(R.id.btn_join);

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoined();
                Toast.makeText(CodingEvent.this,"Event Joined!", Toast.LENGTH_SHORT).show();

            }
        });

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();

            }
        });

    }

    public void openJoined() {
        Intent intent = new Intent(this, Joined.class);
        startActivity(intent);
    }


    public void openChat() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }
}