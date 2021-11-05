package com.pace.myteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
import java.util.Calendar;
import java.util.TimeZone;

public class TeacherView extends AppCompatActivity  {
    String avgRating = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_teacher_view);
        Button name = new Button(getApplicationContext());
        Button btn = (Button) findViewById(R.id.teacherViewName);
        btn.setText(getIntent().getStringExtra("teacherName"));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TableLayout tl = (TableLayout) findViewById(R.id.teacherTable);

        db.collection("reviews")
                .whereEqualTo("teacherId", getIntent().getStringExtra("teacherUserID"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double total = 0.00;
                            Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC-4"));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.toObject(Reviews.class).getPublishTime().compareTo(new Timestamp(date.getTime())) <= 0) {
                                    try {
                                        total += Double.parseDouble(document.toObject(Reviews.class).getRating());
                                    } catch (Exception e) {
                                        total += 0;
                                    }

                                    System.out.println(document.getId() + " => " + document.getData());
                                    TableRow tr = new TableRow(getApplicationContext());
                                    LinearLayout lay = new LinearLayout(getApplicationContext());
                                    lay.setOrientation(1);
                                    TextView b = new TextView(getApplicationContext());
                                    b.setText(document.toObject(Reviews.class).getReviewer());
                                    b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                                    TextView d = new TextView(getApplicationContext());
                                    d.setText(document.toObject(Reviews.class).getRating());
                                    d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                                    lay.addView(b);
                                    lay.addView(d);
                                    lay.setPadding(20, 0, 0, 0);

                                    tr.addView(lay);
                                    TextView c = new TextView(getApplicationContext());
                                    c.setText(document.toObject(Reviews.class).getReview());
                                    c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                                    c.setPadding(50, 0, 20, 0);
                                    tr.addView(c);
                                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                                    tl.requestLayout();
                                }
                                total = total / task.getResult().size();
                                avgRating = String.valueOf(total);
                                TextView ratingLabel = (TextView) findViewById(R.id.welcomeString);
                                ratingLabel.setText(avgRating);
                            }
                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }


                    }
                });
        Intent intentNewReview = new Intent(TeacherView.this, NewReview.class);
        Bundle b = new Bundle();

        b.putString("teacherId",getIntent().getStringExtra("teacherUserID") );
        intentNewReview.putExtras(b);
        FloatingActionButton newBtn = (FloatingActionButton) findViewById(R.id.newReviewBtn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intentNewReview);
            }
        });
    }
    public void goToTeacher(){

    }
}
