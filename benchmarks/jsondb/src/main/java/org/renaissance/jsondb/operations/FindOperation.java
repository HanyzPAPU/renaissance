package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.Artist;

public class FindOperation implements DatabaseOperation {

    private String jxQuery;

    public FindOperation(String jxQuery) {
        this.jxQuery = jxQuery;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {
        return jsonDBTemplate.find(jxQuery, Artist.class);
    }
    
}
