package org.renaissance.jsondb.model;

import java.util.Date;

public class Genre {
    private String name;
    private String description;
    private Date decadeOfCreation;

    public String getName() { return name;  }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDecadeOfCreation() { return decadeOfCreation; }
    public void setDecadeOfCreation(Date decadeOfCreation) { this.decadeOfCreation = decadeOfCreation; }
}
