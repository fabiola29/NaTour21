package com.example.natour2022.presenter.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.Profile_fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile_presenter {

    private StartActivity startActivity;
    private Profile_fragment profile_fragment;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


    public Profile_presenter(Profile_fragment profile_fragment) {
        this.profile_fragment = profile_fragment;
    }

    public void onButtonBackProfileClicked() {
        Fragment fragment = new Home_fragment(startActivity);
        FragmentManager fragmentManager = profile_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonDeleteUserClicked(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
        alert.setTitle("Delete User");
        alert.setMessage("Do you really want to delete User?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUser();
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

    private void deleteUser() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(profile_fragment.getActivity(), "User has been deleted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profile_fragment.getActivity(), StartActivity.class);
                profile_fragment.getContext().startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile_fragment.getActivity(), "User deletion failed, please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

