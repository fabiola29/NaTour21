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
import com.example.natour2022.presenter.fragment.SingUp_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;


public class SingUp_fragment extends Fragment {

    private StartActivity startActivity;
    private SingUp_presenter singUp_presenter;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout nickname;
    private Button buttonSignUp;
    private TextInputLayout birth;
    private TextInputLayout gender;
    public  ProgressBar progressBar;
    private ImageButton buttonBackSingUp;


    public SingUp_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sing_up, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.fragment_sign_up_text_input_email_layout);
        password = view.findViewById(R.id.fragment_sign_up_text_input_password_layout);
        nickname = view.findViewById(R.id.fragment_sign_up_text_input_nickname_layout);
        birth = view.findViewById(R.id.fragment_sign_up_text_input_birth_layout);
        gender =  view.findViewById(R.id.fragment_sign_up_text_input_gender_layout);
        buttonSignUp = view.findViewById(R.id.fragment_sign_up_button_sign_up);
        progressBar = view.findViewById(R.id.progressBar_SingUp);
        buttonBackSingUp = view.findViewById(R.id.btBack_singUp);
        singUp_presenter = new SingUp_presenter(this);

        listenToClickEvents();
    }

    private void listenToClickEvents() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp_presenter.onButtonSignUpClicked();
            }
        });
        buttonBackSingUp.setOnClickListener(new View.OnClickListener() {
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
        nickname.setErrorEnabled(false);
        birth.setErrorEnabled(false);
    }

    public String getEmail() {
        EditText emailEditText = email.getEditText();
        if(emailEditText == null) return "";
        return emailEditText.getText().toString();
    }

    public String getNickname() {
        EditText nicknameEditText = nickname.getEditText();
        if(nicknameEditText == null) return "";
        return nicknameEditText.getText().toString();
    }

    public String getPassword() {
        EditText passwordEditText = password.getEditText();
        if(passwordEditText == null) return "";
        return passwordEditText.getText().toString();
    }

    public String getBirth() {
        EditText birthEditText = birth.getEditText();
        if(birthEditText == null) return "";
        return birthEditText.getText().toString();
    }

    public String getGender() {
        EditText genderEditText = gender.getEditText();
        if(genderEditText == null) return "";
        return genderEditText.getText().toString();
    }

    public void setTextInputEmailError(String error) {
        email.setErrorEnabled(true);
        email.setError(error);
    }

    public void setTextInputBirthError(String error) {
        birth.setErrorEnabled(true);
        birth.setError(error);
    }

    public void setTextInputNicknameError(String error) {
        nickname.setErrorEnabled(true);
        nickname.setError(error);
    }

    public void setTextInputPasswordError(String error) {
        password.setErrorEnabled(true);
        password.setError(error);
    }

    public void setEnabledButtonSignUp(boolean enabled) {
        buttonSignUp.setEnabled(enabled);
    }
}