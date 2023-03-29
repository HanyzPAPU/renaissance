package org.renaissance.jsondb.model;

public class Song {
    private String title;
    private float length;
    private Genre[] genres;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public float getLength() { return length; }
    public void setLength(float length) { this.length = length; }
    public Genre[] getGenres() { return genres; }
    public void setGenres(Genre[] genres) { this.genres = genres; }
}
