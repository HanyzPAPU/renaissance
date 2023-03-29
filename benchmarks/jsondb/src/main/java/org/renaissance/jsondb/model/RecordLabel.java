package org.renaissance.jsondb.model;

import java.util.Date;

public class RecordLabel {
    private String name;
    private int netWorth;
    private Date creationDate;
    private String headquarters;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getNetWorth() { return netWorth; }
    public void setNetWorth(int netWorth) { this.netWorth = netWorth; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public String getHeadquarters() { return headquarters; }
    public void setHeadquarters(String headquarters) { this.headquarters = headquarters; }
}
