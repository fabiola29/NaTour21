package com.example.natour2022.dao;

import com.example.natour2022.dao.listeners.RetrievalEventListener;
import com.example.natour2022.model.Itinerary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class ItineraryDao extends FirebaseDao<Itinerary> {

    private FirebaseAuth auth;

    public ItineraryDao() {
        super("Itinerary");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Itinerary> retrievalEventListener) {
        Itinerary currItinerary = new Itinerary();

        auth = FirebaseAuth.getInstance();
        currItinerary.setItineraryId(dataSnapshot.getKey());
        if (dataSnapshot.child("itineraryId").getValue() != null) {
            currItinerary.setTitleItinerary(dataSnapshot.child("titleItinerary").getValue().toString());
            currItinerary.setDescriptionItinerary(dataSnapshot.child("descriptionItinerary").getValue().toString());
            currItinerary.setDifficultyLevel(dataSnapshot.child("difficultyLevel").getValue().toString());
            currItinerary.setHours(Integer.parseInt(dataSnapshot.child("hours").getValue().toString()));
            currItinerary.setMinutes(Integer.parseInt(dataSnapshot.child("minutes").getValue().toString()));
            currItinerary.setSeconds(Integer.parseInt(dataSnapshot.child("seconds").getValue().toString()));
            currItinerary.setImage_itinerary(dataSnapshot.child("images").getValue().toString());
            currItinerary.setItineraryId(dataSnapshot.child("itineraryId").getValue().toString());
            currItinerary.setEmail(dataSnapshot.child("email").getValue().toString());
            currItinerary.setUserId(dataSnapshot.child("userId").getValue().toString());

            retrievalEventListener.OnDataRetrieved(currItinerary);
        }
     }
}
