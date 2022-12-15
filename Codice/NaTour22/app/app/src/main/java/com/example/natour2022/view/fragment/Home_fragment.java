package com.example.natour2022.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.natour2022.presenter.fragment.Home_presenter;
import org.jetbrains.annotations.NotNull;

import com.example.natour2022.R;
import com.example.natour2022.view.activity.StartActivity;

public class Home_fragment extends Fragment {

    private StartActivity startActivity;
    private Home_presenter home_presenter;
    private CardView buttonExplore;
    private CardView buttonPlan;
    private CardView buttonLogOut;
    private CardView buttonProfile;
    private ImageButton buttonBackHome;

    public Home_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonExplore = view.findViewById(R.id.btExploreHome);
        buttonPlan = view.findViewById(R.id.btPlanHome);
        buttonLogOut = view.findViewById(R.id.btUscitaHome);
        buttonBackHome = view.findViewById(R.id.btBack_Home);
        buttonProfile = view.findViewById(R.id.btProfileHome);
        home_presenter = new Home_presenter(this);
        listenToClickEvents();
    }

    private void listenToClickEvents() {
        buttonExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_presenter.onButtonExploreClicked();
            }
        });
        buttonPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_presenter.onButtonPlanClicked();
            }
        });
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_presenter.onButtonBackClicked(v);
            }
        });
        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_presenter.onButtonBackHomeClicked();
            }
        });
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_presenter.onButtonProfileHomeClicked();
            }
        });
    }
}


