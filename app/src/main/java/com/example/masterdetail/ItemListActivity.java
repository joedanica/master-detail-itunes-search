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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity implements TrackListAdapter.TrackAdapterListener {

    //elements initialization
    private RecyclerView recyclerView;
    private TrackListAdapter adapter;
    private List<TrackModel> trackModelList;

    private EditText search;

    //url for iTunes Search API
    private static final String URL_DATA = "https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all";

    //key strings variables for the objects.
    public static final String KEY_TRACK_NAME = "trackName";
    public static final String KEY_IMAGE = "artworkUrl100";
    public static final String KEY_PRICE = "trackPrice";
    public static final String KEY_GENRE = "primaryGenreName";
    public static final String KEY_ARTIST = "artistName";
    public static final String KEY_COLLECTION_NAME = "collectionName";
    public static final String KEY_LONG_DESCRIPTION = "longDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        //elements definition
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        search = (EditText) findViewById(R.id.etxt_search);

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trackModelList = new ArrayList<>();
        adapter = new TrackListAdapter(ItemListActivity.this, trackModelList, this);
        recyclerView.setAdapter(adapter);

        //function for search edit text.
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.notifyDataSetChanged();
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    return true;
                }
                return false;
            }
        });

        loadUrlData(0);
    }

    //functon to get the JSON  data from the API.
    private void loadUrlData(int i) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response",response);

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++){

                        JSONObject jo = array.getJSONObject(i);

                        String trackName, artworkUrl100, trackPrice, primaryGenreName,
                                artistName, collectionName, releaseDate, longDescription, shortDescription, description;

                        if (jo.has("trackName")) {
                            //Checking address Key Present or not
                            trackName =  jo.getString("trackName");
                        }
                        else {
                            //Replace default string
                             trackName =  "Unknown Track Name";
                        }

                        if (jo.has("artworkUrl100")) {
                            //Checking address Key Present or not
                            artworkUrl100 =  jo.getString("artworkUrl100");
                        }
                        else {
                            //Replace default string
                            artworkUrl100 =  "";
                        }

                        if (jo.has("trackPrice")) {
                            //Checking address Key Present or not
                            trackPrice = "Price: $ "+jo.getString("trackPrice");
                        }
                        else {
                            //Replace default string
                            trackPrice =  "Unknown Track Price";
                        }

                        if (jo.has("primaryGenreName")) {
                            //Checking address Key Present or not
                            primaryGenreName = "Genre: "+jo.getString("primaryGenreName");
                        }
                        else {
                            //Replace default string
                            primaryGenreName =  "Unknown Track Genre";
                        }

                        if (jo.has("artistName")) {
                            //Checking address Key Present or not
                            artistName =  "Artist: "+jo.getString("artistName");
                        }
                        else {
                            //Replace default string
                            artistName =  "Unknown Artist";
                        }

                        if (jo.has("collectionName")) {
                            //Checking address Key Present or not
                            collectionName =  jo.getString("collectionName");
                        }
                        else {
                            //Replace default string
                            collectionName =  "";
                        }

                        if (jo.has("longDescription")) {
                            //Checking address Key Present or not
                            longDescription = jo.getString("longDescription");
                        }
                        else {
                            //Replace default string
                            longDescription =  "";
                        }

                        TrackModel trackModel = new TrackModel(trackName, artworkUrl100, trackPrice, primaryGenreName,
                                artistName, collectionName, longDescription);

                        trackModelList.add(trackModel);

                        // refreshing recycler view
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemListActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //passing data to ItemDetailsActivity.class to view more details
    public void onTrackSelected(TrackModel track) {

        Intent skipIntent = new Intent(this, ItemDetailsActivity.class);
        skipIntent.putExtra(KEY_TRACK_NAME, track.getTrackName());
        skipIntent.putExtra(KEY_IMAGE, track.getArtworkUrl100());
        skipIntent.putExtra(KEY_PRICE, track.getTrackPrice());
        skipIntent.putExtra(KEY_GENRE, track.getPrimaryGenreName());
        skipIntent.putExtra(KEY_ARTIST, track.getArtistName());
        skipIntent.putExtra(KEY_COLLECTION_NAME, track.getCollectionName());
        skipIntent.putExtra(KEY_LONG_DESCRIPTION, track.getLongDescription());
        startActivity(skipIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences preferences = getSharedPreferences("SAVE_STATE", MODE_PRIVATE);

        //setting the scroll position to the last state before the app is closed.
        recyclerView.scrollToPosition(preferences.getInt("position", 0));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollBy(0, preferences.getInt("offset", 0));
            }
        }, 500);
    }

    //saving the last state of the recyclerview when the app is paused.
    @Override
    protected void onPause() {
        super.onPause();
        View firstChild = recyclerView.getChildAt(0);
        int firstVisiblePosition = recyclerView.getChildAdapterPosition(firstChild);
        int offset = recyclerView.computeVerticalScrollOffset();

        SharedPreferences prefs = getSharedPreferences("SAVE_STATE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("position",firstVisiblePosition);
        editor.putInt("offset",offset);
        editor.commit();
    }

    //saving the last state of the recyclerview when the app is destroyed.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        View firstChild = recyclerView.getChildAt(0);
        int firstVisiblePosition = recyclerView.getChildAdapterPosition(firstChild);
        int offset = recyclerView.computeVerticalScrollOffset();

        SharedPreferences prefs = getSharedPreferences("SAVE_STATE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("position",firstVisiblePosition);
        editor.putInt("offset",offset);
        editor.commit();
    }

}
