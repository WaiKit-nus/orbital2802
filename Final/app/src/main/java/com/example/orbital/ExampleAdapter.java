package com.example.orbital;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>{

    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public ExampleAdapter(ArrayList<ExampleItem> mExampleList, OnItemClickListener mListener) {
        this.mExampleList = mExampleList;
        this.mListener = mListener;
    }

    public interface  OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;// name
        public TextView mTextView2;// location
        public TextView mTextView3;// day
        public TextView mTextView4;// Month
        public TextView mTextView5;// count interest

        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
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

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        super();
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        //holder.mImageView.setImageResource(currentItem.getImageResouce());
        holder.mImageView.setImageURI(currentItem.getProfile());
        holder.mTextView1.setText(currentItem.getEventName());
        holder.mTextView2.setText(currentItem.getEventLocation());
        holder.mTextView3.setText(currentItem.getDay());
        holder.mTextView4.setText(currentItem.getMonth());
        holder.mTextView5.setText(currentItem.getCount());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filteredList(ArrayList<ExampleItem> filteredList){
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}


