package com.affinitymapper.affinitymapper.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by udeebsdev on 5/1/14.
 */
public class MatchingPersonList implements BaseModel, Serializable {

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
