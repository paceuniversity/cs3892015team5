package com.williamokano.apps.kidsworld.models;

/**
 * Created by William on 4/18/2015.
 */
public class Player {
    private Integer IdPlayer;
    private String Name;

    public Player() { }

    public Player(Integer _id, String name) {
        this.IdPlayer = _id;
        this.Name = name;
    }

    public Integer getIdPlayer() {
        return IdPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        IdPlayer = idPlayer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
