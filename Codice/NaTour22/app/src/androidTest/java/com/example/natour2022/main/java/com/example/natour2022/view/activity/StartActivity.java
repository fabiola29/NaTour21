package com.example.natour2022.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.natour2022.R;
import com.example.natour2022.presenter.activity.StartPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private StartPresenter startPresenter;
    private Button buttonSignUp;
    private Button buttonSignIn;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignIn = findViewById(R.id.activity_start_button_sign_in);
        buttonSignUp = findViewById(R.id.activity_start_button_sign_up);

        if (getIntent().getBooleanExtra("SIGN_IN", false)) {
            startPresenter = new StartPresenter(this, true);
        } else {
            startPresenter = new StartPresenter(this, false);
        }

        //LOGIN RIMANE SEMPRE ATTIVO ANCHE SE SI RIAPRE L'APP, VA FATTO SOLO SE SI EFFETTUA IL LOGOUT
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            startPresenter.openHomeFragment();
            return;
        }

        listenToClickEvents();
    }

    private void listenToClickEvents() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPresenter.onButtonSignInClicked();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPresenter.onButtonSignUpClicked();
            }
        });
    }

}


