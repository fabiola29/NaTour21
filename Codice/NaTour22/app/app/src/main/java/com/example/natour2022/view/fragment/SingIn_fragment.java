package com.example.natour2022.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.natour2022.R;
import com.example.natour2022.presenter.fragment.SingIn_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class SingIn_fragment extends Fragment {

    private StartActivity startActivity;
    private SingIn_presenter singIn_presenter;
    private TextInputLayout email;
    private TextInputLayout password;
    private Button buttonSignIn;
    private ImageButton buttonBackSingIn;
    public ProgressBar progressBar;


    public SingIn_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sing_in, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.fragment_sign_in_text_input_email_layout);
        password = view.findViewById(R.id.fragment_sign_in_text_input_password_layout);
        buttonSignIn = view.findViewById(R.id.fragment_sign_in_button_sign_in);
        buttonBackSingIn = view.findViewById(R.id.btBack_singIn);
        progressBar = view.findViewById(R.id.progressBar_SingIn);
        singIn_presenter = new SingIn_presenter(this);
        listenToClickEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void listenToClickEvents() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn_presenter.onButtonSignInClicked();
            }
        });
        buttonBackSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void cleanTextInputErrors() {
        email.setErrorEnabled(false);
        password.setErrorEnabled(false);
    }

    public String getEmail() {
        EditText emailEditText = email.getEditText();
        if (emailEditText == null) return "";
        return emailEditText.getText().toString();
    }

    public String getPassword() {
        EditText passwordEditText = password.getEditText();
        if (passwordEditText == null) return "";
        return passwordEditText.getText().toString();
    }

    public void setTextInputEmailError(String error) {
        email.setErrorEnabled(true);
        email.setError(error);
    }

    public void setTextInputPasswordError(String error) {
        password.setErrorEnabled(true);
        password.setError(error);
    }

    public void setEnabledButtonSignIn(boolean enabled) {
        buttonSignIn.setEnabled(enabled);
    }

}


