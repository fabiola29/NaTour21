package com.example.natour2022.presenter.fragment;

import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.dao.UserDao;
import com.example.natour2022.model.User;
import com.example.natour2022.utils.exception.NoInternetConnectionException;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.SingUp_fragment;
import com.example.natour2022.widgtes.toast.MotionToast;
import com.example.natour2022.widgtes.toast.MotionToastType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SingUp_presenter {

    private final SingUp_fragment singUp_fragment;
    private StartActivity startActivity;
    private FirebaseAuth auth;
    private DatabaseReference referenceDb;


    public SingUp_presenter(SingUp_fragment singUpFragment) {
        this.singUp_fragment = singUpFragment;
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        /*
             ^ : inizio stringa
            (?=[A-Z0-9]*[a-z]) : guarda avanti per assicurarci almeno un carattere minuscolo
            (?=[a-zA-Z]*[0-9]) : guarda avanti per assicurarci almeno un cifra
            (?=[a-z0-9]*[A-Z]) : guarda avanti per assicurarci almeno un carattere maiuscolo
               [a-zA-Z0-9]{8,} : acquisisce otto p più caratteri alfanumerici
                             $ : corrisponde alla fine della stringa
        */
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "^(?=[A-Z0-9]*[a-z])(?=[a-zA-Z]*[0-9])(?=[a-z0-9]*[A-Z])[a-zA-Z0-9]{8,}$");

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public void onButtonSignUpClicked() {
        Resources resources = singUp_fragment.getStartActivity().getResources();
        singUp_fragment.setEnabledButtonSignUp(false);
        singUp_fragment.cleanTextInputErrors();
        String email = singUp_fragment.getEmail();
        String password = singUp_fragment.getPassword();
        String nickname = singUp_fragment.getNickname();
        String birth = singUp_fragment.getBirth();
        String gender = singUp_fragment.getGender();
        if (nickname.length() == 0) {
            singUp_fragment.setTextInputNicknameError(
                    resources.getString(R.string.fragment_sign_up_enter_nickname)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (email.length() == 0) {
            singUp_fragment.setTextInputEmailError(
                    resources.getString(R.string.fragment_sign_up_enter_email)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (!isValidEmail(email)) {
            singUp_fragment.setTextInputEmailError(
                    resources.getString(R.string.fragment_sign_up_invalid_format_email)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (password.length() == 0) {
            singUp_fragment.setTextInputPasswordError(
                    resources.getString(R.string.fragment_sign_up_enter_password)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (!isValidPassword(password)) {
            singUp_fragment.setTextInputPasswordError(
                    resources.getString(R.string.fragment_sign_up_format_password)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (birth.length() == 0) {
            singUp_fragment.setTextInputBirthError(
                    resources.getString(R.string.fragment_sign_up_enter_birth)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (gender.length() == 0) {
            singUp_fragment.setTextInputBirthError(
                    resources.getString(R.string.fragment_sign_up_enter_gender)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else if (!(gender.equals("Female")||gender.equals("Male") ||gender.equals("female") || gender.equals("male") )) {
            singUp_fragment.setTextInputBirthError(
                    resources.getString(R.string.fragment_sign_up_invalid_format_gender)
            );
            singUp_fragment.setEnabledButtonSignUp(true);
        } else {
            singUp_fragment.progressBar.setVisibility(View.VISIBLE);
            signUp(nickname, email, password, birth, gender);
        }
    }

    public void openHomeFragment() {
        Fragment fragment = new Home_fragment(startActivity);
        FragmentManager fragmentManager = singUp_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void signUp(String nickname, String email, String password,String dataOfBirth, String gender){
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            UserDao userDao = new UserDao();
                            User currUser = new User(uid, nickname, email, password, dataOfBirth, gender);

                            referenceDb.child("Users").child(uid).setValue(currUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                       Toast.makeText(singUp_fragment.getActivity(), "SingUp successful", Toast.LENGTH_SHORT).show();
                                        openHomeFragment();
                                    } else {
                                        Toast.makeText(singUp_fragment.getActivity(), "SingUp failed. Please try again", Toast.LENGTH_SHORT).show();
                                        singUp_fragment.progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Resources resources = singUp_fragment.getStartActivity().getResources();
                            singUp_fragment.cleanTextInputErrors();
                            try {
                                throw task.getException();
                            } catch (NoInternetConnectionException e) {
                                MotionToast.display(
                                        singUp_fragment.getStartActivity(),
                                        R.string.toast_warning_internet_connection,
                                        MotionToastType.WARNING_MOTION_TOAST
                                );
                            } catch (FirebaseAuthInvalidUserException e) {
                                singUp_fragment.setTextInputNicknameError(
                                        resources.getString(R.string.fragment_sign_up_invalid_nickname)
                                );
                            } catch (FirebaseAuthUserCollisionException e) {
                                singUp_fragment.setTextInputEmailError(
                                        resources.getString(R.string.fragment_sign_up_invalid_login)
                                );
                            } catch (Exception e) {
                                MotionToast.display(
                                        singUp_fragment.getStartActivity(),
                                        R.string.toast_error_unknown_error,
                                        MotionToastType.ERROR_MOTION_TOAST
                                );
                            }
                        }
                        singUp_fragment.progressBar.setVisibility(View.GONE);
                        singUp_fragment.setEnabledButtonSignUp(true);
                    }
                });
    }
}