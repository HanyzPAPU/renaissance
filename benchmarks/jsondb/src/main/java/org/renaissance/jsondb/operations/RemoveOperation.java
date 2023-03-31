package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import javax.xml.crypto.Data;

import org.renaissance.jsondb.model.Artist;

public class RemoveOperation implements DatabaseOperation {
    
    private String name;

    public RemoveOperation(String name) {
        this.name = name;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {

        // Removal works based on ID, but requires the whole object in its API
        // Because of this, we create an artist but set only its realName (ID)
        Artist artist = new Artist();
        artist.setRealName(name);
        return jsonDBTemplate.remove(artist, Artist.class);
    }
}
