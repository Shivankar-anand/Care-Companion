package com.example.locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorHomepage extends AppCompatActivity {
    
    Button active, pending, completed;
    public static Double spLat, spLng;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Doctors_type").child(user.getUid());
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ServiceProviders c = dataSnapshot.getValue(ServiceProviders.class);
                assert c != null;
                spLat = c.getLatitude();
                spLng = c.getLongitude();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view_sp);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_sp);
        NavigationUI.setupWithNavController(navView, navController);
        
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DoctorHomepage.this);
        builder.setMessage("Do you want to exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                moveTaskToBack(true);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void logout(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DoctorHomepage.this);
        builder.setMessage("Do you want to Logout ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DoctorHomepage.this, MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void contact(View v) {
        Intent intent = new Intent(DoctorHomepage.this, contact_us.class);
        startActivity(intent);

    }

    public void reset(View v) {
        Intent intent = new Intent(DoctorHomepage.this, ResetPassword.class);
        startActivity(intent);

    }

    public void changelocation(View v) {
        Intent intent = new Intent(DoctorHomepage.this, AskLoactionDoctor.class);
        startActivity(intent);
    }


    public void onServiceClick(View v) {
        Button b = (Button) v;
        String buttontext = b.getText().toString();

        Intent intent = new Intent(DoctorHomepage.this, RequestStatus.class);
        intent.putExtra("buttontext", buttontext);
        startActivity(intent);
    }

    public void servicehistory(View v) {
        Intent intent = new Intent(DoctorHomepage.this, RequestStatus.class);
        intent.putExtra("buttontext", "Completed Requests");
        startActivity(intent);

    }
    
}