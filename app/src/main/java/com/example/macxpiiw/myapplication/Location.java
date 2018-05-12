package com.example.macxpiiw.myapplication;

/**
 * Created by macxpiiw on 3/4/2018 AD.
 */

public class Location {
    private long id;
    private String location_name;
    private String moo;
    private String tumbon;
    private String amphur;
    private String province;
    private String post_code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;


    public Location() {
    }

    public Location(String location_name, String moo, String tumbon, String amphur , String province , String post_code , String status) {
        this.location_name=location_name;
        this.moo = moo;
        this.tumbon=tumbon;
        this.amphur = amphur ;
        this.province = province;
        this.post_code = post_code;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getMoo() {
        return moo;
    }

    public void setMoo(String moo) {
        this.moo = moo;
    }

    public String getTumbon() {
        return tumbon;
    }

    public void setTumbon(String tumbon) {
        this.tumbon = tumbon;
    }

    public String getAmphur() {
        return amphur;
    }

    public void setAmphur(String amphur) {
        this.amphur = amphur;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
}
