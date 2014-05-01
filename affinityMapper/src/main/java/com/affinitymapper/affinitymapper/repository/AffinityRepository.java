package com.affinitymapper.affinitymapper.repository;

/**
 * Created by udeebsdev on 4/30/14.
 */

import com.affinitymapper.affinitymapper.model.*;
import com.affinitymapper.affinitymapper.repository.restCalls.GetNearByUsers;

import java.util.ArrayList;

public class AffinityRepository {

    AffinityRepository() {

    }

    public void addUser(Person newUser) {
        //TODO make call to addUser

    }

    public void updateUser(Person updateUser) {

    }

    public Person getUser(String userId){
        return null;
    }

    public ArrayList<String> getInterestList() {
        //TODO return a  list of interests.
        return null;
    }

    public ArrayList<MatchingPerson> getMatchingPerson(String userId){
        //new GetNearByUsers().execute(userId);
        return null;
    }

    public Location getLocation(String userId){
        return null;
    }

    public void updateLocation(Location currentLocation){

    }
}
