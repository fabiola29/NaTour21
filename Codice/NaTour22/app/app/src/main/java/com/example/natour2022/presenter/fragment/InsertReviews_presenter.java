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
import com.example.natour2022.dao.ReviewDao;

import com.example.natour2022.model.Itinerary;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.Explore_fragment;
import com.example.natour2022.view.fragment.InsertReviews_fragment;
import com.example.natour2022.view.fragment.ViewReviews_fragment;
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
import java.util.regex.Pattern;

public class InsertReviews_presenter {

    private InsertReviews_fragment insertReviews_fragment;
    private StartActivity startActivity;
    private DatabaseReference referenceDb;
    private StorageReference storageReference;
    private FirebaseAuth auth;

    private Uri images;
    private static final int MEDIA_IMAGE_REQUEST_CODE = 1;
    private String idItinerary;
    private Itinerary itinerary;


    public InsertReviews_presenter(InsertReviews_fragment insertReviews_fragment, String idItinerary) {
        this.insertReviews_fragment = insertReviews_fragment;
        this.idItinerary = idItinerary;
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }

    public void onButtonBackReviewClicked() {
        Fragment fragment = new Explore_fragment(startActivity);
        FragmentManager fragmentManager = insertReviews_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onButtonCancelClicked() {
        InsertReviews_fragment fragment = new InsertReviews_fragment(startActivity,itinerary);
        FragmentManager fragmentManager = insertReviews_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

    public void onTitleChanged() {
        if (insertReviews_fragment.getTitleReview().length() >= 30) {
            insertReviews_fragment.setCounterEnabledTextInputTitle(false);
        } else {
            insertReviews_fragment.setCounterEnabledTextInputTitle(true);
        }
    }

    public void onDescriptionChanged() {
        if (insertReviews_fragment.getDescriptionReview().length() >= 150) {
            insertReviews_fragment.setCounterEnabledTextInputDescription(false);
        } else {
            insertReviews_fragment.setCounterEnabledTextInputDescription(true);
        }
    }

    public void onButtonFirstImageClicked() {
        if (images == null) {
            openGallery(MEDIA_IMAGE_REQUEST_CODE);
        } else {
            images = null;
            insertReviews_fragment.setFirstImagePreview(null);
            insertReviews_fragment.setVisibilityFirstImageBox(true);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Drawable preview = getPreview(imageUri);
            if (preview == null) {
                MotionToast.display(
                        insertReviews_fragment.getActivity(),
                        R.string.toast_error_unknown_error,
                        MotionToastType.ERROR_MOTION_TOAST
                );
            } else {
                switch (requestCode) {
                    case MEDIA_IMAGE_REQUEST_CODE:
                        images = imageUri;
                        insertReviews_fragment.setVisibilityFirstImageBox(false);
                        insertReviews_fragment.setFirstImagePreview(preview);
                        break;
                }
            }
        }
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(EXTERNAL_CONTENT_URI, "image/*");
        insertReviews_fragment.startActivityForResult(intent, requestCode);
    }

    private Drawable getPreview(Uri uri) {
        try {
            ContentResolver contentResolver = insertReviews_fragment.getActivity().getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void onButtonConfermationClicked() {
        Resources resources = insertReviews_fragment.getActivity().getResources();
        insertReviews_fragment.cleanTextInputErrors();
        insertReviews_fragment.setEnabledButtonConfermation(false);
        String titleReview = insertReviews_fragment.getTitleReview();
        String descriptionReview = insertReviews_fragment.getDescriptionReview();
        String voteItinerary = insertReviews_fragment.getVoteReview();
        if (titleReview.length() == 0) {
            insertReviews_fragment.setTextInputTitleReviewError(
                    resources.getString(R.string.fragment_post_review_enter_title)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
        } else if (titleReview.length() < 30) {
            insertReviews_fragment.setTextInputTitleReviewError(
                    resources.getString(R.string.fragment_post_review_invalid_title)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
        } else if (descriptionReview.length() == 0) {
            insertReviews_fragment.setTextInputDescriptionReviewError(
                    resources.getString(R.string.fragment_post_review_enter_description)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
        } else if (descriptionReview.length() < 150) {
            insertReviews_fragment.setTextInputDescriptionReviewError(
                    resources.getString(R.string.fragment_post_review_enter_invalid_description)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
        } else if (voteItinerary.length()  == 0) {
            insertReviews_fragment.setTextInputVoteReviewError(
                    resources.getString(R.string.fragment_post_review_enter_vote)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
        } else if (!isValidVoteItinerary(voteItinerary)) {
            insertReviews_fragment.setTextInputVoteReviewError(
                    resources.getString(R.string.fragment_post_review_invalid_vote)
            );
            insertReviews_fragment.setEnabledButtonConfermation(true);
         } else {
            addReview();
        }
    }

    public static boolean isValidVoteItinerary(String voteItinerary) {
        /*
             ^ : inizio stringa
            [0-9] : acquisise un carattere da 0 a 9
             $ : corrisponde alla fine della stringa
        */
        Pattern VOTE_ITINERARY_PATTERN
                = Pattern.compile(
                "^[0-9]$");

        return VOTE_ITINERARY_PATTERN.matcher(voteItinerary).matches();
    }

    public void addReview() {
        storageReference = FirebaseStorage.getInstance().getReference();
        referenceDb = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String titleReview = insertReviews_fragment.getTitleReview();
        String descriptionReview = insertReviews_fragment.getDescriptionReview();
        String voteItinerary = insertReviews_fragment.getVoteReview();

        ReviewDao reviewDao = new ReviewDao();
        String reviewId = reviewDao.getNewKey();

         if (images != null) {
            StorageReference reviewRef = storageReference.child("review_image/").child(reviewId + ".jpg");
            reviewRef.putFile(Uri.parse(String.valueOf(images))).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        reviewRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String uid = auth.getUid();

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("titleReview", titleReview);
                                map.put("image", uri.toString());
                                map.put("email", firebaseUser.getEmail());
                                map.put("voteReview", voteItinerary);
                                map.put("descriptionReview", descriptionReview);
                                map.put("userId", uid);
                                map.put("reviewId", reviewId);
                                map.put("itineraryId",idItinerary);

                                referenceDb.child("Reviews").child(reviewId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            //APRO SCHERMATA CONTENENTE TUTTE LE RECENSIONI
                                            Toast.makeText(insertReviews_fragment.getActivity(), "Operation successful, thanks for your feedback", Toast.LENGTH_SHORT).show();
                                            openListReviewsFragment(idItinerary);

                                        } else {

                                            //ERRORE
                                            Toast.makeText(insertReviews_fragment.getActivity(), "Operation failed. Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(insertReviews_fragment.getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(insertReviews_fragment.getActivity(), "Please add image", Toast.LENGTH_SHORT).show();
        }
     }

    public void openListReviewsFragment(String itinerary) {
        Fragment fragment = new ViewReviews_fragment(startActivity,itinerary);
        FragmentManager fragmentManager = insertReviews_fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.activity_main, fragment).commit();
    }

}