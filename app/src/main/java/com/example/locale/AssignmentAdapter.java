package com.example.locale;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView testName, testPrice, testDetails, testNumber;
    public ImageView testImage, newsEdit, newsDelete;
    public ProgressBar progressBar;
    public NewsItemClickListener listener;
    private final Context context;

    public AssignmentAdapter(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        testName = itemView.findViewById(R.id.test_name);
        testPrice = itemView.findViewById(R.id.test_price);
        testDetails = itemView.findViewById(R.id.test_details);
        testImage = itemView.findViewById(R.id.test_image);
        progressBar = itemView.findViewById(R.id.loading);

    }

    public void setItemClickListener(NewsItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(),false);
    }

}
