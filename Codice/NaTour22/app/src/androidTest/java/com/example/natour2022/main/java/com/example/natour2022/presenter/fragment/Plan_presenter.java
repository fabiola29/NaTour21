package com.example.natour2022.presenter.fragment;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2022.R;
import com.example.natour2022.dao.ItineraryDao;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Home_fragment;
import com.example.natour2022.view.fragment.Plan_fragment;
import com.example.natour2022.widgtes.toast.MotionToast;
import com.example.natour2022.widgtes.toast.MotionToastType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class Plan_presenter {

    private final Plan_fragment plan_fragment;
    private StartActivity startActivity;
    private DatabaseReference referenceDb;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    private Uri images;
    private static final int MEDIA_IMAGE_REQUEST_CODE = 1;


    public Plan_presenter(Plan_fragment planFragment) {
        this.plan_fragment = planFragment;
    }

    public void onButtonCancelClicked() {
        Fragment fragment = new Plan_fragment(startActivity);
        FragmentManager fragmentManager = plan_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonBackPlanClicked() {
        Fragment fragment = new Home_fragment(startActivity);
        FragmentManager fragmentManager = plan_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonPublicClicked() {
        Resources resources = plan_fragment.getActivity().getResources();
        plan_fragment.setEnabledButtonPublic(false);
        plan_fragment.cleanTextInputErrors();
        String titleItinerary = plan_fragment.getTitleItinerary();
        String descriptionItinerary = plan_fragment.getDescriptionItinerary();
        String difficultyLevel = plan_fragment.getDifficultyLevel();
        String hours = String.valueOf(plan_fragment.getHoursItinerary());
        String minutes = String.valueOf(plan_fragment.getMinutesItinerary());
        String seconds = String.valueOf(plan_fragment.getSecondsItinerary());
        if (titleItinerary.length() == 0) {
            plan_fragment.setTextInputNameItineraryError(
                    resources.getString(R.string.fragment_plan_enter_name_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (descriptionItinerary.length() == 0) {
            plan_fragment.setTextInputDescriptionItineraryError(
                    resources.getString(R.string.fragment_plan_enter_description_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (difficultyLevel.length() == 0) {
            plan_fragment.setTextInputDifficultyLevelError(
                    resources.getString(R.string.fragment_plan_enter_difficultyLevel_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (!(difficultyLevel.equals("medium") || difficultyLevel.equals("Medium")
                || difficultyLevel.equals("difficult") || difficultyLevel.equals("Difficult")
                || difficultyLevel.equals("simple") || difficultyLevel.equals("Simple"))) {
            plan_fragment.setTextInputDifficultyLevelError(
                    resources.getString(R.string.fragment_plan_enter_difficultyLevel_itinerary_choice)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (hours.length() == 0) {
            plan_fragment.setTextInputHoursItineraryError(
                    resources.getString(R.string.fragment_plan_enter_hours_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (minutes.length() == 0) {
            plan_fragment.setTextInputMinutesItineraryError(
                    resources.getString(R.string.fragment_plan_enter_minutes_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (seconds.length() == 0) {
            plan_fragment.setTextInputSecondsItineraryError(
                    resources.getString(R.string.fragment_plan_enter_second_itinerary)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (Integer.valueOf(hours) > 99) {
            plan_fragment.setTextInputHoursItineraryError(
                    resources.getString(R.string.fragment_plan_enter_format_hours_correct)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (Integer.valueOf(minutes) > 59) {
            plan_fragment.setTextInputMinutesItineraryError(
                    resources.getString(R.string.fragment_plan_enter_format_minutes_correct)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if (Integer.valueOf(seconds) > 59) {
            plan_fragment.setTextInputSecondsItineraryError(
                    resources.getString(R.string.fragment_plan_enter_format_seconds_correct)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if(titleItinerary.length() < 30) {
            plan_fragment.setTextInputNameItineraryError(
                    resources.getString(R.string.fragment_plan_itinerary_invalid_title)
            );
            plan_fragment.setEnabledButtonPublic(true);
        } else if(descriptionItinerary.length() < 150) {
            plan_fragment.setTextInputNameItineraryError(
                    resources.getString(R.string.fragment_plan_itinerary_invalid_description)
            );
            plan_fragment.setEnabledButtonPublic(true);
        }else {
            addItinerary();
        }
    }

    public void onTitleChanged() {
        if(plan_fragment.getTitleItinerary().length() >= 30) {
            plan_fragment.setCounterEnabledTextInputTitle(false);
        } else {
            plan_fragment.setCounterEnabledTextInputTitle(true);
        }
    }

    public void onDescriptionChanged() {
        if(plan_fragment.getDescriptionItinerary().length() >= 150) {
            plan_fragment.setCounterEnabledTextInputDescription(false);
        } else {
            plan_fragment.setCounterEnabledTextInputDescription(true);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Drawable preview = getPreview(imageUri);
            if (preview == null) {
                MotionToast.display(
                        plan_fragment.getActivity(),
                        R.string.toast_error_unknown_error,
                        MotionToastType.ERROR_MOTION_TOAST
                );
            } else {
                switch (requestCode) {
                    case MEDIA_IMAGE_REQUEST_CODE:
                        images = imageUri;
                        plan_fragment.setVisibilityFirstImageBox(false);
                        plan_fragment.setFirstImagePreview(preview);
                        break;
                }
            }
        }
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(EXTERNAL_CONTENT_URI, "image/*");
        plan_fragment.startActivityForResult(intent, requestCode);
    }

    private Drawable getPreview(Uri uri) {
        try {
            ContentResolver contentResolver = plan_fragment.getActivity().getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void onButtonFirstImageClicked() {
        if (images == null) {
            openGallery(MEDIA_IMAGE_REQUEST_CODE);
        } else {
            images = null;
            plan_fragment.setFirstImagePreview(null);
            plan_fragment.setVisibilityFirstImageBox(true);
        }
    }

    public void addItinerary() {
        storageReference = FirebaseStorage.getInstance().getReference();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String titleItinerary = plan_fragment.getTitleItinerary();
        String descriptionItinerary = plan_fragment.getDescriptionItinerary();
        String difficultyLevel = plan_fragment.getDifficultyLevel();
        String hours = String.valueOf(plan_fragment.getHoursItinerary());
        String minutes = String.valueOf(plan_fragment.getMinutesItinerary());
        String seconds = String.valueOf(plan_fragment.getSecondsItinerary());

        ItineraryDao itineraryDao = new ItineraryDao();
        String itineraryId = itineraryDao.getNewKey();

        if (images != null) {
            StorageReference itineraryRef = storageReference.child("itinerary_image/" + itineraryId + ".jpg");
            itineraryRef.putFile(Uri.parse(String.valueOf(images))).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        itineraryRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String uid = auth.getUid();

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("titleItinerary", titleItinerary);
                                map.put("descriptionItinerary", descriptionItinerary);
                                map.put("difficultyLevel", difficultyLevel);
                                map.put("hours", hours);
                                map.put("minutes", minutes);
                                map.put("seconds", seconds);
                                map.put("image", uri.toString());
                                map.put("userId", uid);
                                map.put("email", firebaseUser.getEmail());
                                map.put("itineraryId", itineraryId);

                                referenceDb.child("Itinerary").child(itineraryId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            //APRO SCHERMATA MAPPA
                                            Toast.makeText(plan_fragment.getActivity(), "Operation performed successfully", Toast.LENGTH_SHORT).show();

                                        } else {

                                            //ERRORE
                                            Toast.makeText(plan_fragment.getActivity(), "Operation failed. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(plan_fragment.getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{
            Toast.makeText(plan_fragment.getActivity(),"Please add image", Toast.LENGTH_SHORT).show();
        }
    }
}
