package com.affinitymapper.affinitymapper.model;

import java.util.List;

/**
 * Created by udeebsdev on 5/1/14.
 */
public class MatchingPersonList implements BaseModel {

    private List<MatchingPerson> matchingPersons;

    public MatchingPersonList(List<MatchingPerson> matchingPersons) {
        this.setMatchingPersons(matchingPersons);
    }

    public List<MatchingPerson> getMatchingPersons() {
        return matchingPersons;
    }

    public void setMatchingPersons(List<MatchingPerson> matchingPersons) {
        this.matchingPersons = matchingPersons;
    }

}
