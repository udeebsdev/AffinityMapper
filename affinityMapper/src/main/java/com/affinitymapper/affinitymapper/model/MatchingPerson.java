package com.affinitymapper.affinitymapper.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class MatchingPerson extends BaseModel{

    private String userId;
    private String name;
    private String email;
    private String imageUrl;
    private ArrayList<String> interestGroups;
    private Double latitude;
    private Double longitude;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
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
