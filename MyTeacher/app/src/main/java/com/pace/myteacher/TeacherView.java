package com.pace.myteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TeacherView extends AppCompatActivity {
    private static final String TAG = "TeacherView";
    String avgRating = "";
    private RecyclerView reviewsRecyclerView;
    private ReviewItemAdapter adapter;
    private List<ReviewItem> reviewItemList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_teacher_view);
        Button reportBtn = (Button) findViewById(R.id.reportViewBtn);
        Bundle bs = new Bundle();

        bs.putString("teacherId", getIntent().getStringExtra("teacherUserID"));

        Button name = new Button(getApplicationContext());
        Button btn = (Button) findViewById(R.id.teacherViewName);
        btn.setText(getIntent().getStringExtra("teacherName") + " (" + getIntent().getStringExtra("district") + ")");
        Intent intentReport = new Intent(TeacherView.this, AdminView.class);
        intentReport.putExtras(bs);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentReport);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        TableLayout tl = (TableLayout) findViewById(R.id.teacherTable);
        Intent intentNewReview = new Intent(TeacherView.this, NewReview.class);
        Bundle b = new Bundle();

        b.putString("teacherId", getIntent().getStringExtra("teacherUserID"));
        b.putString("teacherEmail", getIntent().getStringExtra("teacherEmail"));

        intentNewReview.putExtras(b);
        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run () {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("reviews")
                        .whereEqualTo("teacherId", getIntent().getStringExtra("teacherUserID"))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Teacher On Success");
                                    double total = 0.00;
                                    double numOfRev = 0.0;
                                    NumberFormat formatter = new DecimalFormat("####.###");
                                    Calendar date = Calendar.getInstance();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Calendar date2WeeksAgo = Calendar.getInstance();
                                        date2WeeksAgo.add(Calendar.WEEK_OF_YEAR, -2);
                                        Date calPosted = document.toObject(Reviews.class).getPublishTime().toDate();
                                        if (calPosted.compareTo(date2WeeksAgo.getTime()) >= 0) {
                                            try {
                                                total = total + Double.parseDouble(document.toObject(Reviews.class).getRating());
                                            } catch (Exception e) {
                                                total += 0;
                                            }
                                            numOfRev++;
                                            //create an object of ReviewItem with reviewer,rating and review.
                                            ReviewItem reviewItem = new ReviewItem(document.toObject(Reviews.class).getReviewer(),
                                                    document.toObject(Reviews.class).getRating(), document.toObject(Reviews.class).getReview());
                                            Log.d(TAG, "***************  Reviewer : " + reviewItem.getReviewer() + " Rating : " + reviewItem.getRating() + " Review : " + reviewItem.getReview() + "  ****************");

                                            //add the review to the array list
                                            reviewItemList.add(reviewItem);

                                        }


                                    }

                                    total = total / numOfRev;
                                    System.out.println("TOTAL: " + total);
                                    System.out.println("TOTAL: " + total);
                                    avgRating = "Rating : " + formatter.format(total);
                                    TextView ratingLabel = (TextView) findViewById(R.id.welcomeString);
                                    ratingLabel.setText(avgRating);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            adapter = new ReviewItemAdapter(TeacherView.this, reviewItemList);
                                            reviewsRecyclerView.setAdapter(adapter);
                                        }
                                    });

                                } else {
                                    System.out.println("Error getting documents: " + task.getException());
                                    Log.d(TAG, "Error getting documents: " + task.getException());
                                }


                            }
                        });
            }
        };

        myTimer.scheduleAtFixedRate(myTask , 0l, 5 * (60*1000));
        FloatingActionButton newBtn = (FloatingActionButton) findViewById(R.id.newReviewBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intentNewReview);
            }
        });
        Button sendEmailBtn = (Button) findViewById(R.id.emailTeacher);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getIntent().getStringExtra("teacherEmail")});
        System.out.println(getIntent().getStringExtra("teacherEmail"));
        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        });
        Log.d(TAG,"Teacher Review Loaded");


    }
}
