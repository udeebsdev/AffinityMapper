package com.affinitymapper.affinitymapper.model;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class Location implements BaseModel{
    private String userId;
    private boolean active;
    private Double latitude;
    private Double longitude;


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
