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

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ItemDetailsActivity extends AppCompatActivity {

    //elements initialization
    private Toolbar mActionBarToolbar;
    private ImageView imgArtwork;

    private TextView track, price, genre, artist, long_description, no_description;

    private ConstraintLayout descriptionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //elements definition
        imgArtwork = (ImageView) findViewById(R.id.imgTrackArtWork);
        track = (TextView) findViewById(R.id.etxt_trackname);
        price = (TextView) findViewById(R.id.etxt_price);
        genre = (TextView) findViewById(R.id.etxt_genre);
        artist = (TextView) findViewById(R.id.etxt_artist);
        long_description = (TextView) findViewById(R.id.etxt_longDesc);
        no_description = (TextView) findViewById(R.id.tvNoDescription);
        descriptionLayout = (ConstraintLayout) findViewById(R.id.descriptionLayout);

        Intent intent = getIntent();
        final String trackName = intent.getStringExtra(ItemListActivity.KEY_TRACK_NAME);
        final String trackPrice = intent.getStringExtra(ItemListActivity.KEY_PRICE);
        final String trackGenre = intent.getStringExtra(ItemListActivity.KEY_GENRE);
        final String artistName = intent.getStringExtra(ItemListActivity.KEY_ARTIST);
        final String collectionName = intent.getStringExtra(ItemListActivity.KEY_COLLECTION_NAME);
        final String artWorkUrl100 = intent.getStringExtra(ItemListActivity.KEY_IMAGE);
        final String description = intent.getStringExtra(ItemListActivity.KEY_LONG_DESCRIPTION);


        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(collectionName);

        // show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        track.setText(trackName);
        price.setText(trackPrice);
        genre.setText(trackGenre);
        artist.setText(artistName);

        //this condition is for the item/s that have or hasn't LONG DESCRIPTION from the iTunes Search API.
        if (description.equals("")){
            descriptionLayout.setVisibility(View.GONE);
            no_description.setVisibility(View.VISIBLE);
        }else{
            descriptionLayout.setVisibility(View.VISIBLE);
            long_description.setText(description);
            long_description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        //loading the images from API's URL via Picasso dependency.
        Picasso.with(getApplicationContext())
                .load(artWorkUrl100)
                .into(imgArtwork);

    }


    // This ID represents the Home or Up button. In the case of this
    // activity, the Up button is shown. Use NavUtils to allow users
    // to navigate up one level in the application structure.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
