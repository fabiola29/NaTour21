package com.example.natour2022.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.natour2022.view.activity.StartActivity;
import com.example.natour2022.view.fragment.DetailsItinerary_fragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListItinerary extends RecyclerView.Adapter<AdapterListItinerary.HolderListItinerary> {

    private Context context;
    private List<Itinerary> itineraryList;
    private StartActivity startActivity;


    public AdapterListItinerary(Context context, List<Itinerary> itineraryList) {
        this.context = context;
        this.itineraryList = itineraryList;
    }

    @NonNull
    @Override
    public HolderListItinerary onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_itinerary, parent, false);
        return new HolderListItinerary(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterListItinerary.HolderListItinerary holder, int position) {

        String itineraryId = itineraryList.get(position).getItineraryId();

        String titleItinerary = itineraryList.get(position).getTitleItinerary();
        holder.title_row_itinerary.setText(titleItinerary);

        String email = itineraryList.get(position).getEmail();
        holder.email_user_row_itinerary.setText(email);

        holder.progressBar_row_itinerary.setVisibility(View.VISIBLE);

        try {
            Picasso.get().load(itineraryList.get(position).getImage_itinerary())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.imageView_row_itinerary, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar_row_itinerary.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(context, "Impossibile caricare una delle immagini" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            holder.progressBar_row_itinerary.setVisibility(View.GONE);

                        }
                    });

        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return itineraryList.size();
    }

    class HolderListItinerary extends RecyclerView.ViewHolder implements View.OnClickListener {

       private ImageView imageView_row_itinerary;
       private ProgressBar progressBar_row_itinerary;
       private TextView title_row_itinerary;
       private TextView email_user_row_itinerary;


        public HolderListItinerary(@NonNull View itemView) {
            super(itemView);

            imageView_row_itinerary = itemView.findViewById(R.id.imageView_row_itinerary);
            progressBar_row_itinerary = itemView.findViewById(R.id.progressBar_row_itinerary);
            title_row_itinerary = itemView.findViewById(R.id.title_row_itinerary);
            email_user_row_itinerary = itemView.findViewById(R.id.email_user_row_itinerary);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DetailsItinerary_fragment fg = new DetailsItinerary_fragment(startActivity);
            fg.setItinerary(itineraryList.get(getLayoutPosition()));
            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.activity_main,fg).addToBackStack(null).commit();
        }
    }
}
