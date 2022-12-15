package com.example.natour2022.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.natour2022.R;
import com.example.natour2022.model.Itinerary;
import com.example.natour2022.presenter.fragment.DetailsItinerary_presenter;
import com.example.natour2022.view.activity.StartActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


public class DetailsItinerary_fragment extends Fragment {

    private StartActivity startActivity;
    private DetailsItinerary_presenter detailsItinerary_presenter;

    private ImageButton buttonBackDetailsItinerary;
    private Button buttonInsertReviews;
    private Button buttonViewsReviews;
    private TextView title_details_itinerary;
    private TextView hours_details_Itinerary;
    private TextView minutes_details_Itinerary;
    private TextView seconds_details_Itinerary;
    private TextView difficultLivel_details_Itinerary;
    private TextView description_detailsItinerary;
    private ImageView imageView_details_itinerary;
    private ProgressBar progressBar_details_itinerary;

    private Itinerary itinerary;

    private static final float DEFAULT_ZOOM = 0f;
    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;

    private static final int REQUEST_CODE = 101;

    private static final LatLng latLng = new LatLng(31.0062518, 44.5572678);
    private LatLng initialCameraFocus = latLng;

    public DetailsItinerary_fragment(StartActivity startActivity) {
        this.startActivity = startActivity;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_itinerary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonBackDetailsItinerary = view.findViewById(R.id.back_detailsItinerary);
        buttonInsertReviews = view.findViewById(R.id.button_insert_review);
        buttonViewsReviews = view.findViewById(R.id.button_views_review);
        title_details_itinerary = view.findViewById(R.id.title_details_itinerary);
        hours_details_Itinerary = view.findViewById(R.id.hours_details_Itinerary);
        minutes_details_Itinerary = view.findViewById(R.id.minutes_details_Itinerary);
        difficultLivel_details_Itinerary = view.findViewById(R.id.difficultLivel_details_Itinerary);
        description_detailsItinerary = view.findViewById(R.id.description_detailsItinerary);
        seconds_details_Itinerary = view.findViewById(R.id.seconds_details_Itinerary);
        imageView_details_itinerary = view.findViewById(R.id.imageView_details_itinerary);
        progressBar_details_itinerary = view.findViewById(R.id.progressBar_details_itinerary);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapview);

        initializeMap();

        detailsItinerary_presenter = new DetailsItinerary_presenter(this);

        listenToClickEvents();
        loadItineraryDetails();
    }


    private void listenToClickEvents() {
        buttonBackDetailsItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsItinerary_presenter.onButtonBackDetailsItineraryClicked();
            }
        });
        buttonInsertReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsItinerary_presenter.onButtonInsertReviewsClicked(itinerary);
            }
        });
        buttonViewsReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsItinerary_presenter.onButtonViewsReviewsClicked(itinerary);
            }
        });
    }

    private void loadItineraryDetails() {
        title_details_itinerary.setText(itinerary.getTitleItinerary());
        hours_details_Itinerary.setText(String.valueOf(itinerary.getHours()));
        minutes_details_Itinerary.setText(String.valueOf(itinerary.getMinutes()));
        seconds_details_Itinerary.setText(String.valueOf(itinerary.getSeconds()));
        description_detailsItinerary.setText(String.valueOf(itinerary.getDescriptionItinerary()));
        difficultLivel_details_Itinerary.setText(itinerary.getDifficultyLevel());
        progressBar_details_itinerary.setVisibility(View.VISIBLE);
        try {
            Picasso.get().load(itinerary.getImage_itinerary())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView_details_itinerary, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar_details_itinerary.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getActivity(), "Impossibile caricare una delle immagini" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar_details_itinerary.setVisibility(View.GONE);
                        }
                    });

        } catch (Exception e) {
            //gestisci errore mettendo una immagine di default
        }
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

                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                String provider = locationManager.getBestProvider(criteria, true);

                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location myLocation = locationManager.getLastKnownLocation(provider);

                double latitude = myLocation.getLatitude();
                // Get longitude of the current location
                double longitude = myLocation.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Me"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(initialCameraFocus));

                if (googleMap != null) {
                    if (ActivityCompat.checkSelfPermission(requireContext().getApplicationContext()
                            , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext().getApplicationContext()
                            , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                       ActivityCompat.requestPermissions(requireActivity(), new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                        return;
                    }

                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                }
            }
        });
    }
}


