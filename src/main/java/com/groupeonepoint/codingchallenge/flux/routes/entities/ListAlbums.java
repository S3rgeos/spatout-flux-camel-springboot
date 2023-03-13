package com.groupeonepoint.codingchallenge.flux.routes.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class ListAlbums {
    private List<Album> albums;

    public List<Album> getAlbums() {
        return albums;
    }

    @JsonProperty("albums")
    public void setAlbums(Map<String, Object> albums) {
        this.albums = ;
    }
}