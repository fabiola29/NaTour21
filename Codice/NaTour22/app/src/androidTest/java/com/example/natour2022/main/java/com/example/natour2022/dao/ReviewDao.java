package com.example.natour2022.dao;

import com.example.natour2022.dao.listeners.RetrievalEventListener;
import com.example.natour2022.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;


public class ReviewDao extends FirebaseDao<Review> {

    private FirebaseAuth auth;

    public ReviewDao() {
        super("Reviews");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Review> retrievalEventListener) {
        Review currReview = new Review();

        auth = FirebaseAuth.getInstance();
        if (dataSnapshot.child("reviewId").getValue() != null) {
            currReview.setTitleReview(dataSnapshot.child("titleReview").getValue().toString());
            currReview.setEmailUserReview(dataSnapshot.child("email").getValue().toString());
            currReview.setDescriptionReview(dataSnapshot.child("descriptionReview").getValue().toString());
            currReview.setVoteItinerary(Integer.parseInt(dataSnapshot.child("voteReview").getValue().toString()));
            currReview.setImageReview(dataSnapshot.child("image").getValue().toString());
            currReview.setItineraryId(dataSnapshot.child("itineraryId").getValue().toString());
            currReview.setReviewId(dataSnapshot.child("reviewId").getValue().toString());
            currReview.setUserId(dataSnapshot.child("userId").getValue().toString());

            retrievalEventListener.OnDataRetrieved(currReview);
        }
    }
}