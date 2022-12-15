package com.example.natour2022.presenter.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;

import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Explore_fragment;
import com.example.natour2022.view.fragment.Home_fragment;

public class Explore_presenter {

    private StartActivity startActivity;
    private final Explore_fragment explore_fragment;

    public Explore_presenter(Explore_fragment explore_fragment) {
        this.explore_fragment = explore_fragment;
    }

    public void onButtonBackExploreClicked() {
        Fragment fragment = new Home_fragment(startActivity);
        FragmentManager fragmentManager = explore_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }
}

