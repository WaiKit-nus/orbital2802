package com.example.orbital;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private  ArrayList<ExampleItem> mExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        createExampleList();
        buildRecyclerView();

        EditText editText = findViewById(R.id.find_event);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

    }

    private void filter(String text){
        // Handles whenever you type somethings
        ArrayList<ExampleItem> filteredList = new ArrayList<>();

        for (ExampleItem item : mExampleList){
            if (item.getEventname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if (item.getEventlocation().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filteredList(filteredList);
    }



    private void createExampleList(){
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.coding,"Coding Games for Kids", "Zoom", "25", "JUN", "132"));
        mExampleList.add(new ExampleItem(R.drawable.dlc,"Digital Learning Circles", "Woodlands", "3","JUL", "53"));
        mExampleList.add(new ExampleItem(R.drawable.onlineengage,"Online Engagement 101", "Bukit Batok", "18","JUL", "34"));
        mExampleList.add(new ExampleItem(R.drawable.healthteeth,"Let's Be Health-Teeth!", "Sembawang", "30","JUL", "20"));

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);//wont change in size
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), CodingEvent.class);
                startActivity(intent);
            }
        });
    }





    }





