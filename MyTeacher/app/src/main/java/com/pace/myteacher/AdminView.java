package com.pace.myteacher;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AdminView extends AppCompatActivity {
    int numOfReviews = 0;
    long ageOfReviews = 0;
    double avgScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("reviews")
                .whereEqualTo("teacherId", getIntent().getStringExtra("teacherId"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.toObject(Reviews.class).getPublishTime() != null) {
                                    numOfReviews++;
                                    avgScore += Double.parseDouble(document.toObject(Reviews.class).getRating());

                                    long date = document.toObject(Reviews.class).getPublishTime().toDate().getTime();
                                    ageOfReviews += date;

                                    System.out.println(document.getId() + " => " + document.getData());
                                }

                            }
                            avgScore = avgScore / numOfReviews;
                            ageOfReviews /= numOfReviews;

                            TextView numReviews = (TextView) findViewById(R.id.numOfReviewsLabel);
                            numReviews.setText("There are " + numOfReviews + " reviews.");
                            TextView age = (TextView) findViewById(R.id.avgTime);
                            age.setText("The Average Rating is " + avgScore + ".");
                            TextView time = (TextView) findViewById(R.id.avgRatingLabel);
                            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                            String formatted = format.format(ageOfReviews);
                            Date avgDate = new Date(formatted);
                            int avgDays = avgDate.getDate();
                            time.setText("The Average Length is " + avgDays + " days.");


                        } else {
                        }
                    }
                });

    }
}