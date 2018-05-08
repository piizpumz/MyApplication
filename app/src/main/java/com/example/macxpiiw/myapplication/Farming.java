package com.example.macxpiiw.myapplication;

/**
 * Created by macxpiiw on 3/16/2018 AD.
 */

public class Farming {

    private long id;
    private String d_m_y_farming;
    private String gardenid;
    private String plantid;


    public Farming(){}

    public Farming(String d_m_y_farming, String gardenid, String plantid) {
        this.d_m_y_farming = d_m_y_farming;
        this.gardenid = gardenid;
        this.plantid = plantid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getD_m_y_farming() {
        return d_m_y_farming;
    }

    public void setD_m_y_farming(String d_m_y_farming) {
        this.d_m_y_farming = d_m_y_farming;
    }

    public String getGardenid() {
        return gardenid;
    }

    public void setGardenid(String gardenid) {
        this.gardenid = gardenid;
    }

    public String getPlantid() {
        return plantid;
    }

    public void setPlantid(String plantid) {
        this.plantid = plantid;
    }
}
