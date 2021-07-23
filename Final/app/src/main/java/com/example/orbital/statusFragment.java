package com.example.orbital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class statusFragment extends Fragment {


    private FirebaseFirestore firebaseFirestore;
    LinearLayoutManager linearLayoutManager;
    private FirebaseAuth firebaseAuth;

    StorageReference storageReference;

    FirestoreRecyclerAdapter<OrgModel, ViewHolder2> statusAdapter;
    RecyclerView mRecyclerView;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.statusfragment, container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        mRecyclerView = v.findViewById(R.id.statusview);
        Query query = firebaseFirestore.collection("Orgdetail");
        FirestoreRecyclerOptions<OrgModel> allorgname = new FirestoreRecyclerOptions.Builder<OrgModel>()
                .setQuery(query,OrgModel.class)
                .build();

        statusAdapter = new FirestoreRecyclerAdapter<OrgModel, ViewHolder2>(allorgname) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder2 holder, int i, @NonNull OrgModel orgmodel) {
                holder.the_Eventname.setText(orgmodel.getNameEvent());
                holder.the_name.setText(orgmodel.getOrg());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),Whatsapp.class);
                        intent.putExtra("EventName",orgmodel.getNameEvent());
                        intent.putExtra("PersonName",orgmodel.getOrg());
                        intent.putExtra("Number",orgmodel.getNumber());
                        startActivity(intent);
                    }
                });

            }

            @NonNull

            @Override
            public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statusview_layout,parent,false);
                return new ViewHolder2(view);
            }
        };

        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(statusAdapter);

        return v;
    }


    public class ViewHolder2 extends  RecyclerView.ViewHolder{

        private TextView the_Eventname;
        private TextView the_name;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            the_Eventname = itemView.findViewById(R.id.nameofevent);
            the_name = itemView.findViewById(R.id.nameofperson);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        statusAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        statusAdapter.stopListening();
    }
}
