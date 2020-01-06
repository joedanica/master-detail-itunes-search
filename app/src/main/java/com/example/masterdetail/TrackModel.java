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

public class TrackModel {

    public TrackModel (){}

    //string initiliazation
    private String trackName;
    private String artworkUrl100;
    private String trackPrice;
    private String primaryGenreName;

    private String artistName;
    private String collectionName;
    private String longDescription;

    //strings constructor
    public TrackModel(String trackName, String artworkUrl100, String trackPrice, String primaryGenreName, String artistName, String collectionName, String longDescription) {
        this.trackName = trackName;
        this.artworkUrl100 = artworkUrl100;
        this.trackPrice = trackPrice;
        this.primaryGenreName = primaryGenreName;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.longDescription = longDescription;
    }

    //strings getter
    public String getTrackName() {
        return trackName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getLongDescription() {
        return longDescription;
    }
}
