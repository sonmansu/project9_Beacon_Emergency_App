package com.example.smbeaconclient.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MyFirestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //not using
//    private static CollectionReference workersCollection = db.collection("workers");
//    private static CollectionReference outsidersCollection = db.collection("outsiders");

    //using
    private static CollectionReference workplaceCollection = db.collection("workplace3");

//    public static CollectionReference getWorkersColRef() {
//        return workersCollection;
//    }
//    public static CollectionReference getWorkersColRef() {        return workersCollection;    }
    public static CollectionReference getWorkplaceColRef() {return workplaceCollection;}

}
