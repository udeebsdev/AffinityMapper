package com.affinitymapper.affinitymapper.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class MatchingPerson extends Person{

    private Double latitude;
    private Double longitude;

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
