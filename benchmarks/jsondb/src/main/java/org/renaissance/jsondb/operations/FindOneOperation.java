package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

import org.renaissance.jsondb.model.Artist;

public class FindOneOperation implements DatabaseOperation {

    private String jxQuery;

    public FindOneOperation(String jxQuery) {
        this.jxQuery = jxQuery;
    }

    @Override
    public Object Apply(JsonDBTemplate jsonDBTemplate) {
        return jsonDBTemplate.findOne(jxQuery, Artist.class);
    }
}
