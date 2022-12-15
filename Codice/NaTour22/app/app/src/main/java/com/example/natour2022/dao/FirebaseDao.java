package com.example.natour2022.dao;

import com.example.natour2022.dao.listeners.RetrievalEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public abstract class FirebaseDao<T> {

    protected static final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    protected String tableName;

    public FirebaseDao(String tableName)
    {
        this.tableName = tableName;
    }

    protected abstract void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<T> retrievalEventListener);

    public String getNewKey() {
        return dbReference.child(tableName).push().getKey();
    }

}

