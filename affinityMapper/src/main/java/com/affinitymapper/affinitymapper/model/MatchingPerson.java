package com.affinitymapper.affinitymapper.model;

import java.util.ArrayList;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class MatchingPerson {

    private String name;
    private String email;
    private ArrayList<String> interestGroups;
    private Double latitude;
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getInterestGroups() {
        return interestGroups;
    }

    public void setInterestGroups(ArrayList<String> interestGroups) {
        this.interestGroups = interestGroups;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
