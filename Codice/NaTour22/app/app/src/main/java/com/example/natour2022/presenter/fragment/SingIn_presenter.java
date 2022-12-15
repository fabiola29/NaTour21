package com.example.natour2022.presenter.fragment;

import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.utils.exception.NoInternetConnectionException;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.SingIn_fragment;
import com.example.natour2022.widgtes.toast.MotionToast;
import com.example.natour2022.widgtes.toast.MotionToastType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.regex.Pattern;

public class SingIn_presenter {

    private SingIn_fragment singIn_fragment;
    private FirebaseAuth auth;
    private StartActivity startActivity;


    public SingIn_presenter(SingIn_fragment signInFragment) {
        this.singIn_fragment = signInFragment;
    }

    public void onButtonSignInClicked() {
        Resources resources = singIn_fragment.getStartActivity().getResources();
        singIn_fragment.setEnabledButtonSignIn(false);
        singIn_fragment.cleanTextInputErrors();
        String email = singIn_fragment.getEmail();
        String password = singIn_fragment.getPassword();
        if (email.length() == 0) {
            singIn_fragment.setTextInputEmailError(
                    resources.getString(R.string.fragment_sign_in_enter_email)
            );
            singIn_fragment.setEnabledButtonSignIn(true);
        } else if (!isValidEmail(email)) {
            singIn_fragment.setTextInputEmailError(
                    resources.getString(R.string.fragment_sign_in_invalid_email)
            );
            singIn_fragment.setEnabledButtonSignIn(true);
        } else if (password.length() == 0) {
            singIn_fragment.setTextInputPasswordError(
                    resources.getString(R.string.fragment_sign_in_enter_pass)
            );
            singIn_fragment.setEnabledButtonSignIn(true);
        } else if (!isValidPassword(password)) {
            singIn_fragment.setTextInputPasswordError(
                    resources.getString(R.string.fragment_sign_in_format_password)
            );
            singIn_fragment.setEnabledButtonSignIn(true);
        } else if (!isValidEmail(email) && !isValidPassword(password)) {
            singIn_fragment.setTextInputPasswordError(
                    resources.getString(R.string.fragment_sign_in_format_password)
            );
            singIn_fragment.setEnabledButtonSignIn(true);
        } else {
            singIn_fragment.progressBar.setVisibility(View.VISIBLE);
            signIn(email, password);
        }
    }

    private boolean isValidEmail (String email){
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    private boolean isValidPassword (String password){
            Pattern PASSWORD_PATTERN
                    = Pattern.compile(
                    "^(?=[A-Z0-9]*[a-z])(?=[a-zA-Z]*[0-9])(?=[a-z0-9]*[A-Z])[a-zA-Z0-9]{8,}$");

               /*
             ^ : inizio stringa
            (?=[A-Z0-9]*[a-z]) : guarda avanti per assicurarci almeno un carattere minuscolo
            (?=[a-zA-Z]*[0-9]) : guarda avanti per assicurarci almeno un cifra
            (?=[a-z0-9]*[A-Z]) : guarda avanti per assicurarci almeno un carattere maiuscolo
               [a-zA-Z0-9]{8,} : acquisisce otto p più caratteri alfanumerici
                             $ : corrisponde alla fine della stringa
        */

            return PASSWORD_PATTERN.matcher(password).matches();
        }

    public void openHomeFragment() {
            Fragment fragment = new Home_fragment(startActivity);
            FragmentManager fragmentManager = singIn_fragment.getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.activity_main, fragment).commit();
        }

    private void signIn(String email, String password) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(singIn_fragment.getActivity(), "You are logged it now", Toast.LENGTH_SHORT).show();
                            openHomeFragment();
                        } else {
                            singIn_fragment.cleanTextInputErrors();
                            try {
                                throw task.getException();
                            } catch (NoInternetConnectionException e) {
                                MotionToast.display(
                                        singIn_fragment.getStartActivity(),
                                        R.string.toast_warning_internet_connection,
                                        MotionToastType.WARNING_MOTION_TOAST
                                );
                            } catch (FirebaseAuthInvalidUserException e) {
                                MotionToast.display(
                                        singIn_fragment.getStartActivity(),
                                        R.string.toast_error_user_not_exists,
                                        MotionToastType.ERROR_MOTION_TOAST
                                );
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                MotionToast.display(
                                        singIn_fragment.getStartActivity(),
                                        R.string.toast_error_credentials_err,
                                        MotionToastType.ERROR_MOTION_TOAST
                                );
                            } catch (Exception e) {
                                MotionToast.display(
                                        singIn_fragment.getStartActivity(),
                                        R.string.toast_error_unknown_error,
                                        MotionToastType.ERROR_MOTION_TOAST
                                );
                            }
                        }
                        singIn_fragment.progressBar.setVisibility(View.GONE);
                        singIn_fragment.setEnabledButtonSignIn(true);
                    }
                });
    }
}

