
package com.example.locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.locale.location.NearbyLocations;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity {

    public static Double cusLat, cusLng;
    CardView nearbyLocation, labTest, mediConsult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        nearbyLocation = findViewById(R.id.findNearByHospitals);
        nearbyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://www.google.com/maps/search/hospitals+near+me"));
                startActivity(browserIntent);
//
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container2, new NearbyLocations()).commit();
//                Intent intent = new Intent(Main2Activity.this, NearbyHospital.class);
//                startActivity(intent);
            }
        });

        labTest = findViewById(R.id.testLab);
        labTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, LabTestActivity.class));
            }
        });

        mediConsult = findViewById(R.id.medi_consult_rel);
        mediConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Main2Activity.this, MediConsult.class));
            }
        });

    }


    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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

    public void onServiceClick(View v) {
        TextView b = (TextView) v;
        String buttontext = b.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patients").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customers c = dataSnapshot.getValue(Customers.class);
                cusLat = c.getLatitude();
                cusLng = c.getLongitude();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(Main2Activity.this, selectProviders.class);
        intent.putExtra("buttontext", buttontext);
        startActivity(intent);
    }

    public void onDoctorClick(View v) {
        TextView b = (TextView) v;
        String buttontext = b.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patients").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customers c = dataSnapshot.getValue(Customers.class);
                cusLat = c.getLatitude();
                cusLng = c.getLongitude();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent = new Intent(Main2Activity.this, selectDoctors.class);
        intent.putExtra("buttontext", buttontext);
        startActivity(intent);
    }

    public void reset(View v) {
        Intent intent = new Intent(Main2Activity.this, ResetPassword.class);
        startActivity(intent);

    }

    public void contact(View v) {

        Intent intent = new Intent(Main2Activity.this, contact_us.class);
        startActivity(intent);

    }

    public void logout(View v) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setMessage("Do you want to Logout ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
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

    public void changelocation(View v) {
        Intent intent = new Intent(Main2Activity.this, ChangeLocationCustomer.class);
        startActivity(intent);
    }

    public void sentrequestlist(View view) {
        Intent intent = new Intent(Main2Activity.this, Requests.class);
        startActivity(intent);
    }

}