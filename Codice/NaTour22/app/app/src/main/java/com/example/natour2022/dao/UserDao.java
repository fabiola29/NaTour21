package com.example.natour2022.dao;

import androidx.annotation.NonNull;

import com.example.natour2022.dao.listeners.RetrievalEventListener;
import com.example.natour2022.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;


public class UserDao extends FirebaseDao<User>  {

    private FirebaseAuth auth;

    public UserDao() {
        super("Users");
    }

    @Override
    protected void parseDataSnapshot(@NonNull DataSnapshot dataSnapshot, RetrievalEventListener<User> retrievalEventListener) {
        User currUser = new User();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currUser.setUserId(dataSnapshot.getKey());
        if (dataSnapshot.child("email").getValue() != null) {
            currUser.setNickName(dataSnapshot.child("nickname").getValue().toString());
            currUser.setEmail(dataSnapshot.child("email").getValue().toString());
            currUser.setPassword(dataSnapshot.child("password").getValue().toString());
            currUser.setNickName(dataSnapshot.child("nickname").getValue().toString());

            retrievalEventListener.OnDataRetrieved(currUser);

        } else {
           FirebaseAuth.getInstance().signOut();
        }
    }


}


