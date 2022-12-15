package com.example.natour2022.presenter.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Explore_fragment;
import com.example.natour2022.view.fragment.ViewReviews_fragment;

public class ViewReviews_presenter {

    private ViewReviews_fragment viewReviews_fragment;
    private StartActivity startActivity;


    public ViewReviews_presenter(ViewReviews_fragment viewReviews_fragment) {
        this.viewReviews_fragment = viewReviews_fragment;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    public void onButtonBackViewReviewsClicked() {
        Fragment fragment = new Explore_fragment(startActivity);
        FragmentManager fragmentManager = viewReviews_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }
}
