package org.renaissance.jsondb.model;

import java.util.Date;

public class Album {
    private String title;
    private String description;
    private Date creationDate;
    private Song[] songs;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public Song[] getSongs() { return songs; }
    public void setSongs(Song[] songs) { this.songs = songs; }
}
