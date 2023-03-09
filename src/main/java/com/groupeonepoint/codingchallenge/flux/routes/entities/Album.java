package com.groupeonepoint.codingchallenge.flux.routes.entities;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Album {
    //String id;
    //String albumName;
    String artistName;
    //Date releaseDate;
    //int totalTracks;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @JsonProperty("albums")
    private void getArtistNested(Map<String, Object> artists) {
        this.artistName = (String)artists.get("items");
    }
}
