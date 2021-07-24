package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ChatsLog extends AppCompatActivity {
    TabLayout tabLayout;
    TabItem mchat,mcall,mstatus;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Toolbar mtoolbar;

    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_log);

        tabLayout = findViewById(R.id.include);
        //mcall = findViewById(R.id.calls);
        mchat = findViewById(R.id.chat);
        mstatus = findViewById(R.id.status);
        viewPager = findViewById(R.id.fragmentcontainer);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_more_vert_24);
        mtoolbar.setOverflowIcon(drawable);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition()==0 || tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.profile:
                Intent intent = new Intent(ChatsLog.this,Profile.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference = db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));
        documentReference.update("Status","offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is Offline", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference = db.collection("Users").document(Objects.requireNonNull(mAuth.getUid()));
        documentReference.update("Status","online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is online", Toast.LENGTH_SHORT).show();

            }
        });
    }



}