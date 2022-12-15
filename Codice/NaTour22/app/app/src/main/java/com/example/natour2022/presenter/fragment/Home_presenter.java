package com.example.natour2022.presenter.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Explore_fragment;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.Plan_fragment;
import com.example.natour2022.view.fragment.Profile_fragment;
import com.example.natour2022.view.fragment.SingIn_fragment;
import com.google.firebase.auth.FirebaseAuth;

public class Home_presenter {

    private Home_fragment home_fragment;
    private StartActivity startActivity;

    public Home_presenter(Home_fragment homeFragment) {
        this.home_fragment = homeFragment;
    }

    public void onButtonExploreClicked() {
        Fragment fragment = new Explore_fragment(startActivity);
        FragmentManager fragmentManager = home_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonPlanClicked() {
        Fragment fragment = new Plan_fragment(startActivity);
        FragmentManager fragmentManager = home_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonBackClicked(View v) {
           AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
           alert.setTitle("LogOut");
           alert.setMessage("Do you really want to quit NaTour22?");
           alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   FirebaseAuth.getInstance().signOut();
                   Intent intent = new Intent(home_fragment.getActivity(), StartActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                   home_fragment.startActivity(intent);
                   home_fragment.getActivity().finish();
               }
           });
           alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
               }
           });
           alert.create().show();
    }

    public void onButtonBackHomeClicked() {
        Fragment fragment = new SingIn_fragment(startActivity);
        FragmentManager fragmentManager = home_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonProfileHomeClicked() {
        Fragment fragment = new Profile_fragment(startActivity);
        FragmentManager fragmentManager = home_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }
}
