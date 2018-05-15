package com.example.macxpiiw.myapplication;

/**
 * Created by macxpiiw on 3/15/2018 AD.
 */

public class Survey {
    private long id ;
    private String d_m_y_survey ;
    private String time_survey;
    private String temp ;
    private String moisture;
    private String rain;
    private String light;
    private String dew;
    private String category;
    private String sample_point;
    private String point;
    private Long farmingID ;
    private String incidence;
    private String severity;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Survey(){}

    public Survey(String d_m_y_survey, String time_survey, String temp, String moisture, String rain, String light, String dew, String category, String sample_point, String point, Long farmingID , String incidence , String severity , String status) {
        this.d_m_y_survey = d_m_y_survey;
        this.time_survey = time_survey;
        this.temp = temp;
        this.moisture = moisture;
        this.rain = rain;
        this.light = light;
        this.dew = dew;
        this.category = category;
        this.sample_point = sample_point;
        this.point = point;
        this.farmingID = farmingID;
        this.incidence = incidence;
        this.severity = severity;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getD_m_y_survey() {
        return d_m_y_survey;
    }

    public void setD_m_y_survey(String d_m_y_survey) {
        this.d_m_y_survey = d_m_y_survey;
    }

    public String getTime_survey() {
        return time_survey;
    }

    public void setTime_survey(String time_survey) {
        this.time_survey = time_survey;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getDew() {
        return dew;
    }

    public void setDew(String dew) {
        this.dew = dew;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSample_point() {
        return sample_point;
    }

    public void setSample_point(String sample_point) {
        this.sample_point = sample_point;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Long getFarmingID() {
        return farmingID;
    }

    public void setFarmingID(Long farmingID) {
        this.farmingID = farmingID;
    }
}
