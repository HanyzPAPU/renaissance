package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.Artist;

public class InsertOperation implements DatabaseOperation {

    Artist artist;

    public InsertOperation(Artist artist) {
        this.artist = artist;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {
        jsonDBTemplate.insert(artist);
        return null;
    }
}
