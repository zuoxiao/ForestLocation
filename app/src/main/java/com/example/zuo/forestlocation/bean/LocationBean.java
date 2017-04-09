package com.example.zuo.forestlocation.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zuo on 2017/4/7.
 */

@Table(name = "Location_info",onCreated = "")
public class LocationBean {
    @Column(name = "_id",isId = true,autoGen = true)
    public String id;
    @Column(name = "latitude")
    public double latitude;//纬度
    @Column(name = "longitude")
    public double longitude;//经度
    @Column(name = "number")
    public int number;
    @Column(name = "name")
    public String name;
    @Column(name = "spare")
    public String spare;//备用

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
