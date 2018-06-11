package com.example.macxpiiw.myapplication.location_model;

/**
 * Created by macxpiiw on 6/11/2018 AD.
 */

public class tumbon {
    private long id ;
    private String tumbon_name;
    private long amphurId;
    private long provinceId;


    public tumbon(long id, String tumbon_name, long amphurId, long provinceId) {
        this.id = id;
        this.tumbon_name = tumbon_name;
        this.amphurId = amphurId;
        this.provinceId = provinceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTumbon_name() {
        return tumbon_name;
    }

    public void setTumbon_name(String tumbon_name) {
        this.tumbon_name = tumbon_name;
    }

    public long getAmphurId() {
        return amphurId;
    }

    public void setAmphurId(long amphurId) {
        this.amphurId = amphurId;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }
}
