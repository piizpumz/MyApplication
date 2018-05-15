package com.example.macxpiiw.myapplication;

/**
 * Created by macxpiiw on 3/14/2018 AD.
 */

public class Garden {
    private long id;
    private String garden_name;
    private String longitude;
    private String latitude;
    private String level_sea;
    private String garden_size;
    private String locationID;
    private String garden_satatus;

    public Garden(){}

    public Garden(String garden_name, String longitude, String latitude, String level_sea, String garden_size, String locationID , String garden_status) {
        this.garden_name = garden_name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.level_sea = level_sea;
        this.garden_size = garden_size;
        this.locationID = locationID;
        this.garden_satatus = garden_status;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGarden_name() {
        return garden_name;
    }

    public void setGarden_name(String garden_name) {
        this.garden_name = garden_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLevel_sea() {
        return level_sea;
    }

    public void setLevel_sea(String level_sea) {
        this.level_sea = level_sea;
    }

    public String getGarden_size() {
        return garden_size;
    }

    public void setGarden_size(String garden_size) {
        this.garden_size = garden_size;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getGarden_satatus() {
        return garden_satatus;
    }

    public void setGarden_satatus(String garden_satatus) {
        this.garden_satatus = garden_satatus;
    }
}
