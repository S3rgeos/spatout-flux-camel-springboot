package com.groupeonepoint.codingchallenge.flux.routes.entities;

import java.util.Date;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import java.text.SimpleDateFormat;

@CsvRecord(separator = ",", skipFirstLine = false, generateHeaderColumns = false)
public class AlbumMinify {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @DataField(pos = 1, columnName = "Id")
    private String id;

    @DataField(pos = 2, columnName = "Album Name")
    private String name;

    @DataField(pos = 3, columnName = "Artist Name")
    private String artist;

    @DataField(pos = 4, columnName = "Release Date", pattern="yyyy-MM-dd")
    private Date releaseDate;

    @DataField(pos = 5, columnName = "Total Tracks")
    private int totalTracks;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public Date getReleaseDateDate() {
        return releaseDate;
    }

    public String getReleaseDate() {
        return simpleDateFormat.format(releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public int getTotalTracks() {
        return totalTracks;
    }
    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

}
