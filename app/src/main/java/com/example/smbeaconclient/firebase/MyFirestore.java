package com.example.smbeaconclient.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyFirestore {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //not using
//    private static CollectionReference workersCollection = db.collection("workers");
//    private static CollectionReference outsidersCollection = db.collection("outsiders");

    //using
    private static CollectionReference workplaceCollection = db.collection("workplace2");

//    public static CollectionReference getWorkersColRef() {
//        return workersCollection;
//    }
//    public static CollectionReference getWorkersColRef() {        return workersCollection;    }
    public static CollectionReference getWorkplaceColRef() {return workplaceCollection;}
}
