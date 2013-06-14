package com.pc.java.wordspuzzle.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public class Player {
    //region Fields
    @JsonProperty("name") private String mName;
    @JsonProperty("points") private int mPoints;
    //endregion

    //region Constructors
    public Player(String name) {
        this(name, 0);
    }

    @JsonCreator public Player(@JsonProperty("name") String name, @JsonProperty("points") int points) {
        mName = name;
        mPoints = points;
    }
    //endregion

    //region Getters and Setters
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int points) {
        mPoints = points;
    }
    //endregion

    @Override public String toString() {
        return "Nome: " + mName + " - Pontos: " + mPoints;
    }
}