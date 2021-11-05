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

public class Teachers {

    private String teacherName;
    private String teacherUserID;
    private String teacherRating;
    private String id;

    public Teachers() {

    }

    @PropertyName("teacherName")
    public String getTeacherName() {
        return teacherName;
    }
    @PropertyName("teacherName")
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    @PropertyName("teacherUserID")
    public String getTeacherUserID() {
        return teacherUserID;
    }
    @PropertyName("teacherUserID")
    public void setteacherUserID(String teacherUserID) {
        this.teacherUserID = teacherUserID;
    }
    @PropertyName("teacherRating")
    public String getTeacherRating() {
        return teacherRating;
    }
    @PropertyName("teacherRating")
    public void setTeacherRating(String teacherRating) {
        this.teacherRating = teacherRating;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }
    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }
}
