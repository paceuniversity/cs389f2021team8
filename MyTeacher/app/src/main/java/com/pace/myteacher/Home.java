package com.pace.myteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity  {
    static Teachers t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView welcomeStr = (TextView) findViewById(R.id.welcomeString);
        welcomeStr.setText("Welcome, " + user.getEmail());

        TableLayout tl = (TableLayout) findViewById(R.id.teacherTable);
        Timer myTimer = new Timer ();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run () {
                db.collection("teachers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println(document.getId() + " => " + document.getData());
                                        TableRow tr = new TableRow(getApplicationContext());

                                        Button b = new Button(getApplicationContext());
                                        b.setText(document.toObject(Teachers.class).getTeacherName());
                                        b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1.0f));
                                        Intent intentMain = new Intent(Home.this, TeacherView.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id",document.toObject(Teachers.class).getId() );
                                        bundle.putString("teacherUserID", document.toObject(Teachers.class).getTeacherUserID());
                                        bundle.putString("teacherName", document.toObject(Teachers.class).getTeacherName());
                                        bundle.putString("rating", document.toObject(Teachers.class).getTeacherRating());
                                        bundle.putString("teacherEmail", document.toObject(Teachers.class).getTeacherEmail());
                                        bundle.putString("district", document.toObject(Teachers.class).getDistrict());

                                        intentMain.putExtras(bundle);
                                        t = document.toObject(Teachers.class);
                                        b.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(intentMain);
                                            }
                                        });
                                        tr.addView(b);

                                        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);

                                        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                                        tl.requestLayout();
                                    }
                                } else {
                                    System.out.println("Error getting documents: "+ task.getException());
                                }
                            }
                        });

            }
        };

        myTimer.scheduleAtFixedRate(myTask , 0l, 5 * (60*1000)); // Runs every 5 mins


            }
            public void goToTeacher(){

            }
        }
