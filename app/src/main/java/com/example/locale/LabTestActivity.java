package com.example.locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LabTestActivity extends AppCompatActivity {

    RecyclerView dailyRecyclerview;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference aRef;
    String userId;
    Query query;

    LinearLayout progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        // ini
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        dailyAssignment();

    }

    private void dailyAssignment() {
        aRef = FirebaseDatabase.getInstance().getReference().child("test_data");
        dailyRecyclerview= findViewById(R.id.dailyAllAssignmentRecyclerview);
        dailyRecyclerview.setHasFixedSize(true);
        dailyRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressBar1 = findViewById(R.id.home_progree_bar);
        progressBar1.setVisibility(View.VISIBLE);

        query = aRef.orderByChild("test_name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    progressBar1.setVisibility(View.GONE);
                    dailyList();
                }
                else {
                    progressBar1.setVisibility(View.GONE);
                    Toast.makeText(LabTestActivity.this, ":", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dailyList() {
        FirebaseRecyclerOptions<Assignment> options = new FirebaseRecyclerOptions.Builder<Assignment>()
                .setQuery(query, Assignment.class)
                .build();
        FirebaseRecyclerAdapter<Assignment, AssignmentAdapter> AssignmentAdapter = new FirebaseRecyclerAdapter<Assignment,
                AssignmentAdapter>(options) {
            @NonNull
            @Override
            public AssignmentAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent,
                        false);
                AssignmentAdapter holder = new AssignmentAdapter(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull AssignmentAdapter holder, final int position, @NonNull final Assignment model) {

                progressBar1.setVisibility(View.GONE);
                holder.testName.setText(model.getTest_name());
                holder.testDetails.setText(model.getTest_details());
                holder.testPrice.setText(model.getTest_price());

                Glide.with(LabTestActivity.this).load(model.getTest_image()).into(holder.testImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent AssignmentDetailActivity = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);

                        AssignmentDetailActivity.putExtra("test_name",model.getTest_name());
                        AssignmentDetailActivity.putExtra("test_details",model.getTest_details());
                        AssignmentDetailActivity.putExtra("test_price",model.getTest_price());
                        AssignmentDetailActivity.putExtra("test_image",model.getTest_image());
                        AssignmentDetailActivity.putExtra("test_number",model.getTest_number());
                        AssignmentDetailActivity.putExtra("postKey",model.getPostKey());

                        // will fix this later i forgot to add user name to Assignment object
                        //AssignmentDetailActivity.putExtra("userName",mData.get(position).getUsername);
                        long timestamp  = (long) model.getTimeStamp();
                        AssignmentDetailActivity.putExtra("testDate",timestamp) ;
                        startActivity(AssignmentDetailActivity);
                    }
                });
            }
        };

        dailyRecyclerview.setAdapter(AssignmentAdapter);
        AssignmentAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}