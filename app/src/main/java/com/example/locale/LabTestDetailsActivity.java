package com.example.locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import static android.Manifest.permission.CALL_PHONE;

public class LabTestDetailsActivity extends AppCompatActivity {

    ImageView testImage;
    TextView testName, testDetails, testPrice, testTime;
    ImageView call_to_book;
    String PostKey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        testImage = findViewById(R.id.test_image);
        testName = findViewById(R.id.test_name);
        testDetails = findViewById(R.id.test_details);
        testPrice = findViewById(R.id.test_price);
        testTime = findViewById(R.id.assignment_time);

        call_to_book = findViewById(R.id.call_image);

        PostKey = getIntent().getExtras().getString("postKey");

        String postImage = getIntent().getExtras().getString("test_image") ;
        Glide.with(this).load(postImage).into(testImage);

        String postTitle = getIntent().getExtras().getString("test_name");
        testName.setText(postTitle);

        String postDetails = getIntent().getExtras().getString("test_details");
        testDetails.setText(postDetails);

        String postPrice = getIntent().getExtras().getString("test_price");
        testPrice.setText(postPrice);

        String date = timestampToString(getIntent().getExtras().getLong("testDate"));
        testTime.setText(date);

        String number = getIntent().getExtras().getString("test_number");

        call_to_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{CALL_PHONE}, 1);
                    }
                }
            }
        });

    }

    private String timestampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy",calendar).toString();
        return date;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}