package com.example.natour2022.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.natour2022.R;
import com.example.natour2022.model.User;
import com.example.natour2022.presenter.fragment.Profile_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Profile_fragment  extends Fragment {

    private StartActivity startActivity;
    private Profile_presenter profile_presenter;
    private ImageButton buttonBackProfile;
    private Button buttonDeleteUser;
    private TextView email_Itinerary;
    private TextView nickname_Itinerary;
    private TextView birth_Itinerary;
    private TextView gender_Itinerary;
    private ProgressBar progressBar_Profile;
    private User user;
    private FirebaseAuth auth;
    private String email, nickname, birth, gender;


    public Profile_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonBackProfile = view.findViewById(R.id.btBack_profile);
        email_Itinerary = view.findViewById(R.id.email_Itinerary);
        nickname_Itinerary = view.findViewById(R.id.nickname_Itinerary);
        birth_Itinerary = view.findViewById(R.id.birth_Itinerary);
        buttonDeleteUser = view.findViewById(R.id.fragment_profile_button_deleteUser);
        gender_Itinerary = view.findViewById(R.id.gender_Itinerary);
        progressBar_Profile = view.findViewById(R.id.progressBar_Profile);
        profile_presenter = new Profile_presenter(this);
        listenToClickEvents();

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(getContext(), "Something went wrong! user's details are not avaible at the moment", Toast.LENGTH_SHORT).show();
        }else {
            progressBar_Profile.setVisibility(View.VISIBLE);
            loadUserDate(firebaseUser);
        }
    }

    private void listenToClickEvents() {
        buttonBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_presenter.onButtonBackProfileClicked();
            }
        });
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_presenter.onButtonDeleteUserClicked(v);
            }
        });
    }

    private void loadUserDate(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        DatabaseReference referenceDb = FirebaseDatabase.getInstance().getReference("Users");
        referenceDb.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    email = firebaseUser.getEmail();
                    nickname = user.getNickName();
                    birth = user.getDataOfBirth();
                    gender = user.getGender();

                    email_Itinerary.setText(email);
                    nickname_Itinerary.setText(user.getNickName());
                    birth_Itinerary.setText(user.getDataOfBirth());
                    gender_Itinerary.setText(user.getGender());

                }
                progressBar_Profile.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                progressBar_Profile.setVisibility(View.GONE);
            }
        });
    }

}
