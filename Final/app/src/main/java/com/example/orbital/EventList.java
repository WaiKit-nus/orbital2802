package com.example.orbital;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter  adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnItemClickListener mListener;
    private ArrayList<ExampleItem> mExampleList;

    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db ;
    StorageReference storageReference;


    ArrayList<String> eventList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        mRecyclerView = findViewById(R.id.recyclerView);
        //Query
        Query query = db.collection("Events");
        //FireStoreRecycler
        FirestoreRecyclerOptions<ExampleItem> options = new FirestoreRecyclerOptions.Builder<ExampleItem>().setQuery(query, ExampleItem.class).build();
        //FireStore Adapter
        adapter = new FirestoreRecyclerAdapter<ExampleItem, ExampleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ExampleViewHolder holder, int position, @NonNull ExampleItem model) {
                //ExampleItem currentItem = mExampleList.get(position);
                StorageReference profileRef = storageReference.child("Events/" + model.getUid() + "/profile.png");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(holder.mImageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("1st UID", "1st getUid" + model.getUid().toString());
                        Log.e("Failed:", "onFailure: Failed to retrieve URI" + e.toString());
                    }
                });
                //holder.mImageView.setImageURI(model.getProfile());
                holder.mTextView1.setText(model.getEventName());
                holder.mTextView2.setText(model.getEventLocation());
                holder.mTextView3.setText(model.getDay());
                holder.mTextView4.setText(model.getMonth());
                holder.mTextView5.setText(model.getCount());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EventList.this, EventInfo.class);
                        intent.putExtra("UID", model.getUid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
                ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
                return evh;
            }

        };
        buildRecyclerView(adapter);

        EditText editText = findViewById(R.id.find_event);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

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
            if (item.getEventName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if (item.getEventLocation().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mExampleList = filteredList;
        adapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public void buildRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setHasFixedSize(true);//wont change in size
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    public interface  OnItemClickListener {
        void onItemClick(int position);
    }

    private class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;// name
        public TextView mTextView2;// location
        public TextView mTextView3;// day
        public TextView mTextView4;// Month
        public TextView mTextView5;// count interest

        public ExampleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.card_image);
            mTextView1 = itemView.findViewById(R.id.eventTitle);
            mTextView2 = itemView.findViewById(R.id.event_location);
            mTextView3 = itemView.findViewById(R.id.day);
            mTextView4 = itemView.findViewById(R.id.month);
            mTextView5 = itemView.findViewById(R.id.count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                        }
                    }

                }
            });
        }
    }

    @Override
    protected void onStop(){super.onStop(); adapter.stopListening();}
    @Override
    protected void onStart(){super.onStart(); adapter.startListening();}
}





