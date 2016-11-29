package com.example.jayjay.informer;

/**
 * Created by JayJay on 11/29/2016.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Stations {

    private String caw_name;
    private String constituency_name;
    private String county_name;
    private String polling_station_name;

    public Stations() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Stations(String caw_name, String constituency_name, String county_name, String polling_station_name) {
        this.caw_name = caw_name;
        this.constituency_name = constituency_name;
        this.county_name = county_name;
        this.polling_station_name = polling_station_name;
    }

    public String getCaw_name() {
        return caw_name;
    }

    public void setCaw_name(String caw_name) {
        this.caw_name = caw_name;
    }

    public String getConstituency_name() {
        return constituency_name;
    }

    public void setConstituency_name(String constituency_name) {
        this.constituency_name = constituency_name;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public String getPolling_station_name() {
        return polling_station_name;
    }

    public void setPolling_station_name(String polling_station_name) {
        this.polling_station_name = polling_station_name;
    }

}
