package com.example.natour2022.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.natour2022.R;
import com.example.natour2022.model.Itinerary;
import com.example.natour2022.model.Review;
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.DetailsItinerary_fragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListReviews extends RecyclerView.Adapter<AdapterListReviews.HolderListReviews> {

    private Context context;
    private List<Review> reviewList;
    private StartActivity startActivity;
    private List<Itinerary> itineraryList;


    public AdapterListReviews(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public HolderListReviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_reviews, parent, false);
        return new HolderListReviews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListReviews.HolderListReviews holder, int position) {

        Review review = reviewList.get(position);
        String itineraryId = review.getItineraryId();

        String titleReview = reviewList.get(position).getTitleReview();
        holder.title_row_review.setText(titleReview);

        String email = reviewList.get(position).getEmailUserReview();
        holder.email_user_row_review.setText(email);

        String descriptionReview = reviewList.get(position).getDescriptionReview();
        holder.description_row_review.setText(descriptionReview);

        String voteReview = String.valueOf(Integer.parseInt(String.valueOf(reviewList.get(position).getVoteItinerary())));
        holder.vot_row_review.setText(voteReview);

        holder.progressBar_row_review.setVisibility(View.VISIBLE);

        try {
            Picasso.get().load(reviewList.get(position).getImageReview())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView_row_review, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar_row_review.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(context, "Impossibile caricare una delle  immagini" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            holder.progressBar_row_review.setVisibility(View.GONE);

                        }
                    });

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class HolderListReviews extends RecyclerView.ViewHolder {

        private ImageView imageView_row_review;
        private ProgressBar progressBar_row_review;
        private TextView title_row_review;
        private TextView email_user_row_review;
        private TextView description_row_review;
        private TextView vot_row_review;
        private Button buttonInsertReviews;


        public HolderListReviews(@NonNull View itemView) {
            super(itemView);

            title_row_review = itemView.findViewById(R.id.title_row_review);
            email_user_row_review = itemView.findViewById(R.id.email_user_row_review);
            description_row_review = itemView.findViewById(R.id.description_row_review);
            vot_row_review = itemView.findViewById(R.id.vot_row_review);
            progressBar_row_review = itemView.findViewById(R.id.progressBar_row_review);
            imageView_row_review = itemView.findViewById(R.id.imageView_row_review);
            buttonInsertReviews = itemView.findViewById(R.id.button_insert_review);
        }
    }
}
