package com.pc.java.wordspuzzle.models;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public class Player {
    //region Fields
    private String mName;
    private int mPoints;
    //endregion

    //region Constructors
    public Player() {
        this(null);
    }

    public Player(String name) {
        this(name, 0);
    }

    public Player(String name, int points) {
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