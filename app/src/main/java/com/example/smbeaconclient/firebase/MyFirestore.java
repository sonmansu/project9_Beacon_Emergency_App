package com.example.smbeaconclient.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyFirestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference workersCollection = db.collection("workers");
    private static CollectionReference outsidersCollection = db.collection("outsiders");
    private static CollectionReference workplaceCollection = db.collection("workplace");

    public static CollectionReference getWorkersColRef() {
        return workersCollection;
    }
    public static CollectionReference getWorkplaceColRef() {return workplaceCollection;}
    public static CollectionReference getOutsidersColRef() {return outsidersCollection;}
}
