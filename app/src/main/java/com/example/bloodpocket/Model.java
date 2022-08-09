package com.example.bloodpocket;

import java.util.Date;

public class Model {

    String userid, id, pusat, status, type;
    Date dateTime;

    public Model() {
    }

    public Model(String id, Date dateTime, String userid, String pusat, String status, String type) {
        this.id = id;
        this.dateTime = dateTime;
        this.userid = userid;
        this.pusat = pusat;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPusat() {
        return pusat;
    }

    public void setPusat(String pusat) {
        this.pusat = pusat;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
