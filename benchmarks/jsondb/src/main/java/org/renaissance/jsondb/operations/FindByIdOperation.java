package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.Artist;
import org.renaissance.jsondb.operations.DatabaseOperation;

public class FindByIdOperation implements DatabaseOperation {

    private String id;

    public FindByIdOperation(String id) {
        this.id = id;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {
        return jsonDBTemplate.findById(this.id, Artist.class);        
    }
}
