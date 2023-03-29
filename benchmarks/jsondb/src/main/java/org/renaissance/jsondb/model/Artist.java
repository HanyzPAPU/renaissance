package org.renaissance.jsondb.model;

import java.util.Date;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "artists", schemaVersion = "1.0")
public class Artist {    
    @Id
    private String realName;
    private String[] pseudonym;
    private Date dateOfBirth;
    private Date careerStartDate;
    private RecordLabel[] recordLabels;
    private Album[] albums;
    
    public String getRealName() {return realName; }
    public void setRealName(String realName) {this.realName = realName; }
    public String[] getPseudonym() { return pseudonym; }
    public void setPseudonym(String[] pseudonym) { this.pseudonym = pseudonym; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Date getCareerStartDate() { return careerStartDate; }
    public void setCareerStartDate(Date careerStartDate) { this.careerStartDate = careerStartDate; }
    public RecordLabel[] getRecordLabels() { return recordLabels; }
    public void setRecordLabels(RecordLabel[] recordLabels) { this.recordLabels = recordLabels; }
    public Album[] getAlbums() { return albums; }
    public void setAlbums(Album[] albums) { this.albums = albums; }
}
