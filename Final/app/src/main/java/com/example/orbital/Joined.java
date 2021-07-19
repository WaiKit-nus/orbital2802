package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Joined extends AppCompatActivity {
    TabLayout joinedTablayout;
    ViewPager2 joinedViewPager;
    JoinedEventsAdapter joinedadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined);

        joinedTablayout = findViewById(R.id.event_tab_layout);
        joinedViewPager = findViewById(R.id.event_view_pager2);
        FragmentManager fm = getSupportFragmentManager();

        joinedadapter = new JoinedEventsAdapter(fm, getLifecycle());
        joinedViewPager.setAdapter(joinedadapter);

        joinedTablayout.addTab(joinedTablayout.newTab().setText("Upcoming"));
        joinedTablayout.addTab(joinedTablayout.newTab().setText("Past Events"));
        joinedTablayout.setTabTextColors(Color.parseColor("#D8D8D8"), Color.parseColor("#FF6200EE"));
        joinedTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        joinedTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                joinedViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        joinedViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position){
                joinedTablayout.selectTab(joinedTablayout.getTabAt(position));
            }
        });
    }
}