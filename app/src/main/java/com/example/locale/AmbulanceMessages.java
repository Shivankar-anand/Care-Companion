package com.example.locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceMessages extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private DocListAdapter docListAdapter;
    private List<ServiceProviders> musers;

    FirebaseUser fuser;
    DatabaseReference database, database1, database2;

    private List<Chatlist> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_messages);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        database = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    userList.add(chatlist);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void chatList() {
        musers = new ArrayList<>();
        database1 = FirebaseDatabase.getInstance().getReference("Doctors");
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                musers.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ServiceProviders c = dataSnapshot1.getValue(ServiceProviders.class);
                    for (Chatlist chatlist : userList) {
                        if (c.getId().equals(chatlist.getId())) {
                            musers.add(c);
                        }
                    }

                }
                listAdapter = new ListAdapter(AmbulanceMessages.this, musers);
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}