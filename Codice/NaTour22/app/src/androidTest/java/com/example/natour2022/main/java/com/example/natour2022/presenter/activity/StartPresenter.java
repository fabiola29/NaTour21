package com.example.natour2022.presenter.activity;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.natour2022.R;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.SingUp_fragment;
import com.example.natour2022.view.fragment.SingIn_fragment;


public class StartPresenter {

    private final StartActivity startActivity;

    public StartPresenter(final StartActivity mainActivity, boolean openSignIn) {
        this.startActivity = mainActivity;
        if(openSignIn) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openFragment(new SingIn_fragment(mainActivity));
                }
            }, 500);
        }
    }

    public void onButtonSignInClicked() {
        openFragment(new SingIn_fragment(startActivity));
    }

    public void onButtonSignUpClicked() {
        openFragment(new SingUp_fragment(startActivity));
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = startActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.add(R.id.activity_main, fragment).commit();
    }

    public void openHomeFragment() {
        openFragment(new Home_fragment(startActivity));
    }

}