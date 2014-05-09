package com.affinitymapper.affinitymapper.model;

import java.util.ArrayList;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class Person extends BaseModel {
    private String userId;
    private String name;
    private String email;
    private String imageUrl;
    private boolean chatRequestToggle;
    private int proximityAlertLimit;
    private boolean proximityAlertToggle;
    private ArrayList<String> interestGroups;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public boolean isChatRequestToggle() {
        return chatRequestToggle;
    }

    public void setChatRequestToggle(boolean chatRequestToggle) {
        this.chatRequestToggle = chatRequestToggle;
    }

    public int getProximityAlertLimit() {
        return proximityAlertLimit;
    }

    public void setProximityAlertLimit(int proximityAlertLimit) {
        this.proximityAlertLimit = proximityAlertLimit;
    }

    public boolean isProximityAlertToggle() {
        return proximityAlertToggle;
    }

    public void setProximityAlertToggle(boolean proximityAlertToggle) {
        this.proximityAlertToggle = proximityAlertToggle;
    }

    public ArrayList<String> getInterestGroups() {
        return interestGroups;
    }

    public void setInterestGroups(ArrayList<String> interestGroups) {
        this.interestGroups = interestGroups;
    }
}
