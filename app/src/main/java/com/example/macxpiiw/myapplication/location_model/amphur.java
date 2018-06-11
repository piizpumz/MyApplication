package com.example.macxpiiw.myapplication.location_model;

/**
 * Created by macxpiiw on 6/11/2018 AD.
 */

public class amphur {
    private String pid ;
    private String name;
    private String changwat_pid;

    public amphur(String pid, String name, String changwat_pid) {
        this.pid = pid;
        this.name = name;
        this.changwat_pid = changwat_pid;
    }

    public amphur(){}

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChangwat_pid() {
        return changwat_pid;
    }

    public void setChangwat_pid(String changwat_pid) {
        this.changwat_pid = changwat_pid;
    }
}
