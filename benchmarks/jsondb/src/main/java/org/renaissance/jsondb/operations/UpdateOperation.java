package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.Artist;

public class UpdateOperation implements DatabaseOperation {
    Artist artist;
    
    public UpdateOperation(Artist artist) {
        this.artist = artist;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {
        // For some reason this operation is called save in JSONDB
        jsonDBTemplate.save(artist, Artist.class);
        return null;
    }
}
