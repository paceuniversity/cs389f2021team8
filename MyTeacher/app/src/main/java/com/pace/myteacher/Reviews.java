package com.pace.myteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

public class Reviews {

    private String rating;
    private String teacherId;
    private String reviewer;
    private String review;
    private int id;

    public Reviews() {

    }

    @PropertyName("rating")
    public String getRating() {
        return rating;
    }
    @PropertyName("rating")
    public void setRating(String rating) {
        this.rating = rating;
    }
    @PropertyName("teacherId")
    public String getTeacherId() {
        return teacherId;
    }
    @PropertyName("teacherId")
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    @PropertyName("reviewer")
    public String getReviewer() {
        return reviewer;
    }
    @PropertyName("reviewer")
    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
    @PropertyName("review")
    public String getReview() {
        return review;
    }
    @PropertyName("review")
    public void setReview(String review) {
        this.review = review;
    }
    @PropertyName("id")
    public int getId() {
        return id;
    }
    @PropertyName("id")
    public void setId(int id) {
        this.id = id;
    }
}
