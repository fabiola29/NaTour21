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
import com.example.natour2022.model.Itinerary;
import com.example.natour2022.presenter.fragment.InsertReviews_presenter;
import com.example.natour2022.view.activity.StartActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class InsertReviews_fragment extends Fragment {

    private StartActivity startActivity;
    private InsertReviews_presenter insertReviews_presenter;
    private TextInputLayout titleReview;
    private TextInputLayout descriptionReview;
    private TextInputLayout voteReview;
    private ImageView buttonFirstImage;
    private ImageButton buttonBackReview;
    private Button buttonConfermation;
    private Button buttonCancel;
    private Itinerary itinerary;

    public InsertReviews_fragment(StartActivity startActivity, Itinerary itinerary) {
        this.startActivity = startActivity;
        this.itinerary = itinerary;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_review, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleReview = view.findViewById(R.id.fragment_insert_review_text_input_name_review_layout);
        descriptionReview = view.findViewById(R.id.fragment_insert_review_text_description_itinerary_layout);
        voteReview = view.findViewById(R.id.fragment_insert_review_text_input_vote_layout);
        buttonFirstImage = view.findViewById(R.id.fragment_review_imageView_ImportFirstImage);
        buttonBackReview = view.findViewById(R.id.btBack_insert_review);
        buttonConfermation = view.findViewById(R.id.fragment_review_button_confermation);
        buttonCancel = view.findViewById(R.id.fragment_review_button_cancel);
        insertReviews_presenter = new InsertReviews_presenter(this, itinerary.getItineraryId());
        listenToClickEvents();
        listenToTextChangedEvents();
    }

    private void listenToTextChangedEvents() {
        EditText title = titleReview.getEditText();
        EditText description = descriptionReview.getEditText();
        if (title != null) {
            title.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    insertReviews_presenter.onTitleChanged();
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
                    insertReviews_presenter.onDescriptionChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
        }
    }

    public void setEnabledButtonConfermation(boolean enabled) {
        buttonConfermation.setEnabled(enabled);
    }

    private void listenToClickEvents() {
        buttonConfermation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReviews_presenter.onButtonConfermationClicked();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReviews_presenter.onButtonCancelClicked();
            }
        });
        buttonBackReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReviews_presenter.onButtonBackReviewClicked();
            }
        });
        buttonFirstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReviews_presenter.onButtonFirstImageClicked();
            }
        });
    }

    public void cleanTextInputErrors() {
        titleReview.setErrorEnabled(false);
        descriptionReview.setErrorEnabled(false);
        voteReview.setErrorEnabled(false);
    }

    public String getTitleReview() {
        EditText titleEditText = titleReview.getEditText();
        if (titleEditText == null) return "";
        return titleEditText.getText().toString();
    }

    public String getDescriptionReview() {
        EditText descriptionEditText = descriptionReview.getEditText();
        if (descriptionEditText == null) return "";
        return descriptionEditText.getText().toString();
    }

    public String getVoteReview() {
        EditText voteEditText = voteReview.getEditText();
        if (voteEditText == null) return "";
        return voteEditText.getText().toString();
    }

    public void setTextInputTitleReviewError(String error) {
        titleReview.setErrorEnabled(true);
        titleReview.setError(error);
    }

    public void setTextInputDescriptionReviewError(String error) {
        descriptionReview.setErrorEnabled(true);
        descriptionReview.setError(error);
    }

    public void setTextInputVoteReviewError(String error) {
        voteReview.setErrorEnabled(true);
        voteReview.setError(error);
    }

    public void setCounterEnabledTextInputTitle(boolean enabled) {
        titleReview.setCounterEnabled(enabled);
    }

    public void setCounterEnabledTextInputDescription(boolean enabled) {
        descriptionReview.setCounterEnabled(enabled);
    }

    public void setFirstImagePreview(Drawable drawable) {
        buttonFirstImage.setBackground(drawable);
    }

    public StartActivity getStartActivity() {
        return startActivity;
    }


    public void setVisibilityFirstImageBox(boolean visibility) {
        if (visibility) {
            buttonFirstImage.setImageDrawable(ContextCompat.getDrawable(
                            getStartActivity(),
                            R.drawable.ic_image_box
                    )
            );
        } else {
            buttonFirstImage.setImageDrawable(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        insertReviews_presenter.onActivityResult(requestCode, resultCode, data);
    }

}
