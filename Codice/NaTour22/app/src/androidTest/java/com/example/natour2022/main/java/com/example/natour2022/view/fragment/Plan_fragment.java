package com.example.natour2022.view.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.natour2022.R;
import com.example.natour2022.presenter.fragment.Plan_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

// VA INSERITO IL PERCORSO TRA DUE PUNTI SULLA MAPPA
public class Plan_fragment extends Fragment {

    private StartActivity startActivity;
    private Plan_presenter plan_presenter;
    private TextInputLayout titleItinerary;
    private TextInputLayout descriptionItinerary;
    private TextInputLayout difficultyLevel;
    private TextInputLayout hoursItinerary;
    private TextInputLayout minutesItinerary;
    private TextInputLayout secondsItinerary;
    private ImageView addImage;
    private Button buttonCancel;
    private Button buttonConferm;
    private ImageButton buttonBackPlan;

    private static final float DEFAULT_ZOOM = 0f;
    private static final LatLng latLng = new LatLng(31.0062518, 44.5572678);
    private LatLng initialCameraFocus = latLng;

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;

    public Plan_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleItinerary = view.findViewById(R.id.fragment_plan_text_input_name_Itinerary_layout);
        descriptionItinerary = view.findViewById(R.id.fragment_plan_text_description_itinerary_layout);
        difficultyLevel = view.findViewById(R.id.fragment_plan_text_input_difficulty_itinerary_layout);
        hoursItinerary = view.findViewById(R.id.fragment_plan_text_input_hours_itinerary_layout);
        minutesItinerary = view.findViewById(R.id.fragment_plan_text_input_minutes_itinerary_layout);
        secondsItinerary = view.findViewById(R.id.fragment_plan_text_input_seconds_itinerary_layout);
        addImage = view.findViewById(R.id.fragment_plan_imageView_ImportImage);
        buttonCancel = view.findViewById(R.id.fragment_plan_button_cancel);
        buttonConferm = view.findViewById(R.id.fragment_plan_button_conferm);
        buttonBackPlan = view.findViewById(R.id.btBack_Plan);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map_container);

        initializeMap();

        plan_presenter = new Plan_presenter(this);
        listenToClickEvents();
        listenToTextChangedEvents();
    }

    private void listenToTextChangedEvents() {
        EditText title = titleItinerary.getEditText();
        EditText description = descriptionItinerary.getEditText();
        if (title != null) {
            title.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    plan_presenter.onTitleChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
        }
        if (description != null) {
            description.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    plan_presenter.onDescriptionChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
        }
    }

    private void listenToClickEvents() {
        buttonConferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_presenter.onButtonPublicClicked();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_presenter.onButtonCancelClicked();
            }
        });
        buttonBackPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_presenter.onButtonBackPlanClicked();
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_presenter.onButtonFirstImageClicked();
            }
        });
    }

    public void cleanTextInputErrors() {
        titleItinerary.setErrorEnabled(false);
        descriptionItinerary.setErrorEnabled(false);
        difficultyLevel.setErrorEnabled(false);
        hoursItinerary.setErrorEnabled(false);
        minutesItinerary.setErrorEnabled(false);
        secondsItinerary.setErrorEnabled(false);
    }

    public String getTitleItinerary() {
        EditText nameEditText = titleItinerary.getEditText();
        if (nameEditText == null) return "";
        return nameEditText.getText().toString();
    }

    public String getDescriptionItinerary() {
        EditText descriptionEditText = descriptionItinerary.getEditText();
        if (descriptionEditText == null) return "";
        return descriptionEditText.getText().toString();
    }

    public String getDifficultyLevel() {
        EditText difficultyLevelEditText = difficultyLevel.getEditText();
        if (difficultyLevelEditText == null) return "";
        return difficultyLevelEditText.getText().toString();
    }

    public String getHoursItinerary() {
        EditText hoursEditText = hoursItinerary.getEditText();
        if (hoursEditText == null) return "";
        return hoursEditText.getText().toString();
    }

    public String getMinutesItinerary() {
        EditText minutesEditText = minutesItinerary.getEditText();
        if (minutesEditText == null) return "";
        return minutesEditText.getText().toString();
    }

    public String getSecondsItinerary() {
        EditText secondsEditText = secondsItinerary.getEditText();
        if (secondsEditText == null) return "";
        return secondsEditText.getText().toString();
    }

    public void setTextInputNameItineraryError(String error) {
        titleItinerary.setErrorEnabled(true);
        titleItinerary.setError(error);
    }

    public void setTextInputDescriptionItineraryError(String error) {
        descriptionItinerary.setErrorEnabled(true);
        descriptionItinerary.setError(error);
    }

    public void setTextInputDifficultyLevelError(String error) {
        difficultyLevel.setErrorEnabled(true);
        difficultyLevel.setError(error);
    }

    public void setTextInputHoursItineraryError(String error) {
        hoursItinerary.setErrorEnabled(true);
        hoursItinerary.setError(error);
    }

    public void setTextInputMinutesItineraryError(String error) {
        minutesItinerary.setErrorEnabled(true);
        minutesItinerary.setError(error);
    }

    public void setTextInputSecondsItineraryError(String error) {
        secondsItinerary.setErrorEnabled(true);
        secondsItinerary.setError(error);
    }

    public void setCounterEnabledTextInputTitle(boolean enabled) {
        titleItinerary.setCounterEnabled(enabled);
    }

    public void setCounterEnabledTextInputDescription(boolean enabled) {
        descriptionItinerary.setCounterEnabled(enabled);
    }

    public void setFirstImagePreview(Drawable drawable) {
        addImage.setBackground(drawable);
    }

    public void setVisibilityFirstImageBox(boolean visibility) {
        if (visibility) {
            addImage.setImageDrawable(ContextCompat.getDrawable(
                            getActivity(),
                            R.drawable.ic_image_box
                    )
            );
        } else {
            addImage.setImageDrawable(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        plan_presenter.onActivityResult(requestCode, resultCode, data);
    }

    public void setEnabledButtonPublic(boolean enabled) {
        buttonConferm.setEnabled(enabled);
    }

    private void initializeMap() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setBuildingsEnabled(true);
                googleMap.setMinZoomPreference(DEFAULT_ZOOM);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialCameraFocus));
            }
        });
    }

}


