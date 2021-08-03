package com.example.smbeaconclient.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyFirestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference workersCollection = db.collection("workers");

    public static CollectionReference getWorkersColInstance() {
        return workersCollection;
    }
}
