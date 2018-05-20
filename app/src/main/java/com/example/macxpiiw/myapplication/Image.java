package com.example.macxpiiw.myapplication;


public class Image {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private long surveyID;
    private String sampleID;
    private String type;
    private String note;
    private byte[] image;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String disease;
    private String status;

    public Image(){

    }

    public Image(Long surveyID, String sampleID, String type, String note, byte[] image , String disease , String status) {
        this.surveyID = surveyID;
        this.sampleID = sampleID;
        this.type = type;
        this.note = note;
        this.image = image;
        this.disease = disease;
        this.status = status;
    }

    public Long getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(Long surveyID) {
        this.surveyID = surveyID;
    }

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

