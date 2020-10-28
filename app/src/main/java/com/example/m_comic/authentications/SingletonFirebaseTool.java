package com.example.m_comic.authentications;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SingletonFirebaseTool {

    private FirebaseAuth myFirebaseAuth;
    private FirebaseFirestore myFireStoreReference;
    private static SingletonFirebaseTool instance = null;

    private SingletonFirebaseTool() {
        myFirebaseAuth = FirebaseAuth.getInstance();
        myFireStoreReference = FirebaseFirestore.getInstance();
    }

    public static SingletonFirebaseTool getInstance() {
        if (instance == null) return instance = new SingletonFirebaseTool();
        return instance;
    }

    public FirebaseAuth getMyFirebaseAuth() {
        return myFirebaseAuth;
    }

    public FirebaseFirestore getMyFireStoreReference() {
        return myFireStoreReference;
    }

}
