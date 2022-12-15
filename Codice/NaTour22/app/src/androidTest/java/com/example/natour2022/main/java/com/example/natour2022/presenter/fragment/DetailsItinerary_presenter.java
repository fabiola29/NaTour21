package com.example.natour2022.presenter.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.model.Itinerary;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.DetailsItinerary_fragment;
import com.example.natour2022.view.fragment.Explore_fragment;
import com.example.natour2022.view.fragment.InsertReviews_fragment;
import com.example.natour2022.view.fragment.ViewReviews_fragment;


public class DetailsItinerary_presenter {

    private StartActivity startActivity;
    private final DetailsItinerary_fragment detailsItinerary_fragment;

    public DetailsItinerary_presenter(DetailsItinerary_fragment detailsItinerary_fragment) {
        this.detailsItinerary_fragment = detailsItinerary_fragment;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    public void onButtonBackDetailsItineraryClicked() {
        Fragment fragment = new Explore_fragment(startActivity);
        FragmentManager fragmentManager = detailsItinerary_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonInsertReviewsClicked(Itinerary itinerary) {
        InsertReviews_fragment fragment = new InsertReviews_fragment(startActivity,itinerary);
        FragmentManager fragmentManager = detailsItinerary_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonViewsReviewsClicked(Itinerary itinerary) {
        Fragment fragment = new ViewReviews_fragment(startActivity,itinerary.getItineraryId());
        FragmentManager fragmentManager = detailsItinerary_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

}
