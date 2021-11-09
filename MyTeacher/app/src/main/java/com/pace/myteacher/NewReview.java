package com.pace.myteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class NewReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);
        Button postBtn = (Button) findViewById(R.id.addNewBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
            }
        });
    }

    public void addReview(){
        TextView rate = (TextView) findViewById(R.id.ratingEntry);
        TextView textRate = (TextView) findViewById(R.id.editTextTextMultiLine);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> city = new HashMap<>();
        city.put("rating", String.valueOf(rate.getText()));
        city.put("review", String.valueOf(textRate.getText()));
        city.put("reviewer", user.getDisplayName());
        Date d = new Date();
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC-4"));
        
        date.add(Calendar.HOUR_OF_DAY, 1);
        Long timeInMillis = date.getTimeInMillis();
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println("TIME: " + ts.toString());
        city.put("publishTime", ts);

        city.put("teacherId",getIntent().getStringExtra("teacherId"));


        CollectionReference dbCourses = db.collection("reviews");

        // below method is use to add data to Firebase Firestore.
        dbCourses.add(city).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Intent goBack = new Intent(NewReview.this, Home.class);
                startActivity(goBack);
                Toast.makeText(NewReview.this, ts.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(NewReview.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}