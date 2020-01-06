package com.example.masterdetail;

/**
 *  Copyright 2019 JRC
 *
 *
 *
 *
 *  Created by Joedanica R. Casanova on 01/04/2019
 *
 *
 *  Coding Challenge!!!
 *
 *
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<TrackModel> trackList;
    private List<TrackModel> trackListFiltered;
    private TrackAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView trackName, trackPrice, trackGenre;
        public TextView viewedDate;
        public ImageView artWork;
        public CardView trackView;

        public MyViewHolder(View view) {
            super(view);
            // initialize the View objects
            trackName = (TextView) itemView.findViewById(R.id.etxt_trackname);
            artWork = (ImageView) itemView.findViewById(R.id.imgTrackArtWork);
            trackPrice = (TextView) itemView.findViewById(R.id.etxt_price);
            trackGenre = (TextView) itemView.findViewById(R.id.etxt_genre);
            trackView = (CardView) itemView.findViewById(R.id.trackCardView);
            viewedDate = (TextView) itemView.findViewById(R.id.tvDateVisited);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onTrackSelected(trackListFiltered.get(getAdapterPosition()));

                    //You can change timeformat as per your requirement
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());

                    String currentDateandTime = sdf.format(new Date());

                    viewedDate.setText(currentDateandTime);
                }
            });
        }
    }

    public TrackListAdapter(Context context, List<TrackModel> trackList, TrackAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.trackList = trackList;
        this.trackListFiltered = trackList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // this method will bind the data to the ViewHolder from whence it'll be shown to other Views
        final TrackModel trackModel = trackListFiltered.get(position);

        holder.trackName.setText(trackModel.getTrackName());
        holder.trackPrice.setText(trackModel.getTrackPrice());
        holder.trackGenre.setText(trackModel.getPrimaryGenreName());

        Picasso.with(context).load(trackModel.getArtworkUrl100()).into(holder.artWork);
    }

    @Override
    public int getItemCount() {
        return trackListFiltered.size();
    }

    //to prepare items when search function is triggered
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    trackListFiltered = trackList;
                } else {
                    List<TrackModel> filteredList = new ArrayList<>();
                    for (TrackModel row : trackList) {
                        if (row.getTrackName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    trackListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = trackListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                trackListFiltered = (ArrayList<TrackModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TrackAdapterListener {
        void onTrackSelected(TrackModel track);
    }

}