package com.example.natour2022.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2022.R;
import com.example.natour2022.adapter.AdapterListReviews;
import com.example.natour2022.model.Review;
import com.example.natour2022.presenter.fragment.ViewReviews_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewReviews_fragment extends Fragment {

    private StartActivity startActivity;
    private ViewReviews_presenter viewReviews_presenter;
    private ImageButton buttonBackListReview;
    private RecyclerView recyclerViewListReviews;
    private List<Review> reviewsList;
    private AdapterListReviews adapterListReviews;
    private String IdItinerary;

    public ViewReviews_fragment(StartActivity startActivity, String IdItinerary) {
        this.startActivity = startActivity;
        this.IdItinerary = IdItinerary;

    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_reviews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        recyclerViewListReviews = view.findViewById(R.id.ReviewListRv);
        recyclerViewListReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        buttonBackListReview = view.findViewById(R.id.back_view_reviews);
        viewReviews_presenter = new ViewReviews_presenter(this);
        getAllReviews();
        listenToClickEvents();
    }

    private void listenToClickEvents() {
        buttonBackListReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReviews_presenter.onButtonBackViewReviewsClicked();
            }
        });
    }

    public void getAllReviews(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceDb = database.getReference("Reviews");

        reviewsList = new ArrayList<>();

        Review rev = new Review();
        reviewsList.clear();

        referenceDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapsho1 = snapshot.getChildren();

                for(DataSnapshot ds : dataSnapsho1) {
                    String id = (String) ds.child("itineraryId").getValue();
                    if(id.equalsIgnoreCase(IdItinerary)){
                        Review rev = new Review(
                                (String)ds.child("titleReview").getValue(),
                                (String)ds.child("email").getValue(),
                                String.valueOf(ds.child("image").getValue()),
                                (String)ds.child("voteReview").getValue(),
                                (String)ds.child("descriptionReview").getValue());
                        reviewsList.add(rev);
                    }
                }
                adapterListReviews = new AdapterListReviews(getActivity(), reviewsList);
                recyclerViewListReviews.setAdapter(adapterListReviews);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

