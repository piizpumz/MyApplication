package com.example.macxpiiw.myapplication.location_model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by macxpiiw on 6/11/2018 AD.
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class province {
    private String pid ;
    private String name;

    public province(String id, String province_name) {
        this.pid = id;
        this.name = province_name;
    }

    public province() {
    }

    public String getId() {
        return pid;
    }

    public void setId(String id) {
        this.pid = id;
    }

    public String getProvince_name() {
        return name;
    }

    public void setProvince_name(String province_name) {
        this.name = province_name;
    }
}
