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
        try {
            // Catch any exceptions raised in case that we insert a key that in already present
            // This should happen only because of race conditions
            jsonDBTemplate.insert(artist);
        }
        catch (Exception e){
            //...
        }
        return null;
    }
}
