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
        // Use upsert so we insert in the rare occasion that the Original artist was not present
        // This should happen only because of race conditions
        jsonDBTemplate.upsert(artist);
        return null;
    }
}
