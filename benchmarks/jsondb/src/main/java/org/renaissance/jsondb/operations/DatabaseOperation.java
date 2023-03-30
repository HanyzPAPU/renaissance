package org.renaissance.jsondb.operations;

import io.jsondb.JsonDBTemplate;

public interface DatabaseOperation {
    public Object Apply(JsonDBTemplate jsonDBTemplate);
}