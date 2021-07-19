package com.example.orbital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class upcomingeventfragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EventList.OnItemClickListener mListener;
    private ArrayList<JoinedEventItem> mExampleList;

    FirebaseAuth mAuth;
    private String userID;
    FirebaseFirestore db;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcomingeventfragment, container, false);
        mRecyclerView = v.findViewById(R.id.upcomingrecyclerView);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        Query query = db.collection("Users").document(mAuth.getUid()).collection("UpcomingEvents");
        FirestoreRecyclerOptions<JoinedEventItem> options = new FirestoreRecyclerOptions.Builder<JoinedEventItem>().setQuery(query, JoinedEventItem.class).build();
        adapter = new FirestoreRecyclerAdapter<JoinedEventItem, joinViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull joinViewHolder holder, int position, @NonNull JoinedEventItem model) {
                StorageReference profileRef = storageReference.child("Events/" + model.getUid() + "/profile.png");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(holder.mJoinImageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("1st UID", "1st getUid" + model.getUid().toString());
                        Log.e("Failed:", "onFailure: Failed to retrieve URI" + e.toString());
                    }
                });
                holder.mTextView1.setText(model.getEventName());
                holder.mTextView3.setText(model.getDay());
                holder.mTextView4.setText(model.getMonth());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), UpcomingEventInfo.class);
                        intent.putExtra("UID", model.getUid());
                        Toast.makeText(getContext(), "Clicked activated", Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public joinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joinedevent_card, parent, false);
                return new joinViewHolder(view);
            }
        };
        //mRecyclerView.setHasFixedSize(true);//wont change in size
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        return v;
    }

    private class joinViewHolder extends RecyclerView.ViewHolder {

        public ImageView mJoinImageView;
        public TextView mTextView1;// name
        public TextView mTextView3;// day
        public TextView mTextView4;// Month

        public joinViewHolder(@NonNull View itemView) {
            super(itemView);
            mJoinImageView = itemView.findViewById(R.id.joinedevents_cardview);
            mTextView1 = itemView.findViewById(R.id.joinedevents_eventTitle);
            mTextView3 = itemView.findViewById(R.id.joinedevents_day);
            mTextView4 = itemView.findViewById(R.id.joinedevents_month);

        }
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    }
